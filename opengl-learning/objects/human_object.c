#include <assimp/cimport.h>
#include <assimp/scene.h>
#include <assimp/postprocess.h>
#include <GL/glew.h>
#include <GLFW/glfw3.h>
#include <stdlib.h>
#include "human_object.h"
#include "gl_utils.h"

HumanObject* create_human_object() {
    HumanObject* human = malloc(sizeof(HumanObject));
    load_mesh("objects/human.obj", &human->vao, &human->vertex_count);
    return human;
}

void draw_human_object(HumanObject* human) {
    glBindVertexArray(human->vao);
    glDrawArrays(GL_LINES, 0, human->vertex_count);
}
