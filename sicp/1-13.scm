(define (cube x) (* x x x))

(define (p x) (- (* 3 x) (* 4 (cube x))))

(define (sine angle)
  (if (not (> (abs angle) 0.1))
    angle
    (p (sine (/ angle 3.0)))))

; (sine 12.15)
; (p (sine 4.05)) 
; (p (p (sine 1.35))) 
; (p (p (p (sine 0.45)))) 
; (p (p (p (p (sine 0.15))))) 
; (p (p (p (p (p (sine 0.05)))))) 
; (p (p (p (p (p 0.05))))) => 5 calls to function p

; time complexity is number of times p is called.
;    (x / 3^n) < 0.1
; => (x / 0.1) < 3^n
; => log_3(x / 0.1) < n
; 
; space complexity also number of times p is called.
; so space complexity and time complexity is log_3(x / 0.1)
