; Ackermann's funciton
(define (A x y)
  (cond ((= y 0) 0)
        ((= x 0) (* 2 y))
        ((= y 1) 2)
        (else (A (- x 1)
                 (A x (- y 1))))))

; (A 1 10) => 1024
; (A 2 4) => 65536
; (A 3 3) => 65536

(define (f n) (A 0 n))
; returns 2*n, as x will be zero in the Ackermann function.

(define (g n) (A 1 n))
; returns 0 if n = 0
; returns 2^n if n > 0

(define (h n) (A 2 n))
; not exactly concise but:
; returns 0 if n = 0
; returns 2^(A 2 (- n 1)) if n > 0

(define (k n) (* 5 n n))
; returns 5n^2
