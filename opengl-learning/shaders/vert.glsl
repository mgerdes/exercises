#version 330
in vec3 vertex_position;
in vec3 vertex_normal;
in int bone_id;

uniform mat4 view_mat, proj_mat;
uniform mat4 bone_matrices[64];

out vec3 color;

void main () {
    color = vertex_normal;
    gl_Position = proj_mat * view_mat * bone_matrices[bone_id] * vec4(vertex_position, 1.0);
}
