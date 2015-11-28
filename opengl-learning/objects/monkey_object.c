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

MonkeyObject* create_monkey_object(GLuint shader_program) {
    MonkeyObject* monkey = malloc(sizeof(MonkeyObject));
    load_mesh("objects/monkey.dae", &monkey->vao, &monkey->vertex_count, &monkey->root_node, monkey->bone_offset_mats);

    monkey->speed = 0.02;
    monkey->direction = -1;

    char name[64];
    //Mat* identity = create_translation_mat(1,1,1);
    Mat* identity = identity_mat();; 
    for (int i = 0; i < 64; i++) {
        sprintf(name, "bone_matrices[%i]", i);
        monkey->bone_matrices_locations[i] = glGetUniformLocation(shader_program, name);
        glUniformMatrix4fv(monkey->bone_matrices_locations[i], 1, GL_FALSE, identity->m);
    }

    return monkey;
}

void draw_monkey_object(MonkeyObject* monkey) {
    glBindVertexArray(monkey->vao);
    glDrawArrays(GL_TRIANGLES, 0, monkey->vertex_count);
}

void animate_monkey_object(MonkeyObject* monkey) {
    monkey->theta += monkey->direction * monkey->speed;
    if (abs(monkey->theta) > 0.02) {
        monkey->direction *= -1;
    }

    Mat* head_mat = create_rotation_mat(&z_axis, monkey->theta);        
    glUniformMatrix4fv(monkey->bone_matrices_locations[0], 1, GL_FALSE, head_mat->m);
    Mat* ears_mat = mat_times_mat(head_mat, create_rotation_mat(&x_axis, monkey->theta));
    glUniformMatrix4fv(monkey->bone_matrices_locations[1], 1, GL_FALSE, ears_mat->m);
    glUniformMatrix4fv(monkey->bone_matrices_locations[2], 1, GL_FALSE, ears_mat->m);
}
