typedef struct SphereObject {
    GLuint vao;
    int vertex_count;
    double r;
    int p;
} SphereObject;

SphereObject* create_sphere_object(double, int);
void draw_sphere_object(SphereObject* sphere);
