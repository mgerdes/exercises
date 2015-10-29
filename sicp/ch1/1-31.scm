(define (inc x) (+ x 1))
(define (inc-2 x) (+ x 2))
(define (identity x) x)
(define (square x) (* x x))

(define (product-recur term a next b)
    (if (>= a b)
      (term a)
      (* (term a) (product-recur term (next a) next b)))) 

(define (product-iter term a next b)
  (define (iter a result)
    (if (> a b)
      result
      (iter (next a) (* (term a) result))))
  (iter a 1))

(define (factorial n)
    (product-recur identity 1 inc n))

(define (pi-term x)
    (* (/ x (inc x)) (/ (inc-2 x) (inc x))))

(define (pi n)
    (exact->inexact (* 4 (product-iter pi-term 2 inc-2 n))))
