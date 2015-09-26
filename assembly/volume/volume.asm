    .MODEL  LARGE
    .386
    .STACK  100h

    .DATA
LENGTH_OF_INPUT_PROMPTS         EQU 11
LENGTH_OF_RESULT_PROMPTS        EQU 18

instructions                    DB 'Input format should be: feet \space\ inches. (e.g. "2 3" for 2 feet 3 inches)', 13, 10, '$' 
input_overflow_message          DB 'Your input caused overflow (At most it can be 65535 inches).', 13, 10, '$'
multiplication_overflow_message DB 'Your input caused overflow (L* W * H must be < 65535 cu. inches).', 13, 10, '$'
input_prompts                   DB 'Width  >: $', 'Height >: $', 'Length >: $'   
result_prompts                  DB ' cu. inches.   ', 13, 10, '$', ' cu. feet.  and  ', '$', ' cu. inches.', 13, 10, '$'

results                         DW ?, ?, ?
inches_in_foot                  DW 12
inches_3_in_foot_3              DW 1728    
    .CODE
    EXTRN   GetDec : NEAR, PutDec : NEAR, PutDDec : NEAR, PutUDec : NEAR
Volume PROC
    mov     ax, @data
    mov     ds, ax

    mov     dx, OFFSET [instructions]
    mov     ah, 9h
    int     21h

    index = 0
    repeat  3
        mov     dx, OFFSET [input_prompts + LENGTH_OF_INPUT_PROMPTS * index]
        mov     ah, 9h
        int     21h

        call    GetDec
        mul     inches_in_foot 
        jo      input_overflow_error 
        mov     bx, ax ; bx = 12 * number of feet

        call    GetDec
        add     ax, bx          
        jc      input_overflow_error
        mov     [results + 2 * index], ax ; store 12 * num feet + num inches as the result 

        index = index + 1
    endm

    mov     ax, results ; ax = width

    mov     bx, OFFSET [results]
    add     bx, 2
    mov     cx, [bx]
    mul     cx
    jo      multiplication_overflow_error

    add     bx, 2
    mov     cx, [bx]
    mul     cx
    jo      multiplication_overflow_error

    mov     bx, dx
    mov     cx, ax

    call    PutUDec
    mov     dx, OFFSET [result_prompts + 0 * LENGTH_OF_RESULT_PROMPTS]
    mov     ah, 9h
    int     21h

    mov     dx, bx
    mov     ax, cx

    div     inches_3_in_foot_3      
    call    PutUDec
    mov     bx, dx

    mov     dx, OFFSET [result_prompts + 1 * LENGTH_OF_RESULT_PROMPTS]
    mov     ah, 9h
    int     21h

    mov     ax, bx
    call    PutUDec

    mov     dx, OFFSET [result_prompts + 2 * LENGTH_OF_RESULT_PROMPTS]
    mov     ah, 9h
    int     21h

    jmp     end_of_program

input_overflow_error:
    mov     dx, OFFSET [input_overflow_message]
    mov     ah, 9h
    int     21h
    jmp     end_of_program

multiplication_overflow_error:
    mov     dx, OFFSET [multiplication_overflow_message]
    mov     ah, 9h
    int     21h
    jmp     end_of_program

end_of_program:
    mov     al, 0
    mov     ah, 4ch
    int     21h

Volume ENDP
    END
