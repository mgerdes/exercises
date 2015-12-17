typedef struct SphereList {
    SphereObject* sphere; 
    struct SphereList* rest_of_spheres;
} SphereList;

typedef struct SphereSimulation {
    PlaneObject* plane1;
    PlaneObject* plane2;
    PlaneObject* plane3;
    PlaneObject* plane4;
    PlaneObject* plane5;
    SphereList* sphere_list;
    int num_of_spheres;
    double time_last_drawn;
    double next_collision_time;
    double impact_time;
} SphereSimulation;

SphereSimulation* create_sphere_simulation(GLuint shader_program);
void draw_sphere_simulation(SphereSimulation* s);
void add_sphere(SphereSimulation* sphere_sim, SphereObject* sphere);
double get_smallest_impact_time(SphereSimulation* sim);
