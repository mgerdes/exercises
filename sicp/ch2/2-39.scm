(define (reverse-f-r seq)
    (fold-right (lambda (x y) (append y (list x))) '() seq))

(define (reverse-f-l seq)
    (fold-left (lambda (x y) (cons y x)) '() seq))
