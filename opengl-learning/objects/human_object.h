typedef struct HumanObject {
    GLuint vao;
    int vertex_count;
    int direction;
    double speed;
    double theta;
    Skeleton_Node* root_node;
    GLuint bone_matrices_locations[64];
    Mat* bone_offset_mats[16];
} HumanObject;

HumanObject* create_human_object(GLuint shader_program);
void draw_human_object(HumanObject* human);
void animate_human_object(HumanObject* human);
