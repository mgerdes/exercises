(define (square x)
  (* x x))

(define (average x y)
  (/ (+ x y) 2))

(define (improve guess x)
  (average guess (/ x guess)))

(define (good-enough? guess x)
  (< (abs (- (square guess) x)) 0.001))

(define (sqrt-iter guess x)
  (if (good-enough? guess x)
    guess
    (sqrt-iter (improve guess x)
               x)))

(define (sqrt x)
  (sqrt-iter 1.0 x))

; (sqrt 0.00001) => 0.031356, but (sqrt 0.00001) is actually 0.00316, were off by a factor of 10. Indeed our good enough function is not good for small numbers.
; We used 0.001 as our epsilon here, the actual square root of 0.001 is 0.0316. 
; Therefore as x -> 0, the value our sqrt function returns -> 0.0316, because (< ((square 0.0316) - (small number)) 0.001) will evaluate to true. 

; An alternative good-enough? function is below it returns true once the delta between two consecutive guesses is sufficiently small. 

(define (alt-good-enough? guess prev-guess)
  (< (abs (- guess prev-guess)) 0.001))

(define (alt-sqrt-iter guess prev-guess x)
  (if (alt-good-enough? guess prev-guess)
    guess
    (alt-sqrt-iter (improve guess x) 
                   guess 
                   x))) 

(define (alt-sqrt x)
  (alt-sqrt-iter 1.0 0.0 x))

; (alt-sqrt 0.00001) => 3.172e^-3, much closer to the actual answer.
