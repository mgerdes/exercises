(define (add-streams s1 s2)
  (stream-map + s1 s2))

(define s (cons-stream 1 (add-streams s s)))

; s = (1 2 4 8 16 ...)
; It's essentially the same thing as: 
(define double (cons-stream 1 (scale-stream double 2)))
