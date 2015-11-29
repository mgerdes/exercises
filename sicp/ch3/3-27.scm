(define (make-table)
  (let ((table (cons '*table* '())))
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

(define (fib n)
  (cond ((= n 0) 0)
        ((= n 1) 1)
        (else (+ (fib (- n 1))
                 (fib (- n 2))))))

(define (memoize f)
  (let ((table (make-table)))
    (lambda (x)
      (let ((previously-calculated-value ((table 'lookup) x)))
        (if previously-calculated-value
          previously-calculated-value
          (let ((result (f x)))
            ((table 'insert) x result)
            result))))))

(define memo-fib
  (memoize (lambda (n)
             (cond ((= n 0) 0)
                   ((= n 1) 1)
                   (else (+ (memo-fib (- n 1))
                            (memo-fib (- n 2))))))))

(memo-fib 100) ; => Each time memo-fib calculates a new value, it then stores it in the table.
               ; This prevents use from making uneeded calculations.


(define memo-fib (memoize fib))

(memo-fib 100) ; => Takes a very long time. This is because fib merely calls itself, not memo-fib
               ; Thus the saved values are not actually used.
