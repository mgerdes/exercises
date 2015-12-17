#include <GL/glew.h>
#include <GLFW/glfw3.h>
#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include "camera.h"
#include "maths.h"
#include "sphere_object.h"
#include "plane_object.h"
#include "sphere_simulation.h"

void handle_collision(SphereObject* sphere, SphereList* sphere_list);

SphereSimulation* create_sphere_simulation(GLuint shader_program) {
    SphereSimulation* s = malloc(sizeof(SphereSimulation));

    s->plane1 = create_plane_object(
            shader_program,
            create_vec(0,0,-1,0),
            create_vec(0,0,1,0),
            20, 20);
    s->plane2 = create_plane_object(
            shader_program,
            create_vec(-10,0,0,0),
            create_vec(0,1,0,0),
            20, 20);
    s->plane3 = create_plane_object(
            shader_program,
            create_vec(10,0,0,0),
            create_vec(0,1,0,0),
            20, 20);
    s->plane4 = create_plane_object(
            shader_program,
            create_vec(0,-10,0,0),
            create_vec(1,0,0,0),
            20, 20);
    s->plane5 = create_plane_object(
            shader_program,
            create_vec(0,10,0,0),
            create_vec(1,0,0,0),
            20, 20);

    s->sphere_list = 0;
    s->num_of_spheres = 0;
    s->time_last_drawn = glfwGetTime();
    s->next_collision_time = 0;
    s->impact_time = 0;
    return s;
}

void add_sphere(SphereSimulation* sphere_sim, SphereObject* sphere) {
    SphereList* new_sphere_list = malloc(sizeof(SphereList));
    new_sphere_list->sphere = sphere;
    new_sphere_list->rest_of_spheres = sphere_sim->sphere_list;
    sphere_sim->sphere_list = new_sphere_list;
    sphere_sim->num_of_spheres++;
}

void draw_sphere_simulation(SphereSimulation* s) {
    double current_time = glfwGetTime();
    double elapsed_time = current_time - s->time_last_drawn; 
    s->time_last_drawn = current_time;

    draw_plane_object(s->plane1);
    draw_plane_object(s->plane2);
    draw_plane_object(s->plane3);
    draw_plane_object(s->plane4);
    draw_plane_object(s->plane5);

    SphereList* sphere_list = s->sphere_list;
    for (int i = 0; i < s->num_of_spheres; i++) {
        SphereObject* sphere = sphere_list->sphere;
        sphere->current_time += elapsed_time;
        handle_collision(sphere, s->sphere_list);
        draw_sphere_object(sphere);
        sphere_list = sphere_list->rest_of_spheres;
    }
}

double sphere_plane_intersection_time(SphereObject* sphere, PlaneObject* plane) {
    double t;
    if (plane->normal->y == 1) {
        t = (plane->center->x - sphere->r - sphere->center->x) / sphere->velocity->x;
    } else {
        t = (plane->center->y - sphere->r - sphere->center->y) / sphere->velocity->y;
    }
    if (t < 0) {
        return 99999999;
    } else {
        return t;
    }
}

void reset_colliding_spheres(SphereObject* s1, SphereObject* s2) {
    if (s1) {
        s1->next_velocity = s1->velocity;
    }
    if (s2) {
        s2->next_velocity = s2->velocity;
    }
}

double get_smallest_impact_time(SphereSimulation* sim) {
    SphereList* sphere_list = sim->sphere_list; 

    double min_t = 9999999, t;
    SphereObject* colliding_sphere1 = 0;
    SphereObject* colliding_sphere2 = 0;

    while (sphere_list) {
        SphereObject* sphere = sphere_list->sphere;

        // intersect 4 planes
        t = sphere_plane_intersection_time(sphere, sim->plane2);
        if (t < min_t) {
            reset_colliding_spheres(colliding_sphere1, colliding_sphere2);

            colliding_sphere1 = sphere;
            colliding_sphere2 = 0;

            Vec* v = colliding_sphere1->velocity;
            colliding_sphere1->next_velocity = create_vec(-v->x, v->y, v->z, 1);

            min_t = t;
        }
        t = sphere_plane_intersection_time(sphere, sim->plane3);
        if (t < min_t) {
            reset_colliding_spheres(colliding_sphere1, colliding_sphere2);

            colliding_sphere1 = sphere;
            colliding_sphere2 = 0;

            Vec* v = colliding_sphere1->velocity;
            colliding_sphere1->next_velocity = create_vec(-v->x, v->y, v->z, 1);

            min_t = t;
        }
        t = sphere_plane_intersection_time(sphere, sim->plane4);
        if (t < min_t) {
            reset_colliding_spheres(colliding_sphere1, colliding_sphere2);

            colliding_sphere1 = sphere;
            colliding_sphere2 = 0;

            Vec* v = colliding_sphere1->velocity;
            colliding_sphere1->next_velocity = create_vec(v->x, -v->y, v->z, 1);

            min_t = t;
        }
        t = sphere_plane_intersection_time(sphere, sim->plane5);
        if (t < min_t) {
            reset_colliding_spheres(colliding_sphere1, colliding_sphere2);

            colliding_sphere1 = sphere;
            colliding_sphere2 = 0;

            Vec* v = colliding_sphere1->velocity;
            colliding_sphere1->next_velocity = create_vec(v->x, -v->y, v->z, 1);

            min_t = t;
        }

        sphere_list = sphere_list->rest_of_spheres;
    }

    return min_t;
}

void handle_collision(SphereObject* sphere, SphereList* sphere_list) {
    double current_time = glfwGetTime();
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

    // Check for collision with wall.
    double max = 9.5;
    if (fabs(x) > max || fabs(y) > max || fabs(z) > max) {
        free(sphere->center);
        free(sphere->velocity);

        sphere->center = create_vec(x, y, z, 0);
        sphere->theta_i = sphere->theta_i + sphere->rotation_speed * t;
        sphere->current_time = 0;

        if (fabs(x) > max) {
            sphere->velocity = create_vec(-vx, vy, vz, 1);
        }
        if (fabs(y) > max) {
            sphere->velocity = create_vec(vx, -vy, vz, 1);
        }
        if (fabs(z) > max) {
            sphere->velocity = create_vec(vx, vy, -vz, 1);
        }
    }
    if (sphere->last_collision_time + 0.2 > current_time) {
        return;
    }

    // Check for collision with other sphere.
    while (sphere_list) {
        SphereObject* sphere_o = sphere_list->sphere;
        if (sphere_o != sphere) {
            double t_o = sphere_o->current_time;

            double x0_o = sphere_o->center->x;
            double y0_o = sphere_o->center->y;
            double z0_o = sphere_o->center->z;

            double vx_o = sphere_o->velocity->x;
            double vy_o = sphere_o->velocity->y;
            double vz_o = sphere_o->velocity->z;

            double x_o = x0_o + vx_o * t_o;
            double y_o = y0_o + vy_o * t_o;
            double z_o = z0_o + vz_o * t_o;

            if (fabs(x - x_o)-0.3 < sphere->r && 
                    fabs(y - y_o)-0.3 < sphere->r && 
                    fabs(z - z_o)-0.3 < sphere->r) {
                Vec* x1 = create_vec(x,y,z,0);
                Vec* x2 = create_vec(x_o,y_o,z_o,0);
                Vec* v1 =  sphere->velocity;
                Vec* v2 =  sphere_o->velocity;

                Vec* temp11 = vec_minus_vec(x1, x2);
                Vec* temp12 = vec_minus_vec(v1, v2);
                double divisor1 = dot_vec(temp11, temp11);
                double scale_temp1 = dot_vec(temp11, temp12) / divisor1;
                Vec* temp13 = scale_vec(temp11, scale_temp1);

                Vec* temp21 = vec_minus_vec(x2, x1);
                Vec* temp22 = vec_minus_vec(v2, v1);
                double divisor2 = dot_vec(temp21, temp21);
                double scale_temp2 = dot_vec(temp21, temp22) / divisor2;
                Vec* temp23 = scale_vec(temp21, scale_temp2);

                Vec* v1_p = vec_minus_vec(v1, temp13);
                Vec* v2_p = vec_minus_vec(v2, temp23);

                sphere->center = x1;
                sphere_o->center = x2;

                sphere->velocity = v1_p;
                sphere_o->velocity = v2_p;

                sphere->current_time = 0;
                sphere_o->current_time = 0;

                sphere->last_collision_time = current_time;
                sphere_o->last_collision_time = current_time;

                sphere->theta_i = sphere->theta_i + sphere->rotation_speed * t;
                sphere_o->theta_i = sphere_o->theta_i + sphere_o->rotation_speed * t;

                return;                     
            }
        }
        sphere_list = sphere_list->rest_of_spheres;
    }
}
