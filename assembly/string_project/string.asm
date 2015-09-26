    .MODEL  SMALL
    .386
    .STACK  100h
INCLUDE PCMAC.INC
    
    .DATA
CAPITALIZE_BIT_MASK     EQU 0DFH
LOWERCASE_BIT_MASK      EQU 020H
TOGGLECASE_BIT_MASK     EQU LOWERCASE_BIT_MASK
MAX_BUFFER              EQU 50

String_Struct           DB  MAX_BUFFER 
                        DB  ?
String_Buffer           DB  MAX_BUFFER DUP ('$')

Previous_String_Buffer  DB  MAX_BUFFER DUP ('$')

Newline                 DB  13, 10, '$'

Current_String_Message  DB  "The current string is: $"

Instructions            DB  "The function 0 describes all other functions and 11 exits the app.$"
Help_String1            DB  "0 - Describes the functions.$"
Help_String2            DB  "1 - Finds the first occurence of a character.$"
Help_String3            DB  "2 - Finds the number of occurences of a character.$"
Help_String4            DB  "3 - Finds the number of characters in the string.$"
Help_String5            DB  "4 - Finds the number of alphanumeric characters in the string.$"
Help_String6            DB  "5 - Replaces a character with another.$"
Help_String7            DB  "6 - Capitalizes each character in the string.$"
Help_String8            DB  "7 - Makes each character lowercase in the string.$"
Help_String9            DB  "8 - Toggles the case of each character in the string.$"
Help_String10           DB  "9 - Input a new string.$"
Help_String11           DB  "10 - Rewind.$"
Help_String12           DB  "11 - Exit the application.$"

Function1_String1       DB  "Enter a character to determine the first occurence of: $"
Function1_String2       DB  "The first '$"
Function1_String3       DB  "' in the string $"
Function1_String4       DB  "occurs in position $"
Function1_String5       DB  "The character '$"
Function1_String6       DB  "' was not found in the string$"

Function2_String1       DB  "Enter a character to determine the number of occurences: $"
Function2_String2       DB  "The letter '$"
Function2_String3       DB  "' occurs in the string$"
Function2_String4       DB  " times$"

Function3_String1       DB  "There are $"
Function3_String2       DB  " characters in the string$"

Function4_String1       DB  "There are $"
Function4_String2       DB  " alphanumeric characters in the string$"

Function5_String1       DB  "Enter a character to replace: $"
Function5_String2       DB  "Enter replacement character: $"
Function5_String3       DB  "Replacing each occurence of '$"
Function5_String4       DB  "' in the string with '$"
Function5_String5       DB  "' yields$"

Function6_String1       DB  "Capitalizing each letter in the string yields$"

Function7_String1       DB  "Making each letter lowercase in the string yields$"

Function8_String1       DB  "Toggling the case of each letter in the string yields$"

Rewind_Function_String  DB  "Rewound to the string $"

Input_New_String_Mesg   DB  "Input a new string (max length 50) > $"

Invalid_Func_Mesg       DB  " is not a valid function.$"

Prompt_For_Function     DB  "Input a function (number between 0 and 11) > $"

character               DB  ?
replacement             DB  ?

    .CODE
    EXTERN   GetDec : NEAR, PutDec : Near

String PROC 
    mov     AX, @data
    mov     DS, AX

    _PutStr Input_New_String_Mesg
    _GetStr String_Struct
    _PutCh  10

    call    Set_Previous_String_Buffer

    _PutStr Instructions
    _PutStr Newline

function_prompt_loop:
    _PutStr Current_String_Message
    _PutStr String_Buffer
    _PutStr NewLine
    _PutStr Prompt_For_Function
    call    GetDec

    cmp     AX, 0
    jnz     NOT_FN_0
    call    Set_Previous_String_Buffer
    call    Help_Function
    jmp     function_prompt_loop

NOT_FN_0:

    cmp     AX, 1
    jnz     NOT_FN_1
    call    Set_Previous_String_Buffer
    call    Input_Function1
    mov     DI, Function1
    call    Iterate_Through_String
    call    Output_Function1
    jmp     function_prompt_loop

NOT_FN_1:
    cmp     AX, 2
    jnz     NOT_FN_2
    call    Set_Previous_String_Buffer
    call    Input_Function2
    mov     DI, Function2
    call    Iterate_Through_String
    call    Output_Function2
    jmp     function_prompt_loop

NOT_FN_2:
    cmp     AX, 3
    jnz     NOT_FN_3
    call    Set_Previous_String_Buffer
    call    Input_Function3
    mov     DI, Function3
    call    Iterate_Through_String
    call    Output_Function3
    jmp     function_prompt_loop

NOT_FN_3:
    cmp     AX, 4
    jnz     NOT_FN_4
    call    Set_Previous_String_Buffer
    call    Input_Function4
    mov     DI, Function4
    call    Iterate_Through_String
    call    Output_Function4
    jmp     function_prompt_loop

NOT_FN_4:
    cmp     AX, 5
    jnz     NOT_FN_5
    call    Set_Previous_String_Buffer
    call    Input_Function5
    mov     DI, Function5
    call    Iterate_Through_String
    call    Output_Function5
    jmp     function_prompt_loop

NOT_FN_5:
    cmp     AX, 6
    jnz     NOT_FN_6
    call    Set_Previous_String_Buffer
    call    Input_Function6
    mov     DI, Function6
    call    Iterate_Through_String
    call    Output_Function6
    jmp     function_prompt_loop

NOT_FN_6:
    cmp     AX, 7
    jnz     NOT_FN_7
    call    Set_Previous_String_Buffer
    call    Input_Function7
    mov     DI, Function7
    call    Iterate_Through_String
    call    Output_Function7
    jmp     function_prompt_loop

NOT_FN_7:
    cmp     AX, 8
    jnz     NOT_FN_8
    call    Set_Previous_String_Buffer
    call    Input_Function8
    mov     DI, Function8
    call    Iterate_Through_String
    call    Output_Function8
    jmp     function_prompt_loop

NOT_FN_8:
    cmp     AX, 9
    jnz     NOT_FN_9
    call    Set_Previous_String_Buffer
    call    Input_New_String
    jmp     function_prompt_loop

NOT_FN_9:
    cmp     AX, 10
    jnz     NOT_FN_10
    call    Rewind_Function
    call    Rewind_Function_Output
    jmp     function_prompt_loop

NOT_FN_10:
    cmp     AX, 11
    jz      end_program

    call    PutDec
    _PutStr Invalid_Func_Mesg
    _PutStr Newline

    jmp     function_prompt_loop

end_program:

    mov     AL, 0
    mov     AH, 4ch
    int     21h
String ENDP

; Will iterate through the string.
; For each character it will call the procedure stored at the address in DI
; The position of the character will be stored in BX
; The current character will be stored in AH
Iterate_Through_String PROC
    mov     CX, 0
    mov     BX, OFFSET [String_Buffer] 
iterate_loop:
    mov     AH, BYTE PTR [BX] 
    cmp     AH, '$'
    je      end_iterate_loop
    cmp     DH, 1
    je      end_iterate_loop
    call    DI
    inc     BX
    jmp     iterate_loop
end_iterate_loop:
    RET
Iterate_Through_String ENDP

Help_Function PROC
    _PutStr Help_String1
    _PutStr Newline
    _PutStr Help_String2
    _PutStr Newline
    _PutStr Help_String3
    _PutStr Newline
    _PutStr Help_String4
    _PutStr Newline
    _PutStr Help_String5
    _PutStr Newline
    _PutStr Help_String6
    _PutStr Newline
    _PutStr Help_String7
    _PutStr Newline
    _PutStr Help_String8
    _PutStr Newline
    _PutStr Help_String9
    _PutStr Newline
    _PutStr Help_String10
    _PutStr Newline
    _PutStr Help_String11
    _PutStr Newline
    _PutStr Help_String12
    _PutStr Newline
    RET
Help_Function ENDP

; Procecures to call before function starts
Input_Function1 PROC
    _PutStr Function1_String1
    _GetCh
    mov     character, AL
    _PutStr Newline
    mov     DH, 0
    RET
Input_Function1 ENDP
Input_Function2 PROC
    _PutStr Function2_String1
    _GetCh  
    mov     character, AL
    _PutStr Newline
    RET
Input_Function2 ENDP
Input_Function3 PROC
    RET
Input_Function3 ENDP
Input_Function4 PROC
    RET
Input_Function4 ENDP
Input_Function5 PROC
    _PutStr Function5_String1
    _GetCh  
    mov     character, AL
    _PutStr Newline
    _PutStr Function5_String2
    _GetCh  
    mov     replacement, AL
    _PutStr Newline
    RET
Input_Function5 ENDP
Input_Function6 PROC
    RET
Input_Function6 ENDP
Input_Function7 PROC
    RET
Input_Function7 ENDP
Input_Function8 PROC
    RET
Input_Function8 ENDP
Input_New_String PROC
    call    Empty_String_Buffer  
    _PutStr Input_New_String_Mesg
    _GetStr String_Struct
    _PutCh  10
    RET
Input_New_String ENDP

; Procedures to call while iterating through the string
Function1 PROC
    mov     AL, character
    cmp     AH, AL
    jne     dont_set_stop_iterating
    mov     DH, 1      
    RET
dont_set_stop_iterating:
    inc     CX
    RET
Function1 ENDP
Function2 PROC
    mov     AL, character
    cmp     AH, AL
    jne     do_not_increase_count 
    inc     CX 
do_not_increase_count:
    RET
Function2 ENDP
Function3 PROC
    inc     CX
    RET
Function3 ENDP
Function4 PROC
    call    Check_If_Alphanumeric
    cmp     AL, 0 
    je      do_not_increase_count
    inc     CX
do_not_increase_count:
    RET
Function4 ENDP
Function5 PROC
    mov     DL, character
    cmp     AH, DL
    jne     do_not_replace_character
    mov     DL, replacement
    mov     [BX], DL
do_not_replace_character:
    RET
Function5 ENDP
Function6 PROC
    call    Check_If_Letter
    cmp     AL, 0 
    je      not_letter
    and     AH, CAPITALIZE_BIT_MASK 
    mov     [BX], AH
not_letter:
    RET
Function6 ENDP
Function7 PROC
    call    Check_If_Letter
    cmp     AL, 0 
    je      not_letter
    or      AH, LOWERCASE_BIT_MASK 
    mov     [BX], AH
not_letter:
    RET
Function7 ENDP
Function8 PROC
    call    Check_If_Letter
    cmp     AL, 0 
    je      not_letter
    xor     AH, TOGGLECASE_BIT_MASK 
    mov     [BX], AH
not_letter:
    RET
Function8 ENDP
Rewind_Function PROC
    mov     BX, OFFSET String_Buffer
    mov     DI, OFFSET Previous_String_Buffer
iterate_through_string_buffer:
    cmp     BYTE PTR [DI], '$'
    je      done_iterating_through_string_buffer
    mov     AL, BYTE PTR [DI]
    mov     BYTE PTR [BX], AL
    inc     BX
    inc     DI
    jmp     iterate_through_string_buffer
done_iterating_through_string_buffer:
    inc     BX
    mov     BYTE PTR [BX], '$'
    RET
Rewind_Function ENDP

; Procedures to call after function is finished
Output_Function1 PROC
    cmp     DH, 0
    jne     found_character
    _PutStr Function1_String5
    mov     AL, character
    _PutCh  AL
    _PutStr Function1_String6
    _PutStr Newline
    _PutStr String_Buffer
    _PutStr Newline
    RET
found_character:
    _PutStr Function1_String2
    mov     AL, character
    _PutCh  AL
    _PutStr Function1_String3
    _PutStr Newline
    _PutStr String_Buffer
    _PutStr Newline
    _PutStr Function1_String4
    mov     AX, CX
    call    PutDec
    _PutStr Newline
    RET
Output_Function1 ENDP
Output_Function2 PROC
    _PutStr Function2_String2
    mov     AL, character
    _PutCh  AL
    _PutStr Function2_String3
    _PutStr Newline
    _PutStr String_Buffer
    _PutStr Newline
    mov     AX, CX
    call    PutDec
    _PutStr Function2_String4
    _PutStr NewLine
    RET
Output_Function2 ENDP
Output_Function3 PROC
    _PutStr Function3_String1
    mov     AX, CX
    call    PutDec
    _PutStr Function3_String2
    _PutStr Newline
    _PutStr String_Buffer
    _PutStr Newline
    RET
Output_Function3 ENDP
Output_Function4 PROC
    _PutStr Function4_String1
    mov     AX, CX
    call    PutDec
    _PutStr Function4_String2
    _PutStr Newline
    _PutStr String_Buffer
    _PutStr Newline
    RET
Output_Function4 ENDP
Output_Function5 PROC
    _PutStr Function5_String3
    _PutCh  character
    _PutStr Function5_String4
    _PutCh  replacement
    _PutStr Function5_String5
    _PutStr Newline
    _PutStr String_Buffer
    _PutStr Newline
    RET
Output_Function5 ENDP
Output_Function6 PROC
    _PutStr Function6_String1 
    _PutStr Newline
    _PutStr String_Buffer
    _PutStr Newline
    RET
Output_Function6 ENDP
Output_Function7 PROC
    _PutStr Function7_String1 
    _PutStr Newline
    _PutStr String_Buffer
    _PutStr Newline
    RET
Output_Function7 ENDP
Output_Function8 PROC
    _PutStr Function8_String1 
    _PutStr Newline
    _PutStr String_Buffer
    _PutStr Newline
    RET
Output_Function8 ENDP
Rewind_Function_Output PROC
    _PutStr Rewind_Function_String 
    _PutStr String_Buffer 
    _PutStr Newline
    RET
Rewind_Function_Output ENDP

; Checks if the character in AH is alphanumeric
; Sets AL to 1 if true, 0 if false
Check_If_Alphanumeric PROC
    call    Check_If_Letter
    cmp     AL, 0
    je      not_letter
    RET
not_letter:
    call    Check_If_Number
    cmp     AL, 0
    je      not_number
    RET
not_number:
    RET
Check_If_Alphanumeric ENDP

; Checks if the character in AH is a letter
; Sets AL to 1 if true, 0 if false
Check_If_Letter PROC
    cmp     AH, 'z' 
    jg      not_lower_case_letter
    cmp     AH, 'a'
    jl      not_lower_case_letter
    mov     AL, 1
    RET
not_lower_case_letter:
    cmp     AH, 'Z' 
    jg      not_upper_case_letter
    cmp     AH, 'A'
    jl      not_upper_case_letter
    mov     AL, 1
    RET
not_upper_case_letter:
    mov     AL, 0
    RET
Check_If_Letter ENDP

; Checks if the character in AH is a number
; Sets AL to 1 if true, 0 if false
Check_If_Number PROC
    cmp     AH, '9' 
    jg      not_number
    cmp     AH, '0'
    jl      not_number
    mov     AL, 1
    RET
not_number:
    mov     AL, 0
    RET
Check_If_Number ENDP

Empty_String_Buffer PROC
    mov     BX, OFFSET String_Buffer
iterate_through_string_buffer:
    cmp     BYTE PTR [BX], '$'
    je      done_iterating_through_string_buffer
    mov     BYTE PTR [BX], '$'
    inc     BX
    jmp     iterate_through_string_buffer
done_iterating_through_string_buffer:
    RET
Empty_String_Buffer ENDP

Set_Previous_String_Buffer PROC
    mov     BX, OFFSET String_Buffer
    mov     DI, OFFSET Previous_String_Buffer
iterate_through_string_buffer:
    cmp     BYTE PTR [BX], '$'
    je      done_iterating_through_string_buffer
    mov     AL, BYTE PTR [BX]
    mov     BYTE PTR [DI], AL
    inc     BX
    inc     DI
    jmp     iterate_through_string_buffer
done_iterating_through_string_buffer:
    inc     DI
    mov     BYTE PTR [DI], '$'
    RET
Set_Previous_String_Buffer ENDP

    END String
