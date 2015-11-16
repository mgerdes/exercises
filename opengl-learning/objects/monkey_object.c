#include <assimp/cimport.h>
#include <assimp/scene.h>
#include <assimp/postprocess.h>
#include <GL/glew.h>
#include <GLFW/glfw3.h>
#include <stdlib.h>
#include "monkey_object.h"
#include "gl_utils.h"

MonkeyObject* create_monkey_object() {
    MonkeyObject* monkey = malloc(sizeof(MonkeyObject));
    load_mesh("objects/monkey.dae", &monkey->vao, &monkey->vertex_count);
    return monkey;
}

void draw_monkey_object(MonkeyObject* monkey) {
    glBindVertexArray(monkey->vao);
    glDrawArrays(GL_TRIANGLES, 0, monkey->vertex_count);
}
