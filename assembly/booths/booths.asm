    .MODEL  LARGE
    .386
    .STACK  100h
    .DATA
multiplicand    DW 4
multiplier      DW 4
    .CODE
    EXTRN   PutDDec : NEAR
Booths PROC
    mov     ax, @data
    mov     ds, ax

    mov     cx, 16
    mov     bx, multiplicand
    mov     ax, multiplier
    xor     dx, dx
    clc

begin_loop:
    ; Save the carry flag
    pushf
    ; Check lsb of multiplier
    test    multiplier, 1
    jnz     lsb_1
    ; Get carry flag back
    popf
    jc      lsb_0_cf_1
    ; LSB is 0 and CF is 0 so skip this  
    jmp     end_lsb_check
lsb_0_cf_1:
    ; LSB is 0 and CF is 1
    add     dx, multiplicand
    jmp     end_lsb_check
lsb_1:
    popf
    ; LSB is 1 and CF is 0 so skip this 
    jc      end_lsb_check
lsb_1_cf_0:
    ; LSB is 1 and CF is 0
    sub     dx, multiplicand    
    jmp     end_lsb_check
end_lsb_check:
    ; Shift LSB of DX into MSB of AX
    shrd    ax, dx, 1
    pushf
    sar     dx, 1
    popf
    jcxz    end_loop
    dec     cx
jmp begin_loop
end_loop:

    call    PutDDec
    
    mov     al, 0
    mov     ah, 4ch
    int     21h
Booths ENDP
    END
