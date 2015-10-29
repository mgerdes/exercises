(define (accumulate op initial sequence)
  (if (null? sequence)
    initial
    (op (car sequence)
        (accumulate op initial (cdr sequence)))))

(define (flatmap proc seq)
  (accumulate append '() (map proc seq)))

(define (range a b)
  (if (> a b)
    '()
    (cons a (range (+ a 1) b))))

(define (unique-pairs n)
  (flatmap (lambda (i)
             (map (lambda (j) (list j i))
                  (range 1 (- i 1))))
           (range 1 n)))

(define (prime-sum-pairs n)
  (filter prime-sum? (unique-pairs n)))
