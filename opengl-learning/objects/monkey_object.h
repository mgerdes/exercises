typedef struct MonkeyObject {
    GLuint vao;
    int vertex_count;
    int direction;
    double speed;
    double theta;
    Skeleton_Node* root_node;
    GLuint bone_matrices_locations[64];
    Mat* bone_offset_mats[16];
} MonkeyObject;

MonkeyObject* create_monkey_object(GLuint shader_program);
void draw_monkey_object(MonkeyObject* monkey);
void animate_monkey_object(MonkeyObject* monkey);
