#include <assimp/cimport.h>
#include <assimp/scene.h>
#include <assimp/postprocess.h>
#include <GL/glew.h>
#include <GLFW/glfw3.h>
#include <stdlib.h>
#include "camera.h"
#include "maths.h"
#include "mesh_import.h"
#include "human_object.h"

HumanObject* create_human_object() {
    HumanObject* human = malloc(sizeof(HumanObject));
    human->bone_offset_mats[16]; 
    load_mesh("objects/human.obj", &human->vao, &human->vertex_count, &human->root_node, human->bone_offset_mats);
    return human;
}

void draw_human_object(HumanObject* human) {
    glBindVertexArray(human->vao);
    glDrawArrays(GL_LINES, 0, human->vertex_count);
}

void animate_human_object(HumanObject* human) {

}
