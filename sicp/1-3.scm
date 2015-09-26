(define (sum-of-squares x y)
  (+ (* x x) (* y y)))

(define (sum-of-larger-squares x y z)
  (cond ((>= x y z) (sum-of-squares x y))
        ((>= x z y) (sum-of-squares x z))
        ((>= y z x) (sum-of-squares y z))))
