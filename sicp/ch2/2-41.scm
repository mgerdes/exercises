(define (sum-to? n s)
  (= n (+ (car s) (cadr s) (caddr s))))

(define (unique-pairs n)
  (flatmap (lambda (i)
             (flatmap (lambda (j)
                        (map (lambda (k) (list i j k))
                                 (range (+ 1 j) n)))
                      (range (+ 1 i) n)))
           (range 1 n)))

(filter (lambda (s) (sum-to? 10 s)) (unique-pairs 10))
