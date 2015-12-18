typedef struct Model {
    int num_meshes;
    Mesh** meshes;    
} Model;

Model* create_model(char* filename);
void draw_model(Model* model);
