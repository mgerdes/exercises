    .MODEL  LARGE
    .8086
    .STACK  100h
    .DATA
summation DW 50
    .CODE
Volume PROC
    mov     ax, @data
    mov     ds, ax

    mov     cx, 49
sum:
    add     summation, cx
    dec     cx
    jnz     sum
    
    mov     al, 0
    mov     ah, 4ch
    int     21h
Volume ENDP
    END
