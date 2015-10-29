(define (make-rat n d) 
  (if (negative? d)
    (cons (- n) (- d))
    (cons n d)))

; if denominator is negative, then multiply by -1
