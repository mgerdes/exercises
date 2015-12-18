#include "model.h"

static Mesh* process_mesh(struct aiMesh* mesh, struct aiScene* scene) {
    Vertex** vertices = malloc(sizeof(Vertex*) * mesh->mNumVertices);
    for (int i = 0; i < mesh->mNumVertices; i++) {
        vertices[i] = malloc(sizeof(Vertex));
    }

    // Process vertices
    if (mesh->mVertices && mesh->mNumVertices > 0) {
        for (int i = 0; i < mesh->mNumVertices; i++) {
            struct aiVector3D position = mesh->mVertices[i];
            vertices[i]->position = create_vec(position.x, position.y, position.z, 1.0);
        }
    } else {
        gl_log(INFO, "A mesh was processed with no vertices.");
    }

    // Process normals
    if (mesh->mNormals) {
        for (int i = 0; i < mesh->mNumVertices; i++) {
            struct aiVector3D normal = mesh->mVertices[i];
            vertices[i]->normal = create_vec(normal.x, normal.y, normal.z, 1.0);
        }
    } else {
        gl_log(INFO, "A mesh was processed with no normals.");
    }

    // Process texture coords
    if (mesh->mTextureCoords[0]) {
        for (int i = 0; i < mesh->mNumVertices; i++) {
            struct aiVector2D texture = mesh->mTextureCoords[0][i];
            vertices[i]->texture = create_vec(texture.x, texture.y, texture.z, 1.0);
        }
    } else {
        gl_log(INFO, "A mesh was processed with no texture coordinates.");
    }

    // --TODO-- Process materials

    return create_mesh(vertices, mesh->mNumVertices);
}

static void process_node(Model* model, struct aiNode* node, struct aiScene* scene) {
    for (int i = 0; i < node->mNumMeshes; i++) {
        struct aiMesh* mesh = scene->mMeshes[node->mMeshes[i]];
        process_mesh(mesh, scene);
    }

    for (int i = 0; i < node->mNumChildren; i++) {
        process_node(model, node->mChildren[i], scene);
    }
}

Model* create_model(char* filename) {
    struct aiScene* scene = aiImportFile(filename, aiProcess_Triangulate);
    if (!scene) {
        gl_log(ERROR, "Cannot open scene file: %s", filename);
        return 0;
    }

    gl_log(INFO, "Loading model: %s", filename);
    gl_log(INFO, "%d animations", scene->mNumAnimations);
    gl_log(INFO, "%d cameras", scene->mNumCameras);
    gl_log(INFO, "%d lights", scene->mNumLights);
    gl_log(INFO, "%d materials", scene->mNumMaterials);
    gl_log(INFO, "%d meshes", scene->mNumMeshes);
    gl_log(INFO, "%d textures", scene->mNumTextures);
    
    Model* model = malloc(sizeof(Model));
    model->num_meshes = scene->mNumMeshes;
    model->meshes = malloc(sizeof(Mesh*) * scene->mNumMeshes);

    process_node(model, scene->mRootNode, scene);

    return model;
}

void draw_model(Model* model) {
    for (int i = 0; i < model->num_meshes; i++) {
        draw_mesh(model->meshes[i]);
    }
}
