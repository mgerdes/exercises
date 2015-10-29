(define (expmod base exp)
  (cond ((= exp 0) 1)
        ((even? exp)
         (remainder (* (expmod base (/ exp 2) m)
                       (expmod base (/ exp 2) m))
                    m))
        (else
          (remainder (* base (expmod base (- exp 1) m))
                     m))))

; by calling * explicitly, instead of using square, (expmod base (/ exp 2) m) has to
; now be called twice on each iteration, instead of just once.
