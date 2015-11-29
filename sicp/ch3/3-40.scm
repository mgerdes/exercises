(define x 10)

(parallel-execute (lambda () (set! x (* x x)))
                  (lambda () (set! x (* x x x))))


; 1000000  -> P1 sets x to 100, then P2 sets x to 1000000.
; 10000    -> P1 first gets x as 10, then P2 sets x to 1000, then P1 gets second x as 1000.
; 100000   -> P2 first gets x as 10, then P1 sets x to 100, then P2 gets two other x's as 100.
; 100      -> P1 gets both x's as 10, P2 sets x to 1000, then P1 sets x to 100.
; 1000     -> P2 get's all x's as 10, P1 sets x to 100, then P2 sets x to 1000.

(define x 10)

(define s (make-serializer))

(parallel-execute (s (lambda () (set! x (* x x))))

                  (s (lambda () (set! x (* x x x)))))

; Now only 1000000 exists, as the two processes can no longer be interleaved.
