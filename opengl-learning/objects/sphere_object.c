#include <GL/glew.h>
#include <GLFW/glfw3.h>
#include <stdlib.h>
#include <stdio.h>
#include "camera.h"
#include <math.h>
#include "maths.h"
#include "mesh_import.h"
#include "sphere_object.h"

SphereObject* create_sphere_object() {
    SphereObject* sphere = malloc(sizeof(SphereObject));
    load_mesh("objects/sphere.obj", &sphere->vao, &sphere->vertex_count, 0, 0);
    return sphere;
}

void draw_sphere_object(SphereObject* sphere) {
    glBindVertexArray(sphere->vao);
    glDrawArrays(GL_TRIANGLES, 0, sphere->vertex_count);
}
