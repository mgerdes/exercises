(define (filtered-accumulate pred combiner null-value term a next b)
  (if (>= a b)
    null-value
    (combiner (if (pred a)
                (term a)
                null-value)
              (filtered-accumulate pred combiner null-value term (next a) next b))))

(define (sum-of-prime-squares a b)
  (filtered-accumulate prime? + 0 identity a inc b)) 

(define (product-of-relative-primes n)
  (filtered-accumulate relatively-prime? * 1 identity 1 inc n))
