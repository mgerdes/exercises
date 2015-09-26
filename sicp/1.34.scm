(define (f g)
  (g 2)) 

(f square) 
; => 4

(f (lambda (z) (* z (+ z 1))))
; => 6

(f f)
; (f f)
; (f 2)
; (2 2)
;
; A error will be thrown that 2 is not a function.
; The variable g will be bound to f in the first function call. So we call (f 2)
; Now the variable g will be bound to 2. So we attempt to call (2 2) => invalid 
