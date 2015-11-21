(define (leaf? tree)
  (eq? 'leaf (car tree)))

(define (symbols tree)
  (if (leaf? tree)
    (cons (symbol tree) '())
    (cadr tree)))

(define (symbol leaf)
  (cadr leaf))

(define (left-branch tree)
  (caddr tree))

(define (right-branch tree)
  (cadddr tree))

(define (contains? symbol set)
  (cond ((null? set) #f)
        ((eq? symbol (car set)) #t)
        (else (contains? symbol (cdr set)))))
    
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

(define (encode-symbol symbol tree)
  (if (leaf? tree)
    '()
    (if (contains? symbol (symbols (left-branch tree)))
      (cons 0 (encode-symbol symbol (left-branch tree)))
      (cons 1 (encode-symbol symbol (right-branch tree))))))

(define (encode message tree)
  (if (null? message)
    '()
    (append (encode-symbol (car message) tree)
            (encode (cdr message) tree))))

(encode '(A D A B B C A)
        '(tree (A B C D)
               (leaf A) 
               (tree (B C D)
                     (leaf B)
                     (tree (C D)
                           (leaf D)
                           (leaf C)))))
