#include "mesh.h"

Mesh* create_mesh(Vertex** vertices, int num_vertices) {
    GLfloat* positions = (GLfloat*) malloc(num_vertices * 3 * sizeof(GLfloat));
    GLfloat* normals = (GLfloat*) malloc(num_vertices * 3 * sizeof(GLfloat));

    for (int i = 0; i < num_vertices; i++) {
        positions[i * 3] = vertices[i]->position->x; 
        positions[i * 3 + 1] = vertices[i]->position->y; 
        positions[i * 3 + 2] = vertices[i]->position->z; 

        normals[i * 3] = vertices[i]->normal->x; 
        normals[i * 3 + 1] = vertices[i]->normal->y; 
        normals[i * 3 + 2] = vertices[i]->normal->z; 
    }

    GLuint mesh_vao;
    glGenVertexArrays(1, &mesh_vao);
    glBindVertexArray(mesh_vao);

    GLuint positions_vbo;
    glGenBuffers(1, &positions_vbo);
    glBindBuffer(GL_ARRAY_BUFFER, positions_vbo);
    glBufferData(GL_ARRAY_BUFFER,
            3 * num_vertices * sizeof (GLfloat),
            positions,
            GL_STATIC_DRAW);
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, NULL);
    glEnableVertexAttribArray(0);

    GLuint normals_vbo;
    glGenBuffers(1, &normals_vbo);
    glBindBuffer(GL_ARRAY_BUFFER, normals_vbo);
    glBufferData(GL_ARRAY_BUFFER,
            3 * num_vertices * sizeof (GLfloat),
            normals,
            GL_STATIC_DRAW);
    glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 0, NULL);
    glEnableVertexAttribArray(1);

    glBindVertexArray(0);

    Mesh* mesh = malloc(sizeof(Mesh));
    mesh->vao = mesh_vao;
    mesh->num_vertices = num_vertices;
    return mesh;
}

void draw_mesh(Mesh* mesh) {
    glBindVertexArray(mesh->vao);
    glDrawArrays(GL_TRIANGLES, 0, mesh->vertex_count);
    glBindVertexArray(0);
}
