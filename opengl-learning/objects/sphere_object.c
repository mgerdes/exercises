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
    sphere->velocity = create_vec(5,5,0,1);
    sphere->current_time = 0;
    sphere->rotation_speed = 1;
    sphere->theta_i = 0;
    sphere->last_collision_time = 0;
    sphere->next_center = 0;
    sphere->next_velocity = 0;

    load_mesh("objects/models/sphere.obj", &sphere->vao, &sphere->vertex_count, 0, 0);
	sphere->model_mat_location = glGetUniformLocation(shader_program, "model_mat");
    return sphere;
}

void draw_sphere_object(SphereObject* sphere) {
    double t = sphere->current_time;

    double x0 = sphere->center->x;
    double y0 = sphere->center->y;
    double z0 = sphere->center->z;

    double vx = sphere->velocity->x;
    double vy = sphere->velocity->y;
    double vz = sphere->velocity->z;

    double x = x0 + vx * t;
    double y = y0 + vy * t;
    double z = z0 + vz * t;

    double theta = sphere->theta_i + sqrt(vx*vx + vy*vy) * t;

    Vec* rotation_vec = cross_vec(sphere->velocity, create_vec(0,0,1,1));
    Mat* rotation_mat = create_rotation_mat(normalize_vec(rotation_vec), theta);
    Mat* translation_mat = create_translation_mat(x, y, z);
    Mat* scale_mat = create_scale_mat(sphere->r, sphere->r, sphere->r);

    Mat* temp = mat_times_mat(translation_mat, scale_mat);
    Mat* model_mat = mat_times_mat(temp, rotation_mat);

    glUniformMatrix4fv(sphere->model_mat_location, 1, GL_FALSE, model_mat->m);

    glBindVertexArray(sphere->vao);
    glDrawArrays(GL_TRIANGLES, 0, sphere->vertex_count);
}
