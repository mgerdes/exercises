(define (accumulate op initial sequence)
  (if (null? sequence)
    initial
    (op (car sequence)
        (accumulate op initial (cdr sequence)))))

(define (accumulate-n op init seqs)
  (if (null? (car seqs))
    '()
    (cons (accumulate op init (accumulate (lambda (x y) (cons (car x) y)) '() seqs))
          (accumulate-n op init (accumulate (lambda (x y) (cons (cdr x) y)) '() seqs)))))

(define (dot-prod v w)
  (accumulate + 0 (map * v w)))

(define (mat-*-vec m v)
  (map (lambda (v1) (dot-prod v1 v)) m))

(define (transpose mat)
  (accumulate-n cons '() mat))

(define (mat-*-mat m n)
  (let ((cols (transpose n)))
    (map (lambda (a) 
           (map (lambda (b) 
                  (dot-prod a b)) n)) 
         m)))
