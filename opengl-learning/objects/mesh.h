typedef struct Vertex {
    Vec* position;
    Vec* normal;
    Vec* texture;
} Vertex;

typedef struct Mesh {
    GLuint vao;
    int num_vertices;
} Mesh;

Mesh* create_mesh(Vertex** vertices, int num_vertices);
void draw_mesh(Mesh* mesh);
