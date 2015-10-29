(define (gcd a b)
  (if (= b 0)
    a
    (gcd b (remainder a b))))

; normal order:
; only b is evaluated in loop
;
; (gcd 206 
;      40)
;
; (gcd 40 
;      (remainder 206 40)) ; 4 times
;
; (gcd (remainder 206 40) 
;      (remainder 40 (remainder 206 40))) ; 2 times
;
; (gcd (remainder 40 (remainder 206 40)) 
;      (remainder (remainder 206 40) (remainder 40 (remainder 206 40)))) ; 4 times
;
; (gcd (remainder (remainder 206 40) (remainder 40 (remainder 206 40)))
;      (remainder (remainder 40 (remainder 206 40)) (remainder (remainder 206 40) (remainder 40 (remainder 206 40))))) ; 8 times
;
; (remainder (remainder 206 40) (remainder 40 (remainder 206 40))) ; 4 times
;
; (remainder (remainder 206 40) (remainder 40 6))
;
; (remainder (remainder 206 40) 4)
;
; (remainder 6 4)
;
; 2
;
; remainder is called 18 times.

; applicative order
;
; (gcd 206 40) ; 1 time
; (gcd 40 6) ; 1 time
; (gcd 6 4) ; 1 time
; (gcd 4 2) ; 1 time
; (gcd 2 0) ; 0 times, a is returned 
;
; remainder is called 4 times.
