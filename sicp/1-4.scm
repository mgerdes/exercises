(define (a-plus-abs-b a b)
  ((if (> b 0) + -) a b))

; to know what function to apply (the car of the expression) you have to evaluate the if expression
; if b > 0 then the if evaluates to the + function, so the result will be a + b
; if b <= 0 then the if evaluates to the - function, so the result will be a - b
; this is the definition of the absolute value function.
