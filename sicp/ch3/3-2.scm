(define (make-monitored f)
  (let ((count 0))
    (lambda (m)
      (if (eq? m 'how-many-calls?)
        count
        (begin
          (set! count (+ 1 count))
          (f m))))))

(define s (make-monitored sqrt))
(s 100) ; => 10
(s 'how-many-calls?) ; => 1
