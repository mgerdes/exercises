; Microsystems Homework 0
; Michael Gerdes 6/14/2016
    .MODEL  SMALL
    .586
    .STACK  100h
    .DATA
input_number_1  DB 'Enter a number: $'
input_number_2  DB 'Enter another number: $'
new_line        DB 13, 10, '$'
multiplicand    DW 0
multiplier      DW 0
    .CODE
    EXTRN   PutDDec : NEAR, GetDec : Near
Booths PROC
    mov     ax, @data
    mov     ds, ax

    mov     dx, OFFSET input_number_1
    mov     AH, 09h
    int     21h
    call    GetDec
    mov     multiplicand, ax

    mov     dx, OFFSET input_number_2
    mov     AH, 09h
    int     21h
    call    GetDec
    mov     multiplier, ax

    mov     cx, 16
    mov     bx, multiplicand
    mov     ax, multiplier
    xor     dx, dx
    clc

begin_loop:
    jc      cf_1
cf_0:
    test    ax, 1 
    jz      do_shift
    ; CF 0 and LSB 1
    sub     dx, bx
    jmp     do_shift
cf_1:
    test    ax, 1
    jnz     do_shift
    ; CF 1 and LSB 0
    add     dx, bx 
    jmp     do_shift
do_shift:
    sar     dx, 1 
    rcr     ax, 1
    dec     cx
    jnz     begin_loop     

    ; mov DX:AX into EAX
    mov     bx, ax
    mov     ax, dx
    shl     eax, 10h
    mov     ax, bx

    call    PutDDec
    
    mov     al, 0
    mov     ah, 4ch
    int     21h
Booths ENDP
    END
