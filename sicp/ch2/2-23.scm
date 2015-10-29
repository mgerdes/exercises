(define (for-each f l)
    (if (null? l) 
      42
      (begin
        (f (car l))
        (for-each f (cdr l)))))
