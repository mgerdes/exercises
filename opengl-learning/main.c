#include <GL/glew.h>
#include <GLFW/glfw3.h>
#include <stdio.h>
#include <stdlib.h>
#include "gl_utils.h"
#include "camera.h"
#include "maths.h"
#include "mesh_import.h"
#include "mesh.h"
#include "model.h"

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
	GLint proj_mat_location = glGetUniformLocation(shader_program, "proj_mat");
	GLint view_mat_location = glGetUniformLocation(shader_program, "view_mat");

    Mat* proj_mat = create_perspective_mat(67.0, 1.0, 0.1, 1000.0);
    Mat* view_mat = create_look_at_mat(camera);

	glUniformMatrix4fv(proj_mat_location, 1, GL_FALSE, proj_mat->m);
    glUniformMatrix4fv(view_mat_location,1, GL_FALSE, view_mat->m);

    Model* balls = create_model("objects/models/pool.obj", shader_program);
    Model* table = create_model("objects/models/hi.obj", shader_program);

    while (!glfwWindowShouldClose(window)) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        handle_input();

        view_mat = create_look_at_mat(camera);
        glUniformMatrix4fv(view_mat_location, 1, GL_FALSE, view_mat->m);

        draw_model(balls);
        draw_model(table);

        glfwPollEvents();
        glfwSwapBuffers(window);
    }

    glfwTerminate();
}
