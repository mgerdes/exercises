(set! balance (+ balance 10))
(set! balance (- balance 20))
(set! balance (- balance (/ balance 2)))

; (a) Possible values if no interleaving : 35, 40, 45, 50
; (b) Allowing functions to be interleaved allows even more values, like 110. 
