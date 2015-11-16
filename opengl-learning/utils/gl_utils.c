#include <assimp/cimport.h>
#include <assimp/scene.h>
#include <assimp/postprocess.h>
#include <GL/glew.h>
#include <GLFW/glfw3.h>
#include <stdio.h>
#include <stdbool.h>
#include <stdarg.h>
#include <stdlib.h>
#include <time.h>
#include "gl_utils.h"
#define STB_IMAGE_IMPLEMENTATION
#include "stb_image.h"
#include "camera.h"
#include "maths.h"

#define COLOR_RED "\x1b[31m"
#define COLOR_RESET "\x1b[0m"

void gl_log(int severity_level, char* message, ...) {
    va_list argptr;
    FILE* file = fopen(GL_LOG_FILE, "a");
    if (!file) {
        fprintf(stderr, "ERROR: could not open GL_LOG_FILE %s file\n", GL_LOG_FILE);
    }

    time_t now = time(0);
    struct tm* timeinfo = localtime(&now);

    fprintf(file, "[%d-%d-%d %2d:%2d] ", 
            timeinfo->tm_year + 1900, timeinfo->tm_mon + 1, timeinfo->tm_mday, 
            timeinfo->tm_hour, timeinfo->tm_min);
    if (severity_level == ERROR) {
        fprintf(file, "ERROR: ");
    } else if (severity_level == INFO) {
        fprintf(file, "INFO: ");
    }
    va_start(argptr, message);
    vfprintf(file, message, argptr);
    va_end(argptr);
    fprintf(file, "\n");

    // Also print to stderr if there is an error.
    if (severity_level == ERROR) {
        fprintf(stderr, "[%d-%d-%d %2d:%2d] ", 
                timeinfo->tm_year + 1900, timeinfo->tm_mon + 1, timeinfo->tm_mday, 
                timeinfo->tm_hour, timeinfo->tm_min);
        fprintf(stderr, COLOR_RED "ERROR: " COLOR_RESET);
        va_start(argptr, message);
        vfprintf(stderr, message, argptr);
        va_end(argptr);
        fprintf(stderr, "\n");
    }

    fclose(file);
}

void init_gl(char* window_title) {
    if (!glfwInit()) {
        gl_log(ERROR, "Could not start GLFW3");
    } 

    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

    window = glfwCreateWindow(window_width, window_height, window_title, NULL, NULL);

    if (!window) {
        gl_log(ERROR, "Could not create window");
    }

    glfwMakeContextCurrent(window);
    glewExperimental = GL_TRUE;
    glewInit();
    glEnable(GL_DEPTH_TEST); 
    glDepthFunc(GL_LESS);
}

GLuint compile_shader(char* filename, GLenum shader_type) {
    gl_log(INFO, "Started compiling shader: %s", filename);

    const char* shader_str = read_file(filename);
    GLuint shader = glCreateShader(shader_type);
    glShaderSource(shader, 1, &shader_str, NULL);
    glCompileShader(shader);
    free((char*) shader_str);

    int params = -1;
    glGetShaderiv(shader, GL_COMPILE_STATUS, &params);
    if (params != GL_TRUE) {
        gl_log(ERROR, "Could not compile shader: %s.", filename);
        char* shader_log = malloc(sizeof(char)*2048);
        int length;
        glGetShaderInfoLog(shader, 2048, &length, shader_log);
        gl_log(ERROR, "Shader log: %s", shader_log);
    }

    gl_log(INFO, "Finished compiling shader: %s", filename);
    return shader;
}

GLuint create_shader_program(char* frag_shader_filename, char* vert_shader_filename) {
    GLuint frag_shader = compile_shader(frag_shader_filename, GL_FRAGMENT_SHADER);
    GLuint vert_shader = compile_shader(vert_shader_filename, GL_VERTEX_SHADER);

    GLuint shader_program = glCreateProgram();
    glAttachShader(shader_program, frag_shader);
    glAttachShader(shader_program, vert_shader);
    glLinkProgram(shader_program);
    glUseProgram(shader_program);

    return shader_program;
}

const char* read_file(char* filename) {
    char* buffer = 0;
    long length;
    FILE* f = fopen(filename, "rb");

    if (!f) {
        gl_log(ERROR, "Could not read file %s\n", filename); 
        return 0;
    } 

    fseek(f, 0, SEEK_END);
    length = ftell (f);
    fseek(f, 0, SEEK_SET);
    buffer = malloc (length);
    if (buffer) {
        fread (buffer, 1, length, f);
    }
    fclose(f);
    buffer[length] = 0;

    return buffer;
}

Mat* convert_assimp_matrix(struct aiMatrix4x4 m) {
    return create_mat(
            1.0, 0.0, 0.0, 0.0,
            0.0, 1.0, 0.0, 0.0,
            0.0, 0.0, 1.0, 0.0,
            m.a4, m.b4, m.c4, m.d4);
}

int load_mesh(const char* filename, GLuint* vao, int* point_count) {
    const struct aiScene* scene = aiImportFile(filename, aiProcess_Triangulate);
    if (!scene) {
        gl_log(ERROR, "Cannot open mesh file: %s", filename);
        return 0;
    }

    gl_log(INFO, "Starting to load mesh: %s", filename);
    gl_log(INFO, "%i animations", scene->mNumAnimations);
    gl_log(INFO, "%i cameras", scene->mNumCameras);
    gl_log(INFO, "%i lights", scene->mNumLights);
    gl_log(INFO, "%i materials", scene->mNumMaterials);
    gl_log(INFO, "%i meshes", scene->mNumMeshes);
    gl_log(INFO, "%i textures", scene->mNumTextures);

    const struct aiMesh* mesh = scene->mMeshes[0];
    gl_log(INFO, "%i vertices", mesh->mNumVertices);

    *point_count = mesh->mNumVertices;

    glGenVertexArrays(1, vao);
    glBindVertexArray(*vao);

    GLfloat* points;
    GLfloat* normals;
    GLint* bone_ids;
    Mat* bone_offset_mats[16];

    // If has vertices
    if (mesh->mVertices && mesh->mNumVertices > 0)	{
        points = (GLfloat*) malloc(*point_count * 3 * sizeof(GLfloat));
        for	(int i = 0; i < *point_count; i++)	{
            const struct aiVector3D* vp = &(mesh->mVertices[i]);
            points[i * 3] = (GLfloat)vp->x;
            points[i * 3 + 1] = (GLfloat)vp->y;
            points[i * 3 + 2] = (GLfloat)vp->z;
        }

        GLuint vbo;
        glGenBuffers(1, &vbo);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER,
                3 * *point_count * sizeof (GLfloat),
                points,
                GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, NULL);
        glEnableVertexAttribArray(0);
        free(points);
    }

    // If has normals
    if (mesh->mNormals) {
        normals = (GLfloat*) malloc(*point_count * 3 * sizeof(GLfloat));
        for (int i = 0; i < *point_count; i++) {
            const struct aiVector3D* vn = &(mesh->mNormals[i]);
            normals[i * 3] = (GLfloat)vn->x;
            normals[i * 3 + 1] = (GLfloat)vn->y;
            normals[i * 3 + 2] = (GLfloat)vn->z;
        }

		GLuint vbo;
		glGenBuffers(1, &vbo);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER,
			3 * *point_count * sizeof (GLfloat),
			normals,
			GL_STATIC_DRAW);
		glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 0, NULL);
		glEnableVertexAttribArray(1);
		free(normals);
    }

    // If has bones
    if (mesh->mBones && mesh->mNumBones > 0) {
        bone_ids = (GLint*) malloc(*point_count * sizeof(GLint));
        int bone_count = (int) mesh->mNumBones;
        gl_log(INFO, "%i bones", bone_count);
        char bone_names[256][64];
        for	(int b_i = 0; b_i < bone_count; b_i++)	{
            const struct aiBone* bone = mesh->mBones[b_i];
            strcpy(bone_names[b_i], bone->mName.data);
            bone_offset_mats[b_i] = convert_assimp_matrix(bone->mOffsetMatrix);

            gl_log(INFO, "bones_names[%i] = %s", b_i, bone_names[b_i]);

            int num_weights = (int)bone->mNumWeights;
            for (int w_i = 0; w_i < num_weights; w_i++) {
                struct aiVertexWeight weight = bone->mWeights[w_i];
                int vertex_id = (int)weight.mVertexId;

                if (weight.mWeight >= 0.5) {
                    bone_ids[vertex_id] = b_i;
                }
            }
        }

        GLuint vbo;
        glGenBuffers(1, &vbo);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER,
                *point_count * sizeof (GLint),
                bone_ids,
                GL_STATIC_DRAW);
        glVertexAttribIPointer(2, 1, GL_INT, 0, NULL);
        glEnableVertexAttribArray(2);
        free(bone_ids);
    }

    aiReleaseImport(scene);
    gl_log(INFO, "Finished loading mesh: %s", filename);

    return 1;
}

GLuint create_texture(char* file_name) {
    int x, y, n;
    int force_channels = 3;
    unsigned char* image_data = stbi_load(file_name, &x, &y, &n, force_channels);
    if (!image_data) {
        gl_log(ERROR, "Could not load texture image file, %s", file_name); 
        return 0;
    }

    GLuint tex = 0;
    glGenTextures(1, &tex);
    glActiveTexture(GL_TEXTURE_2D);
    glBindTexture(GL_TEXTURE_2D, tex);
    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, x, y, 0, GL_RGB, GL_UNSIGNED_BYTE, image_data);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

    free(image_data);
    return tex;
}
