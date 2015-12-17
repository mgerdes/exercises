typedef struct SphereObject {
    GLuint vao;
    GLuint model_mat_location;
    int vertex_count;
    Vec* center;
    Vec* next_center;
    double r;
    Vec* velocity;
    Vec* next_velocity;
    double rotation_speed;
    double theta_i;
    double current_time;
    double next_time;
    double last_collision_time;
} SphereObject;

SphereObject* create_sphere_object(GLuint shader_program, Vec* center, double radius);
void draw_sphere_object(SphereObject* sphere);
