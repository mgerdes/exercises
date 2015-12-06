#include <GL/glew.h>
#include <GLFW/glfw3.h>
#include <stdlib.h>
#include <stdio.h>
#include "camera.h"
#include <math.h>
#include "maths.h"
#include "mesh_import.h"
#include "sphere_object.h"

SphereObject* create_sphere_object(GLuint shader_program, Vec* center, double radius) {
    SphereObject* sphere = malloc(sizeof(SphereObject));

    sphere->center = center;
    sphere->r = radius;

    sphere->direction = 1;
    sphere->speed = 1;

    load_mesh("objects/sphere.obj", &sphere->vao, &sphere->vertex_count, 0, 0);
	sphere->model_mat_location = glGetUniformLocation(shader_program, "model_mat");
    return sphere;
}

void draw_sphere_object(SphereObject* sphere) {
    Mat* translation_mat = 
        create_translation_mat(sphere->center->x, sphere->center->y, sphere->center->z);
    Mat* scale_mat = create_scale_mat(sphere->r, sphere->r, sphere->r);
    Mat* rotation_mat = create_rotation_mat(&y_axis, sphere->direction);
    sphere->direction += 0.1;

    Mat* temp = mat_times_mat(scale_mat, translation_mat);
    Mat* model_mat = mat_times_mat(temp, rotation_mat);

    glUniformMatrix4fv(sphere->model_mat_location, 1, GL_FALSE, model_mat->m);

    glBindVertexArray(sphere->vao);
    glDrawArrays(GL_TRIANGLES, 0, sphere->vertex_count);
}
