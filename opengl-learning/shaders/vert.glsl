#version 330
layout(location=0) in vec3 vertex_position;
layout(location=1) in vec3 vertex_normal;
layout(location=2) in int bone_id;

uniform mat4 view_mat, proj_mat;
uniform mat4 bone_matrices[64];

out vec3 color;

void main () {
    color = vec3(0,0,0);
    if (bone_id == 0) {
        color.r = 1;
    }
    if (bone_id == 1) {
        color.g = 1;
    }
    if (bone_id == 2) {
        color.b = 1;
    }
    if (bone_id == 3) {
        color.g = 1;
    }
    color = vertex_normal;
    //gl_Position = proj_mat * view_mat * bone_matrices[bone_id] * vec4(vertex_position, 1.0);
    gl_Position = proj_mat * view_mat * vec4(vertex_position, 1.0);
}

