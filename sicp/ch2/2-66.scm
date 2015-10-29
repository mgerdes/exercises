(define (key tree)
  (car tree))

(define (entry tree)
  (cadr tree))

(define (left-branch tree) 
  (caddr tree))

(define (right-branch tree)
  (cadddr tree))

(define (lookup key tree)
  (cond ((null? tree) '())
        ((= (key tree) key) (entry tree))
        ((> (key tree) key) (lookup key (left-branch tree)))
        ((< (key tree) key) (lookup key (right-branch tree)))))
