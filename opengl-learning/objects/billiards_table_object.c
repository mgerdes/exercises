#include <assimp/cimport.h>
#include <assimp/scene.h>
#include <assimp/postprocess.h>
#include <GL/glew.h>
#include <GLFW/glfw3.h>
#include <stdlib.h>
#include <stdio.h>
#include "camera.h"
#include "maths.h"
#include "mesh_import.h"
#include "billiards_table_object.h"

BilliardsTableObject* create_billiards_table_object(GLuint shader_program) {
    BilliardsTableObject* billiards_table = malloc(sizeof(BilliardsTableObject));
    load_mesh("objects/models/billiards_table.3DS", &billiards_table->vao, &billiards_table->vertex_count, 0, 0);
    return billiards_table;
}

void draw_billiards_table_object(BilliardsTableObject* billiards_table) {
    glBindVertexArray(billiards_table->vao);
    glDrawArrays(GL_TRIANGLES, 0, billiards_table->vertex_count);
}
