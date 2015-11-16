#version 330
in vec3 vertex_position;
in vec3 vertex_normal;
in int bone_id;

out vec3 color;

uniform mat4 view_mat, proj_mat;

void main () {
    color = vertex_normal;
    gl_Position = proj_mat * view_mat * vec4(vertex_position, 1.0);
}

