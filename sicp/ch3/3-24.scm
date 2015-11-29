(define (make-table same-key?)
  (let ((table (cons '*table* '())))
    (define (assoc key l)
      (cond ((null? l) #f)
            ((same-key? key (caar l)) (car l))
            (else (assoc key (cdr l)))))

    (define (lookup key)
      (let ((record (assoc key (cdr table)))) 
        (if record
          (cdr record)
          #f)))

    (define (insert! key value)
      (let ((record (lookup key)))  
        (if record
          (set-cdr! record value)
          (set-cdr! table 
                    (cons (cons key value) (cdr table))))))

    (define (dispatch m)
      (cond ((eq? m 'lookup) lookup) 
            ((eq? m 'insert) insert!)
            (else "Unknown message")))

    dispatch))

(define t (make-table equal?))
((t 'insert) 'plus '+)
((t 'lookup) 'plus)
