(define x 10)

(define s (make-serializer))

(parallel-execute (lambda () (set! x ((s (lambda () (* x x))))))
                  (s (lambda () (set! x (+ x 1)))))

; 121 and 101 still remain.
; Also now 100 is possible because the second process may be ignored depending
; on how it's interleaved with the first process now.
