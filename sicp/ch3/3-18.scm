(define (contains-cycle? l)
  (let ((pairs-seen '()))
    (define (loop l)
      (cond ((null? l) #f)
            ((member l pairs-seen) #t)
            (else 
              (begin
                (set! pairs-seen (cons l pairs-seen))
                (loop (cdr l))))))
    (loop l)))

(contains-cycle? (list 1 1 3 4 5)) ; => #f

(define x (cons 1 '()))
(set-cdr! x x)

(contains-cycle? x) ; => #t
