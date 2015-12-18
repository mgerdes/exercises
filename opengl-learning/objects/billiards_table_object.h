typedef struct BilliardsTableObject {
    GLuint vao;
    int vertex_count;
} BilliardsTableObject;

BilliardsTableObject* create_billiards_table_object(GLuint shader_program);
void draw_billiards_table_object(BilliardsTableObject* billiards_table);
