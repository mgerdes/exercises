typedef struct SphereObject {
    GLuint vao;
    GLuint model_mat_location;
    int vertex_count;
    Vec* center;
    double r, direction, speed;
} SphereObject;

SphereObject* create_sphere_object(GLuint shader_program, Vec* center, double radius);
void draw_sphere_object(SphereObject* sphere);
