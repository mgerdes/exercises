	.MODEL  small
	.386
	.STACK  100h
INCLUDE PCMAC.INC
	.DATA
INPUT_MESSAGE_1			DB	'Enter the first number: $'	
INPUT_MESSAGE_2			DB	'Enter the second number: $'	
REPEAT_MESSAGE			DB	'Do you wish to repeat? Enter y or Y to repeat, any other key exits: $'
OUTPUT_MESSAGE_1		DB	'The absolute value of the sum is $'
OUTPUT_MESSAGE_2		DB	', and the absolute value of the difference is $'
OUTPUT_MESSAGE_3		DB	'The product is $'
OUTPUT_MESSAGE_4		DB	', and the quotient/remainder is $'
OUTPUT_MESSAGE_5		DB	' R $'
Newline                 DB	13, 10, '$'
	.CODE
	EXTERN   GetDec : NEAR, PutDec : NEAR
Midterm PROC
	mov     AX, @DATA
	mov     DS, AX
input_loop:
	_PutStr	Newline
	_PutStr	INPUT_MESSAGE_1
	call	GetDec
	push	AX
	_PutStr	INPUT_MESSAGE_2
	pop		BX
	call    GetDec
	cmp 	BX, AX
	mov		CX, AX
	jle		multiply_numbers

	add		AX, BX
	call	AbsoluteValue
	push	AX
	_PutStr	OUTPUT_MESSAGE_1
	pop		AX
	call	PutDec
	_PutStr	OUTPUT_MESSAGE_2
	sub		BX, CX
	mov		AX, BX	
	call	AbsoluteValue
	call	PutDec
	_PutStr	Newline
	jmp		ask_if_should_continue
multiply_numbers:
	imul	AX, BX
	push	AX
	_PutStr	OUTPUT_MESSAGE_3
	pop		AX
	call	PutDec
	mov		AX, BX
	push	AX
	_PutStr	OUTPUT_MESSAGE_4
	pop		AX
	CWD
	idiv	CX
	call	PutDec
	mov		AX, DX
	call	AbsoluteValue
	push	AX
	_PutStr	OUTPUT_MESSAGE_5
	pop		AX
	call	PutDec
	_PutStr	Newline
ask_if_should_continue:
	_PutStr	REPEAT_MESSAGE
	_GetCh
	cmp		AL, 'y'
	je 		input_loop
	cmp		AL, 'Y'
	je 		input_loop

	mov     AL, 0
	mov     AH, 4CH
	int     21H
Midterm ENDP

AbsoluteValue PROC
	cmp		AX, 0
	jge		do_nothing
	neg		AX
do_nothing:
	RET
AbsoluteValue ENDP
	END Midterm
