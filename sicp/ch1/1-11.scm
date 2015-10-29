; f(n) = n if n < 3
; f(n) = f(n-1) + 2f(n-2) + 3f(n-3) if n >= 3

; recursive definition
(define (f-recur n)
  (if (< n 3) 
    n
    (+ (f-recur (- n 1))
       (* 2 (f-recur (- n 2))) 
       (* 3 (f-recur (- n 3))))))

; iterative definition
(define (f-iter prev1 prev2 prev3 i)
  (cond ((= i 0) prev1)
        ((= i 1) prev2)
        ((= i 2) prev3)
        (else (f-iter prev2 
                      prev3 
                      (+ prev3 (* 2 prev2) (* 3 prev1)) 
                      (- i 1)))))  

; kick off f-iter
(define (f n)
  (f-iter 0 1 2 n))

