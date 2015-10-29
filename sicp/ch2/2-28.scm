(define x (list (list 1 2) (list 3 4)))

(define (fringe l)
  (cond ((null? l) 
         '()) 
        ((list? (car l))
         (append (fringe (car l)) (fringe (cdr l))))
        (else
          (cons (car l) (fringe (cdr l))))))

(fringe x)
(fringe (list x x))
