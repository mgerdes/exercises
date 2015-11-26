#include <GL/glew.h>
#include <GLFW/glfw3.h>
#include <stdlib.h>
#include <stdio.h>
#include "camera.h"
#include <math.h>
#include "maths.h"
#include "mesh_import.h"
#include "sphere_object.h"

/*
 * Code from https://gist.github.com/stuartjmoore/1076642
 */
SphereObject* create_sphere_object(double r, int p) {
    float theta1 = 0.0, theta2 = 0.0, theta3 = 0.0;
    float ex = 0.0f, ey = 0.0f, ez = 0.0f;
    float px = 0.0f, py = 0.0f, pz = 0.0f;
    GLfloat vertices[p*6+6], normals[p*6+6];

    for(int i = 0; i < p/2; ++i) {
        theta1 = i * (M_PI*2) / p - M_PI_2;
        theta2 = (i + 1) * (M_PI*2) / p - M_PI_2;

        for(int j = 0; j <= p; ++j) {
            theta3 = j * (M_PI*2) / p;

            ex = cosf(theta2) * cosf(theta3);
            ey = sinf(theta2);
            ez = cosf(theta2) * sinf(theta3);
            px = r * ex;
            py = r * ey;
            pz = r * ez;

            vertices[(6*j)+(0%6)] = px;
            vertices[(6*j)+(1%6)] = py;
            vertices[(6*j)+(2%6)] = pz;

            normals[(6*j)+(0%6)] = ex;
            normals[(6*j)+(1%6)] = ey;
            normals[(6*j)+(2%6)] = ez;

            ex = cosf(theta1) * cosf(theta3);
            ey = sinf(theta1);
            ez = cosf(theta1) * sinf(theta3);
            px = r * ex;
            py = r * ey;
            pz = r * ez;

            vertices[(6*j)+(3%6)] = px;
            vertices[(6*j)+(4%6)] = py;
            vertices[(6*j)+(5%6)] = pz;

            normals[(6*j)+(3%6)] = ex;
            normals[(6*j)+(4%6)] = ey;
            normals[(6*j)+(5%6)] = ez;
        }
    }

    SphereObject* s = malloc(sizeof(SphereObject));

    s->vertex_count = p*6+6;
    s->r = r;
    s->p = p;

    glGenVertexArrays(1, &s->vao);
    glBindVertexArray(s->vao);

    GLuint vbo;
    glGenBuffers(1, &vbo);
    glBindBuffer(GL_ARRAY_BUFFER, vbo);
    glBufferData(GL_ARRAY_BUFFER,
            (p*6+6) * sizeof (GLfloat),
            vertices,
            GL_STATIC_DRAW);
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, NULL);
    glEnableVertexAttribArray(0);

    glGenBuffers(1, &vbo);
    glBindBuffer(GL_ARRAY_BUFFER, vbo);
    glBufferData(GL_ARRAY_BUFFER,
            (p*6+6) * sizeof (GLfloat),
            normals,
            GL_STATIC_DRAW);
    glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 0, NULL);
    glEnableVertexAttribArray(1);

    return s;
}

void draw_sphere_object(SphereObject* sphere) {
    glBindVertexArray(sphere->vao);
    glDrawArrays(GL_TRIANGLE_STRIP, 0, (sphere->p + 1) * 2);
}
