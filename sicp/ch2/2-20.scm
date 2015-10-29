(define (same-parity x . y)
  (define (same-parity-recur y)
    (if (null? y)
      '()
      (if (= (remainder x 2) (remainder (car y) 2))
        (cons (car y) (same-parity-recur (cdr y)))
        (same-parity-recur (cdr y)))))

  (cons x (same-parity-recur y)))
