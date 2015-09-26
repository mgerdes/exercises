#include <GL/glew.h> 
#include <GLFW/glfw3.h> 
#include <time.h>
#include <stdarg.h>
#include <stdio.h>
#include "maths_funcs.h"
#include <string>
#include <fstream>
#include <iostream>
#include <math.h>

#define PI 3.14159265

std::string readFile(const char *filePath) {
    std::string content;
    std::ifstream fileStream(filePath, std::ios::in);

    if(!fileStream.is_open()) {
        std::cerr << "Could not read file " << filePath << ". File does not exist." << std::endl;
        return "";
    }

    std::string line = "";
    while(!fileStream.eof()) {
        std::getline(fileStream, line);
        content.append(line + "\n");
    }

    fileStream.close();
    return content;
}

int main () {
    if (!glfwInit ()) {
        fprintf (stderr, "ERROR: could not start GLFW3\n");
        return 1;
    } 

    glfwWindowHint (GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint (GLFW_CONTEXT_VERSION_MINOR, 2);
    glfwWindowHint (GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
    glfwWindowHint (GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

    int width = 1000;
    int height = 1000;

    GLFWwindow* window = glfwCreateWindow (width, height, "opengl", NULL, NULL);
    if (!window) {
        fprintf (stderr, "ERROR: could not open window with GLFW3\n");
        glfwTerminate();
        return 1;
    }
    glfwMakeContextCurrent (window);

    glewExperimental = GL_TRUE;
    glewInit ();

    /* tell GL to only draw onto a pixel if the shape is closer to the viewer */
    glEnable (GL_DEPTH_TEST); 
    /* with LESS depth-testing interprets a smaller value as "closer" */
    glDepthFunc (GL_LESS);

    GLfloat points[] = {
        0.5f,  0.5f, -0.5f,
        0.5f, 0.5f, 0.5f,
        0.5f, -0.5f, 0.5f,

        0.5f,  -0.5f, 0.5f,
        0.5f, -0.5f, -0.5f,
        0.5f, 0.5f, -0.5f,

        -0.5f,  0.5f, -0.5f,
        -0.5f, 0.5f, 0.5f,
        -0.5f, -0.5f, 0.5f,

        -0.5f,  -0.5f, 0.5f,
        -0.5f, -0.5f, -0.5f,
        -0.5f, 0.5f, -0.5f
    };

    GLfloat colors[] = {
        0.0f, 0.0f, 1.0f,
        0.0f, 1.0f, 0.0f,
        1.0f, 0.0f, 0.0f,

        1.0f, 0.0f, 0.0f,
        0.0f, 1.0f, 0.0f,
        0.0f, 0.0f, 1.0f,

        0.0f, 0.0f, 1.0f,
        0.0f, 1.0f, 0.0f,
        1.0f, 0.0f, 0.0f,

        1.0f, 0.0f, 0.0f,
        0.0f, 1.0f, 0.0f,
        0.0f, 0.0f, 1.0f
    };

    GLfloat square_in_middle_points[] = {
        0.01f, 0.01f, 0.0f,
        0.01f, -0.01f, 0.0f,
        -0.01f, -0.01f, 0.0f,

        -0.01f, -0.01f, 0.0f,
        -0.01f, 0.01f, 0.0f,
        0.01f, 0.01f, 0.0f
    };

    GLfloat square_in_middle_colors[] = {
        0.0f, 0.0f, 1.0f,
        0.0f, 1.0f, 0.0f,
        1.0f, 0.0f, 0.0f,

        1.0f, 0.0f, 0.0f,
        0.0f, 1.0f, 0.0f,
        0.0f, 0.0f, 1.0f
    };

    GLuint points_vbo = 0;
    glGenBuffers(1, &points_vbo);
    glBindBuffer(GL_ARRAY_BUFFER, points_vbo);
    glBufferData(GL_ARRAY_BUFFER, sizeof (points), points, GL_STATIC_DRAW);

    GLuint color_vbo = 0;
    glGenBuffers (1, &color_vbo);
    glBindBuffer (GL_ARRAY_BUFFER, color_vbo);
    glBufferData (GL_ARRAY_BUFFER, sizeof (colors), colors, GL_STATIC_DRAW);

    GLuint square_in_middle_points_vbo = 0;
    glGenBuffers(1, &square_in_middle_points_vbo);
    glBindBuffer(GL_ARRAY_BUFFER, square_in_middle_points_vbo);
    glBufferData(GL_ARRAY_BUFFER, sizeof (square_in_middle_points), square_in_middle_points, GL_STATIC_DRAW);

    GLuint square_in_middle_color_vbo = 0;
    glGenBuffers (1, &square_in_middle_color_vbo);
    glBindBuffer (GL_ARRAY_BUFFER, square_in_middle_color_vbo);
    glBufferData (GL_ARRAY_BUFFER, sizeof (square_in_middle_colors), square_in_middle_colors, GL_STATIC_DRAW);

    GLuint vao = 0;
    glGenVertexArrays (1, &vao);
    glBindVertexArray (vao);

    glBindBuffer (GL_ARRAY_BUFFER, points_vbo);
    glVertexAttribPointer (0, 3, GL_FLOAT, GL_FALSE, 0, NULL);

    glBindBuffer (GL_ARRAY_BUFFER, color_vbo);
    glVertexAttribPointer (1, 3, GL_FLOAT, GL_FALSE, 0, NULL);

    glEnableVertexAttribArray (0);
    glEnableVertexAttribArray (1);

    GLuint square_in_middle_vao = 0;
    glGenVertexArrays (1, &square_in_middle_vao);
    glBindVertexArray (square_in_middle_vao);

    glBindBuffer (GL_ARRAY_BUFFER, square_in_middle_points_vbo);
    glVertexAttribPointer (0, 3, GL_FLOAT, GL_FALSE, 0, NULL);

    glBindBuffer (GL_ARRAY_BUFFER, square_in_middle_color_vbo);
    glVertexAttribPointer (1, 3, GL_FLOAT, GL_FALSE, 0, NULL);

    glEnableVertexAttribArray (0);
    glEnableVertexAttribArray (1);

    std::string vertex_shader_str = readFile("./test.vert");
    std::string fragment_shader_str = readFile("./test.frag");
    const char* vertex_shader = vertex_shader_str.c_str();
    const char* fragment_shader = fragment_shader_str.c_str();

    GLuint vs = glCreateShader (GL_VERTEX_SHADER);
    glShaderSource (vs, 1, &vertex_shader, NULL);
    glCompileShader (vs);

    GLuint fs = glCreateShader (GL_FRAGMENT_SHADER);
    glShaderSource (fs, 1, &fragment_shader, NULL);
    glCompileShader(fs);

    GLuint shader_programme = glCreateProgram();
    glAttachShader (shader_programme, fs);
    glAttachShader (shader_programme, vs);
    glLinkProgram (shader_programme);

    float translation_matrix[] = {
        1.0f, 0.0f, 0.0f, 0.0f,
        0.0f, 1.0f, 0.0f, 0.0f,
        0.0f, 0.0f, 1.0f, 0.0f,
        0.01f, 0.0f, 0.0f, 1.0f
    };

    float translation_speed = 0.5f;
    float last_position = 0.0f;

    float camera_speed = 1.0f;
    float camera_yaw_speed = 10.0f;

    float camera_position[] = {0.0f, 0.0f, 2.0f};
    float camera_yaw = 0.0f;

    mat4 T = translate(identity_mat4(), vec3 (-camera_position[0], -camera_position[1], -camera_position[2]));
    mat4 R = rotate_y_deg(identity_mat4(), -camera_yaw);
    mat4 view_mat = R * T;

    float near = 0.1f;
    float far = 100.0f;
    float fov = 67.0f * ONE_DEG_IN_RAD;
    float aspect = (float)width / (float)height;
    float range = tan(fov * 0.5f) * near;
    float Sx = (2.0f * near) / (range * aspect + range * aspect);
    float Sy = near / range;
    float Sz = -(far + near) / (far - near);
    float Pz = -(2.0f * far * near) / (far - near);

    float proj_mat[] = {
        Sx, 0.0f, 0.0f, 0.0f,
        0.0f, Sy, 0.0f, 0.0f,
        0.0f, 0.0f, Sz, -1.0f,
        0.0f, 0.0f, Pz, 0.0f
    };

    int view_mat_location = glGetUniformLocation(shader_programme, "view");
    glUseProgram(shader_programme);
    glUniformMatrix4fv(view_mat_location, 1, GL_FALSE, view_mat.m);

    int proj_mat_location = glGetUniformLocation(shader_programme, "proj");
    glUseProgram(shader_programme);
    glUniformMatrix4fv(proj_mat_location, 1, GL_FALSE, proj_mat);

    int translation_matrix_location = glGetUniformLocation(shader_programme, "translate");
    glUseProgram(shader_programme);
    glUniformMatrix4fv(translation_matrix_location, 1, GL_FALSE, translation_matrix);

    while (!glfwWindowShouldClose (window)) {
        static double previous_seconds = glfwGetTime();
        double current_seconds = glfwGetTime();
        double elapsed_seconds = current_seconds - previous_seconds;
        previous_seconds = current_seconds;

        bool cam_moved = false;
        if (glfwGetKey(window, GLFW_KEY_A)) {
            camera_position[0] -= camera_speed * elapsed_seconds; 
            cam_moved = true;
        }
        if (glfwGetKey(window, GLFW_KEY_D)) {
            camera_position[0] += camera_speed * elapsed_seconds; 
            cam_moved = true;
        }
        if (glfwGetKey(window, GLFW_KEY_W)) {
            camera_position[1] += camera_speed * elapsed_seconds; 
            cam_moved = true;
        }
        if (glfwGetKey(window, GLFW_KEY_S)) {
            camera_position[1] -= camera_speed * elapsed_seconds; 
            cam_moved = true;
        }
        if (glfwGetKey(window, GLFW_KEY_LEFT)) {
            camera_yaw += camera_yaw_speed * elapsed_seconds;
            cam_moved = true;
        }
        if (glfwGetKey(window, GLFW_KEY_RIGHT)) {
            camera_yaw -= camera_yaw_speed * elapsed_seconds;
            cam_moved = true;
        }

        if (cam_moved) {
            mat4 T = translate(identity_mat4(), vec3(-camera_position[0], -camera_position[1], -camera_position[2]));
            mat4 R = rotate_y_deg(identity_mat4(), -camera_yaw);
            mat4 view_mat = R * T;
            glUniformMatrix4fv(view_mat_location, 1, GL_FALSE, view_mat.m);
        }

        glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glUseProgram (shader_programme);

        if (fabs(last_position) > 0.38) {
            translation_speed *= -1;
        }

        translation_matrix[12] = 0;
        glUniformMatrix4fv(translation_matrix_location, 1, GL_FALSE, translation_matrix);
        
        glBindVertexArray (vao);
        glDrawArrays (GL_TRIANGLES, 0, 12);

        translation_matrix[12] = elapsed_seconds * translation_speed + last_position;
        glUniformMatrix4fv(translation_matrix_location, 1, GL_FALSE, translation_matrix);
        last_position = translation_matrix[12];

        glBindVertexArray (square_in_middle_vao);
        glDrawArrays (GL_TRIANGLES, 0, 6);

        glfwPollEvents ();
        glfwSwapBuffers (window);

        if (GLFW_PRESS == glfwGetKey(window, GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose (window, 1);
        }
    }


    glfwTerminate();
    return 0;
}
