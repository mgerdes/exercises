    .MODEL  LARGE
    .8086
    .STACK  100h

    .DATA
Prompt  DB  'Enter n: $'
NewLine DB  13, 10, '$'
n       DW  ?    
prev1   DW  ?
prev2   DW  ?
    .CODE
    EXTRN   GetDec : NEAR, PutDec : NEAR
Fib PROC
    mov     ax, @data
    mov     ds, ax

    ; Prompt user for input
    mov     dx, OFFSET Prompt
    mov     ah, 9h
    int     21h
    call    GetDec
    mov     n, ax

    ; Initalize loop counter to 1
    mov     cx, 1 

    ; Start with first 2 numbers being 1
    mov     prev1, 1
    mov     prev2, 1

l:
    mov     bx, prev1
    mov     ax, prev2

    add     prev1, ax ; prev1 = prev2 + prev1
    mov     prev2, bx ; prev2 = prev1

    ; Print prev2
    call    PutDec

    ; Print a newline
    mov     dx, OFFSET NewLine
    mov     ah, 9h
    int     21h
    
    cmp     cx, n
    ; End loop if the counter equals n
    je      el

    ; Increment loop counter and start over
    inc     cx
    jmp     l
el:

    ; Clean up stuffs
    mov     al, 0
    mov     ah, 4ch
    int     21h
Fib ENDP
    END
