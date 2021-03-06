typedef struct Model {
    int num_meshes;
    Mesh** meshes;
    GLuint shader_program;
} Model;

Model* create_model(char* filename, GLint shader_program);
void draw_model(Model* model);
