(define (leaf? tree)
  (eq? 'leaf (car tree)))

(define (symbol leaf)
  (cadr leaf))

(define (left-branch tree)
  (cadr tree))

(define (right-branch tree)
  (caddr tree))

(define (decode-bits bits tree)
  (define (decode-bits-1 bits current-tree)
    (if (null? bits)
      (cons (symbol current-tree) '())
      (if (leaf? current-tree)
        (cons (symbol current-tree)
              (decode-bits-1 bits tree))
        (if (= 0 (car bits))
          (decode-bits-1 (cdr bits)
                         (left-branch current-tree))
          (decode-bits-1 (cdr bits)
                         (right-branch current-tree))))))
  (decode-bits-1 bits tree))

(decode-bits '(0 1 1 0 0 1 0 1 0 1 1 1 0) '(tree (leaf A) 
                                                 (tree (leaf B)
                                                       (tree (leaf D)
                                                             (leaf C)))))
; => (a d a b b c a)