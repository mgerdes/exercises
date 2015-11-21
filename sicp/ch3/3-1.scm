(define (make-accumulator amount)
  (lambda (value)
    (set! amount (+ amount value)) 
    amount))

(define A (make-accumulator 5))

(A 10) ; => 15
(A 10) ; => 25


