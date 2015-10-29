(define (subsets s)
  (if (null? s)
    (list '())
    (let ((rest (subsets (cdr s))))
      (append rest (map 
                     (lambda (x) (cons (car s) x)) 
                     rest)))))

; For the base case, (subsets '()) => '(())

; Now assume that in the let, rest is the correct list of subsets for (cdr s)
; The subset for s will then be everything in rest and everything in rest with
; (car s) inserted in.
; The map gives everything in rest with (car s) inserted in, and the append
; combines these two lists. Thus the function gives the subsets for s.
