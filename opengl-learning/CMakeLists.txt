cmake_minimum_required(VERSION 3.3)
project(opengl_learning)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES
        image_reader/stb_image.h
        maths/maths.c
        maths/maths.h
        objects/mesh.c
        objects/mesh.h
        objects/model.c
        objects/model.h
        utils/camera.c
        utils/camera.h
        utils/gl_utils.c
        utils/gl_utils.h
        utils/mesh_import.c
        utils/mesh_import.h
        main.c)

include_directories(image_reader maths objects simulation utils)

add_executable(opengl_learning ${SOURCE_FILES})

target_link_libraries(opengl_learning GL GLEW glfw m assimp)
