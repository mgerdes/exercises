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
#include "monkey_object.h"

static double speed = 0.12;
static double theta = 0;
static GLuint bone_matrices_locations[64];
static GLuint shader_program;

MonkeyObject* create_monkey_object(GLuint shader_program_i) {
    shader_program = shader_program_i;

    MonkeyObject* monkey = malloc(sizeof(MonkeyObject));
    load_mesh("objects/monkey.dae", &monkey->vao, &monkey->vertex_count, &monkey->root_node, monkey->bone_offset_mats);

    char name[64];
    //Mat* identity = create_translation_mat(1,1,1);
    Mat* identity = identity_mat();; 
    for (int i = 0; i < 64; i++) {
        sprintf(name, "bone_matrices[%i]", i);
        bone_matrices_locations[i] = glGetUniformLocation(shader_program, name);
        glUniformMatrix4fv(bone_matrices_locations[i], 1, GL_FALSE, identity->m);
    }

    return monkey;
}

void draw_monkey_object(MonkeyObject* monkey) {
    glBindVertexArray(monkey->vao);
    glDrawArrays(GL_TRIANGLES, 0, monkey->vertex_count);
}

void animate_monkey_object(MonkeyObject* monkey) {
    static int direction = -1;
    theta += direction * speed;
    Mat* hi = create_rotation_mat(&z_axis, theta);        
    glUniformMatrix4fv(bone_matrices_locations[0], 1, GL_FALSE, hi->m);
}
