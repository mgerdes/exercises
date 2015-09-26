    .MODEL  small
    .386
    .STACK  100h
INCLUDE PCMAC.INC
    .DATA
SECONDS_IN_HOUR         EQU 3600
SECONDS_IN_MINUTE       EQU 60
OUTPUT_MESSAGE_LEN      EQU 11

Instructions            DB 'Enter the time in hours minutes seconds > $'
Go_Again_String         DB 'Do you want to repeat (y/n)? $'
Output_Messages         DB 'Time is $  ', ' seconds $ ', ' minutes, $', ' hours, $'
Input_Overflow_Message  DB 'Your input caused overflow. Try again.$'
Newline                 DB 13, 10, '$'
    .CODE
    EXTERN   GetDec : NEAR, PutDec : NEAR, PutDDec : NEAR, PutUDec : NEAR
TimeProg PROC
    mov     AX, @DATA
    mov     DS, AX

time_input_loop:
    _PutStr Instructions 

    xor     EAX, EAX
    call    GetDec
    mov     EBX, SECONDS_IN_HOUR
    mul     EBX
    jo      Input_Overflow
    mov     ECX, EAX

    xor     EAX, EAX
    call    GetDec
    mov     EBX, SECONDS_IN_MINUTE
    mul     EBX
    jo      Input_Overflow
    add     EAX, ECX
    jc      Input_Overflow
    mov     ECX, EAX

    xor     EAX, EAX
    call    GetDec
    add     EAX, ECX
    jc      Input_Overflow

    push    EAX
    push    EAX
    push    EAX
    _PutStr Output_Messages
    pop     EAX
    call    Print_Total_Seconds
    _PutStr Output_Messages
    pop     EAX
    call    Print_Total_Minutes_And_Seconds
    _PutStr Output_Messages
    pop     EAX
    call    Print_Total_Hours_Minutes_And_Seconds

    _PutStr Go_Again_String
    ; input anything but n, N, q, Q to continue.
    _GetCh
    cmp     AL, 'n'
    je      end_of_time_input_loop
    cmp     AL, 'N'
    je      end_of_time_input_loop
    cmp     AL, 'Q'
    je      end_of_time_input_loop
    cmp     AL, 'q'
    je      end_of_time_input_loop
    _PutStr Newline

    jmp     time_input_loop

input_overflow:
    _PutStr Newline
    _PutStr Input_Overflow_Message
    _PutStr Newline
    jmp     time_input_loop

end_of_time_input_loop:

    mov     AL, 0
    mov     AH, 4CH
    int     21H
TimeProg ENDP

Print_Total_Seconds PROC
    call    PutDDec
    _PutStr OFFSET Output_Messages + OUTPUT_MESSAGE_LEN
    _PutStr Newline
    RET
Print_Total_Seconds ENDP

Print_Total_Minutes_And_Seconds PROC
    xor     EDX, EDX
    mov     EBX, SECONDS_IN_MINUTE 
    div     EBX
    push    EDX
    call    PutDDec
    _PutStr OFFSET Output_Messages + 2 * OUTPUT_MESSAGE_LEN
    pop     EAX
    call    Print_Total_Seconds
    RET
Print_Total_Minutes_And_Seconds ENDP

Print_Total_Hours_Minutes_And_Seconds PROC
    xor     EDX, EDX
    mov     EBX, SECONDS_IN_HOUR
    div     EBX
    push    EDX
    call    PutDDec
    _PutStr OFFSET Output_Messages + 3 * OUTPUT_MESSAGE_LEN
    pop     EAX
    call    Print_Total_Minutes_And_Seconds
    RET
Print_Total_Hours_Minutes_And_Seconds ENDP
    END TimeProg
