typedef struct PlaneObject {
    GLuint vao;
    GLuint model_mat_location;
    int vertex_count;
    Vec* center;
    Vec* normal;
} PlaneObject;

PlaneObject* create_plane_object(GLuint shader_program, Vec* center, Vec* normal, 
        double width, double height);
void draw_plane_object(PlaneObject* plane);
