SYSTEM=dos
NAME=radix
LIBS=../lib/util.lib

assemble : $(NAME).asm
	jwasm $(NAME).asm
	jwlink SYS $(SYSTEM) F $(NAME) L $(LIBS)

run : assemble
	dosbox $(NAME).exe

clean:
	rm $(NAME).o $(NAME).exe
