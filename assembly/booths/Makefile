SYSTEM=dos
NAME=booths
LIBS=../lib/util.lib

all : $(NAME).asm
	jwasm $(NAME).asm
	jwlink SYS $(SYSTEM) F $(NAME) L $(LIBS)

run :
	make
	dosbox $(NAME).exe

clean:
	rm $(NAME).o $(NAME).exe
