typedef struct HumanObject {
    GLuint vao;
    int vertex_count;
} HumanObject;

HumanObject* create_human_object();
void draw_human_object(HumanObject* human);
