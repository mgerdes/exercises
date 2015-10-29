(define (deep-reverse l)
  (if (null? l)
    '()
    (append (deep-reverse (cdr l))
            (list (if (list? (car l))
                    (deep-reverse (car l))
                    (car l))) )))

(define x (list (list 1 2) (list 3 4)))

(deep-reverse x)
