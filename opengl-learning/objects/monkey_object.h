typedef struct MonkeyObject {
    GLuint vao;
    int vertex_count;
    Skeleton_Node* root_node;
    Mat* bone_offset_mats[16];
} MonkeyObject;

MonkeyObject* create_monkey_object();
void draw_monkey_object(MonkeyObject* monkey);
void animate_monkey_object(MonkeyObject* monkey);
