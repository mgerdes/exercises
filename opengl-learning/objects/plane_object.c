#include <GL/glew.h>
#include <GLFW/glfw3.h>
#include <stdlib.h>
#include <stdio.h>
#include "camera.h"
#include <math.h>
#include "maths.h"
#include "plane_object.h"

PlaneObject* create_plane_object(GLuint shader_program, Vec* center, Vec* normal, 
        double width, double height) {
    GLfloat points[] = 
    {
        -width / 2.0, -height / 2.0, 0,
        width / 2.0, -height / 2.0, 0,
        -width / 2.0, height / 2.0, 0,

        width / 2.0, -height / 2.0, 0,
        -width / 2.0, height / 2.0, 0,
        width / 2.0, height / 2.0, 0,
    };

    GLfloat normals[] = 
    {
        normal->x, normal->y, normal->z,
        normal->x, normal->y, normal->z,
        normal->x, normal->y, normal->z,
        
        normal->x, normal->y, normal->z,
        normal->x, normal->y, normal->z,
        normal->x, normal->y, normal->z,
    };

    GLuint points_vbo, normals_vbo, vao;

    glGenBuffers(1, &points_vbo);
    glBindBuffer(GL_ARRAY_BUFFER, points_vbo);
    glBufferData(GL_ARRAY_BUFFER, sizeof(points), points, GL_STATIC_DRAW);

    glGenBuffers(1, &normals_vbo);
    glBindBuffer(GL_ARRAY_BUFFER, normals_vbo);
    glBufferData(GL_ARRAY_BUFFER, sizeof(normals), normals, GL_STATIC_DRAW);

    glGenVertexArrays(1, &vao);
    glBindVertexArray(vao);

    glBindBuffer(GL_ARRAY_BUFFER, points_vbo);
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, NULL);
    glEnableVertexAttribArray(0);

    glBindBuffer(GL_ARRAY_BUFFER, normals_vbo);
    glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 0, NULL);
    glEnableVertexAttribArray(1);

    PlaneObject* plane = malloc(sizeof(PlaneObject));
    plane->vao = vao;
	plane->model_mat_location = glGetUniformLocation(shader_program, "model_mat");
    plane->vertex_count = 6;
    plane->center = center;
    plane->normal = normal;

    return plane;
}

void draw_plane_object(PlaneObject* p) {
    Mat* translation_mat = create_translation_mat(p->center->x, p->center->y, p->center->z);
    Mat* rotation_mat = create_rotation_mat(p->normal, M_PI/2);
    Mat* model_mat = mat_times_mat(translation_mat, rotation_mat);
    glUniformMatrix4fv(p->model_mat_location, 1, GL_FALSE, model_mat->m);

    glBindVertexArray(p->vao);
    glDrawArrays(GL_TRIANGLES, 0, p->vertex_count);
}
