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
#include "human_object.h"

HumanObject* create_human_object(GLuint shader_program) {
    HumanObject* human = malloc(sizeof(HumanObject));
    human->bone_offset_mats[16]; 
    load_mesh("objects/models/human_bones.dae", &human->vao, &human->vertex_count, &human->root_node, human->bone_offset_mats);

    human->speed = 0.008;
    human->direction = -1;

    char name[64];
    Mat* identity = create_translation_mat(0, -10, 0);; 
    for (int i = 0; i < 64; i++) {
        sprintf(name, "bone_matrices[%i]", i);
        human->bone_matrices_locations[i] = glGetUniformLocation(shader_program, name);
        glUniformMatrix4fv(human->bone_matrices_locations[i], 1, GL_FALSE, identity->m);
    }

    return human;
}

void draw_human_object(HumanObject* human) {
    glBindVertexArray(human->vao);
    glDrawArrays(GL_TRIANGLES, 0, human->vertex_count);
}

void animate_human_object(HumanObject* human) {
    human->theta += human->direction * human->speed;
    if (human->theta*human->direction > 0.32) {
        human->direction *= -1;
    }

    Mat* head_mat = mat_times_mat(create_rotation_mat(&y_axis, human->theta),
            create_translation_mat(0,-10,0));        
    glUniformMatrix4fv(human->bone_matrices_locations[1], 1, GL_FALSE, head_mat->m);
}
