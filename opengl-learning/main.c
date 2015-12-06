#include <GL/glew.h>
#include <GLFW/glfw3.h>
#include <stdio.h>
#include <stdlib.h>
#include "gl_utils.h"
#include "camera.h"
#include "maths.h"
#include "mesh_import.h"
#include "human_object.h"
#include "monkey_object.h"
#include "sphere_object.h"
#include "plane_object.h"

Camera* camera;

void handle_input() {
    double current_seconds = glfwGetTime();
    static double last_key_press;

    if (current_seconds > last_key_press + 0.15) {
        if (glfwGetKey(window, GLFW_KEY_A)) {
            rotate_camera_left(camera, 0.04);
        }
        if (glfwGetKey(window, GLFW_KEY_D)) {
            rotate_camera_left(camera, -0.04);
        }
        if (glfwGetKey(window, GLFW_KEY_W)) {
            rotate_camera_up(camera, -0.04);
        }
        if (glfwGetKey(window, GLFW_KEY_S)) {
            rotate_camera_up(camera, 0.04);
        }
        if (glfwGetKey(window, GLFW_KEY_COMMA)) {
            zoom_camera(camera, 1.01);
        }
        if (glfwGetKey(window, GLFW_KEY_PERIOD)) {
            zoom_camera(camera, 0.99);
        }
    }
}

void init() {
    window_height = window_width = 1000;
    camera = create_default_camera();
}

int main() {
    init();
    init_gl("opengl");

    GLuint shader_program = create_shader_program("shaders/frag.glsl", "shaders/vert.glsl");
	GLuint proj_mat_location = glGetUniformLocation(shader_program, "proj_mat");
	GLuint view_mat_location = glGetUniformLocation(shader_program, "view_mat");

    Mat* proj_mat = create_perspective_mat(67.0, 1.0, 0.1, 1000.0);   
    Mat* view_mat = create_look_at_mat(camera);

	glUniformMatrix4fv(proj_mat_location, 1, GL_FALSE, proj_mat->m);
    glUniformMatrix4fv(view_mat_location,1, GL_FALSE, view_mat->m);

    PlaneObject* plane = create_plane_object(shader_program, 
            create_vec(0,0,-0.5,0), 
            create_vec(0,0,1,0), 
            20, 20, 1);
    SphereObject* sphere = create_sphere_object(shader_program,
            create_vec(0,0,0,0),
            0.5);
    SphereObject* sphere2 = create_sphere_object(shader_program,
            create_vec(4,4,0,0),
            0.5);
    SphereObject* sphere3 = create_sphere_object(shader_program,
            create_vec(8,8,0,0),
            0.5);

    while (!glfwWindowShouldClose(window)) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        handle_input();

        view_mat = create_look_at_mat(camera);
        glUniformMatrix4fv(view_mat_location, 1, GL_FALSE, view_mat->m);

        draw_sphere_object(sphere);
        draw_sphere_object(sphere2);
        draw_sphere_object(sphere3);
        draw_plane_object(plane);

        glfwPollEvents();
        glfwSwapBuffers(window);
    }

    glfwTerminate();
}
