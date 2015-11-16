typedef struct MonkeyObject {
    GLuint vao;
    int vertex_count;
} MonkeyObject;

MonkeyObject* create_monkey_object();
void draw_monkey_object(MonkeyObject* human);
