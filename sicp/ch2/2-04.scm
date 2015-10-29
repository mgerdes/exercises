(define (cons x y)
  (lambda (m) (m x y)))

(define (car z)
  (z (lambda (p q) p)))

(define (cdr z)
  (z (lambda (p q) q)))

; cons returns a function which takes in a function which takes in two arguments.

; car gives the function that cons returns a function which takes in two arguments,
; and returns the first argument.

; cdr is defined similarly, but uses a function which returns the second argument.

(car (cons 1 2)) ; => 1
(cdr (cons 1 2)) ; => 2
