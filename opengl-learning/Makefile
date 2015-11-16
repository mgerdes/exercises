LIBS = -lGL -lGLEW -lglfw -lassimp -lm
INCLUDES = -Iobjects -Imaths -Iutils -Iobjects -Iimage_reader 
SRCS = main.c maths/maths.c utils/gl_utils.c utils/camera.c objects/human_object.c objects/monkey_object.c
TARGET = opengl

main: $(SRCS)
	gcc $(SRCS) $(LIBS) $(INCLUDES) -o $(TARGET)

clean:
	rm -f $(TARGET)
