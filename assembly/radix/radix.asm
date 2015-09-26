    .MODEL  small
    .8086
    .STACK  100h
    .DATA
Instructions          DB "When entering a radix you enter the radix you want in decimal or ", 13, 10
                      DB "'b' or 'B' for binary, or 'o' or 'O' for octal, or 'h' or 'H' for hex.", 13, 10
                      DB "The highest radix you can enter is 36 (base 10).", 13, 10
                      DB "When entering in letters for a number they must be uppercase.",13,10
                      DB "Have Fun!", 13, 10, '$'
Go_Again_Message      DB 'Would you like to go again? $'
Input_Radix_Message   DB 'Please enter the input radix: $'
Output_Radix_Message  DB 'Please enter the output radix: $'
Input_A_Message       DB 'Please enter the number A: $'
Input_B_Message       DB 'Please enter the number B: $'
Divide_Result         DB 'A / B = $'
Divide_Result2        DB ' R $'
Base_Result           DB ' (base $'
Base_Result2          DB ')$'
Add_Result            DB 'A + B = $'
Sub_Result            DB 'A - B = $'
Mul_Result            DB 'A * B = $'
Exp_Result            DB 'A ^ B = $'
Newline               DB 13, 10, '$'
Input_Radix           DW ?
Output_Radix          DW ?
A                     DW ?
B                     DW ?
    .CODE
    EXTERN PutDec : Near
Radix PROC
    mov     ax, @data
    mov     ds, ax

    mov     dx, OFFSET Instructions
    mov     AH, 09h
    int     21h

Input_Loop:

    mov     dx, OFFSET Input_Radix_Message
    mov     AH, 09h
    int     21h

    mov     DI, 1
    
    mov     CX, 10
    call    GetRadix
    mov     Input_Radix, AX

    mov     dx, OFFSET Newline
    mov     AH, 09h
    int     21h

    mov     dx, OFFSET Output_Radix_Message
    mov     AH, 09h
    int     21h

    mov     CX, 10
    call    GetRadix
    mov     Output_Radix, AX

    xor     DI, DI

    mov     dx, OFFSET Newline
    mov     AH, 09h
    int     21h

    mov     dx, OFFSET Input_A_Message
    mov     AH, 09h
    int     21h

    mov     CX, Input_Radix
    call    GetRadix
    mov     A, AX

    mov     dx, OFFSET Newline
    mov     AH, 09h
    int     21h

    mov     dx, OFFSET Input_B_Message
    mov     AH, 09h
    int     21h

    mov     CX, Input_Radix
    call    GetRadix
    mov     B, AX

    mov     dx, OFFSET Newline
    mov     AH, 09h
    int     21h

    mov     dx, OFFSET Add_Result
    mov     AH, 09h
    int     21h

    mov     AX, A
    add     AX, B
    mov     BX, Output_Radix
    mov     DI, 1
    call    PutRadix

    mov     dx, OFFSET Newline
    mov     AH, 09h
    int     21h

    mov     dx, OFFSET Sub_Result
    mov     AH, 09h
    int     21h

    mov     AX, A
    sub     AX, B
    mov     BX, Output_Radix
    mov     DI, 1 
    call    PutRadix

    mov     dx, OFFSET Newline
    mov     AH, 09h
    int     21h

    mov     dx, OFFSET Mul_Result
    mov     AH, 09h
    int     21h

    mov     AX, A
    imul    B
    mov     BX, Output_Radix
    mov     DI, 1
    call    PutRadix

    mov     dx, OFFSET Newline
    mov     AH, 09h
    int     21h

    mov     dx, OFFSET Divide_Result
    mov     AH, 09h
    int     21h

    mov     AX, A
    cwd
    idiv    B
    push    DX
    mov     BX, Output_Radix
    mov     DI, 1
    call    PutRadix

    mov     dx, OFFSET Divide_Result2
    mov     AH, 09h
    int     21h

    pop     AX
    mov     BX, Output_Radix
    mov     DI, 1
    call    PutRadix

    mov     dx, OFFSET Newline
    mov     AH, 09h
    int     21h

    mov     dx, OFFSET Exp_Result
    mov     AH, 09h
    int     21h

    mov     BX, A
    mov     DX, B
    call    Exponentiation
    mov     BX, Output_Radix
    mov     DI, 1
    call    PutRadix

    mov     dx, OFFSET Newline
    mov     AH, 09h
    int     21h

    mov     dx, OFFSET Go_Again_Message
    mov     AH, 09h
    int     21h

    mov     AH, 01h
    int     21h

    mov     dx, OFFSET Newline
    mov     AH, 09h
    int     21h

    cmp     AL, 'Y'
    je      Input_Loop

    cmp     AL, 'y'
    je      Input_Loop

    mov     ah, 4ch
    int     21h
Radix ENDP

; displays the value in AX
; radix must be in BX
; if DI is 1 then the base of the number should be printed
PutRadix PROC
    xor     SI, SI
    cmp     AX, 32768
    jb      NotNegative
    mov     SI, 1
    neg     AX
NotNegative:
    xor     CX, CX
DivideLoop:
    inc     CX     
    xor     DX, DX 
    div     BX
    push    DX
    cmp     AX, 0
    je      PutChars
    jne     DivideLoop
PutChars:
    cmp     SI, 0
    je      PutCharLoop
    mov     DL, '-'
    mov     AH, 02h
    int     21h
PutCharLoop:
    pop     DX
    call    asciitize
    mov     AH, 02h
    int     21h
    dec     CX
    cmp     CX, 0
    je      EndPutCharLoop
    jmp     PutCharLoop
EndPutCharLoop:
    ; need this to avoid infinite loops
    cmp     DI, 0
    jz      Dont_Print_Base

    ; display the base of the number printed
    mov     dx, OFFSET Base_Result
    mov     AH, 09h
    int     21h

    mov     AX, BX
    mov     BX, 10
    mov     DI, 0
    call    PutRadix 

    mov     dx, OFFSET Base_Result2
    mov     AH, 09h
    int     21h

Dont_Print_Base:


    RET
PutRadix ENDP

; end value will be in AX
; radix must be stored in CX
GetRadix PROC
    xor     BX, BX
    mov     SI, 0
GetCharacters:
    ; get the character, put it into AL
    mov     AH, 08H
    int     21h

    cmp     DI, 0
    jz      NotSpecialCharacter
    
    cmp     AL, 'b'
    jnz     NotLowerB

    mov     DL, 'b'
    mov     AH, 02h
    int     21h

    mov     AX, 2

    jmp     NotNegative
NotLowerB:
    cmp     AL, 'B'
    jnz     NotUpperB

    mov     DL, 'B'
    mov     AH, 02h
    int     21h

    mov     AX, 2

    jmp     NotNegative
NotUpperB:
    cmp     AL, 'o'
    jnz     NotLowerO

    mov     DL, 'o'
    mov     AH, 02h
    int     21h

    mov     AX, 8

    jmp     NotNegative
NotLowerO:
    cmp     AL, 'O'
    jnz     NotUpperO

    mov     DL, 'O'
    mov     AH, 02h
    int     21h

    mov     AX, 8

    jmp     NotNegative
NotUpperO:
    cmp     AL, 'h'
    jnz     NotLowerH

    mov     DL, 'h'
    mov     AH, 02h
    int     21h

    mov     AX, 16

    jmp     NotNegative
NotLowerH:
    cmp     AL, 'H'
    jnz     NotSpecialCharacter

    mov     DL, 'H'
    mov     AH, 02h
    int     21h

    mov     AX, 16

    jmp     NotNegative

NotSpecialCharacter:

    ; If SI is 1 then it's a negative number, 2 for positive number
    cmp     SI, 0
    jne     NotFirstCharacter

    mov     SI, 2
    cmp     AL, 45
    jne     NotFirstCharacter
    mov     SI, 1

    mov     DL, AL
    mov     AH, 02h
    int     21h

    jmp     GetCharacters
NotFirstCharacter:
    ; check if enter was pressed
    cmp     AL, 13
    je      EndGetCharacters

    ; check if backspace was pressed
    cmp     AL, 8
    jne     NotBackSpace
    ; if BX is zero then you can't backspace
    cmp     BX, 0
    je      NotBackSpace
    
    ; if backspace was pressed then divide by the radix and clear out that character on the screen
    mov     AX, BX
    cwd
    div     CX
    mov     BX, AX

    mov     DL, 8
    mov     AH, 02h
    int     21h

    mov     DL, 32
    mov     AH, 02h
    int     21h

    mov     DL, 8
    mov     AH, 02h
    int     21h
NotBackSpace:
    ; ensure character in AL is valid
    ; if character is less then '0' then its bad
    cmp     AL, '0'
    jb      GetCharacters

    cmp     CX, 10
    jg      RadixLargerThen10

    ; if radix <= 10 then AL < '0' + Radix
    mov     DL, CL
    add     DL, '/'
    cmp     AL, DL
    ja      GetCharacters

    jmp     CharacterCorrect

RadixLargerThen10:
    ; if radix > 10 then (AL <= '9') or (AL >= 'a' and AL < 'a' + radix') 
    cmp     AL, '9'
    jbe     CharacterCorrect

    cmp     AL, 'A'
    jb      GetCharacters

    mov     DL, CL
    add     DL, '6'
    cmp     AL, DL
    ja      GetCharacters
CharacterCorrect:
    ; show the character they just typed in
    mov     DL, AL
    mov     AH, 02h
    int     21h

    ; deasciitize the char and push the value to the stack
    call    Deasciitize
    xor     AH, AH
    push    AX

    ; multiply the current value by the radix and add the value of the input character
    mov     AX, BX
    mul     CX
    pop     DX
    add     AX, DX
    mov     BX, AX

    ; get more characters
    jmp     GetCharacters
EndGetCharacters:
    mov     AX, BX
    cmp     SI, 1
    jne     NotNegative
    neg     AX
NotNegative:
    RET
GetRadix ENDP

; performs exponentiation A^B, where A is in BX, B in DX
; result is in AX
Exponentiation PROC
    ; perform absolute value on DX
    cmp     DX, 0
    jg      Not_Negative
    neg     DX
Not_Negative:
    mov     CX, DX
    mov     AX, 1
    jcxz    End_Exponent_Loop
Exponent_Loop:
    imul    BX
    dec     CX
    jnz     Exponent_Loop
End_Exponent_Loop:
    RET
Exponentiation ENDP

; deasciitize's AL and puts value into AL
Deasciitize PROC
    cmp     AL, '9'
    ja      NOT_NUMBER
    sub     AL, '0'
    RET
NOT_NUMBER:
    sub     AL, '7'
    RET
Deasciitize ENDP

; asciitize's DL and puts value into DL
Asciitize PROC
    cmp     DL, 9
    ja      NOT_NUMBER
    add     DL, '0'
    RET
NOT_NUMBER:
    add     DL, '7'
    RET
Asciitize ENDP
    END Radix
