(define zero 
  (lambda (f) (lambda (x) x)))

(define (add-1 n)
  (lambda (f) 
    (lambda (x) 
      (f 
        ((n f) x)))))

(define one
  (lambda (f)
    (lambda (x)
      (f
        ((lambda (f) (lambda (x) x)) f) x))))

(define two
  (lambda (f) 
    (lambda (x) 
      (f 
        (((lambda (f)
            (lambda (x)
              (f
                ((lambda (f) 
                   (lambda (x) x)) f) x))) f) x)))))

(define (add n1 n2)
  (lambda (f) (n1 n2)))
