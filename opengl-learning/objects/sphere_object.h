typedef struct SphereObject {
    GLuint vao;
    int vertex_count;
} SphereObject;

SphereObject* create_sphere_object(void);
void draw_sphere_object(SphereObject* sphere);
