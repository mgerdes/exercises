(define (equal? s1 s2)
  (cond ((and (null? s1) (null? s2)) #t)
        ((or (and (null? s1) (not (null? s2))) 
             (and (not (null? s1)) (null? s2))) #t)
        (else (and (eq? (car s1) (car s2)) 
                   (equal? (cdr s1) (cdr s2))))))

