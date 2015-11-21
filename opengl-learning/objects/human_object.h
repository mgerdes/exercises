typedef struct HumanObject {
    GLuint vao;
    int vertex_count;
    Skeleton_Node* root_node;
    Mat* bone_offset_mats[];
} HumanObject;

HumanObject* create_human_object();
void draw_human_object(HumanObject* human);
