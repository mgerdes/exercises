(define (make-table same-key?)
  (let ((table (cons '*table* '())))
    (define (assoc key l)
      (cond ((null? l) #f)
            ((same-key? key (caar l)) (car l))
            (else (assoc key (cdr l)))))

    (define (lookup keys)
      (let ((record (assoc (car keys) (cdr table)))) 
        (if record
          (if (null? (cdr keys))
            (cdr record)
            (((cdr record) 'lookup) (cdr keys)))  
          #f)))

    (define (insert! key value)
      (let ((record (lookup (list key))))  
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
(define m (make-table equal?))
(define l (make-table equal?))

((t 'insert) 'math m)
((t 'insert) 'letters l)

((m 'insert) '+ 43)
((m 'insert) '- 45)
((m 'insert) '* 42)

((l 'insert) 'a 97)
((l 'insert) 'b 98)

((t 'lookup) '(math *))
