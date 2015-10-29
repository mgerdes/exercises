(define (accumulate op initial sequence)
  (if (null? sequence)
    initial
    (op (car sequence)
        (accumulate op initial (cdr sequence)))))

(define (count-leaves t)
  (accumulate + 0 (map (if (number? t)
                         (lambda (x) 1)
                         count-leaves)
                       (if (number? t)
                         '(1)
                         t))))
