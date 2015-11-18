(define (print-board board)
  (begin
    (newline)
    (map (lambda (row) 
           (begin
             (display row)
             (newline))) board)))

(define (nth n l)
  (if (zero? n)
    (car l)
    (nth (- n 1) (cdr l))))

(define (all-set-col? board)
  (cond ((null? board) #t)
        ((zero? (car board)) #f)
        (else (all-set-col? (cdr board)))))

(define (all-set? board)
  (cond ((null? board) #t)
        ((all-set-col? (car board)) (all-set? (cdr board)))
        (else #f)))

(define (valid-move? row col num board)
  (define (get-box row col board)
    (let ((rows-to-drop (* 3 (floor (/ row 3))))
          (cols-to-drop (* 3 (floor (/ col 3)))))
      (apply append
             (map (lambda (row)
                    (take (drop row cols-to-drop) 3))
                  (take (drop board rows-to-drop) 3)))))

  (define (get-row n board)
    (nth n board))

  (define (get-col n board)
    (fold-right (lambda (cur-row col)
                  (cons (nth n cur-row) col)) '() board))


  (define (all-set-before? row col board)
    (define (all-set-before-col? col board)
      (cond ((zero? col) #t)
            ((zero? (car board)) #f)
            (else (all-set-before-col? (- col 1) board))))

    (if (zero? row)
      (all-set-before-col? col (car board))
      (and (all-set-col? (car board)) 
           (all-set-before? (- row 1) col (cdr board)))))

  (and (not (member num (get-box row col board))) 
       (not (member num (get-row row board)))
       (not (member num (get-col col board)))
       (all-set-before? row col board)))

(define (not-set? row col board)
  (zero? (nth col (nth row board))))      

(define (set-col col num board)
  (if (zero? col)
    (cons num 
          (cdr board))
    (cons (car board) 
          (set-col (- col 1) num (cdr board)))))

(define (set-row-col row col num board)
  (if (zero? row)
    (cons (set-col col num (car board)) 
          (cdr board))
    (cons (car board) 
          (set-row-col (- row 1) col num (cdr board)))))

(define (range x y)
  (if (<= x y) 
    (cons x (range (+ x 1) y))
    '()))

(define (first-not-null l)
  (cond ((null? l) '())
        ((null? (car l)) (first-not-null (cdr l)))
        (else (car l))))

(define (solve row col board)
  (let ((next-row (if (= 8 col) (+ 1 row) row))
        (next-col (if (= 8 col) 0 (+ 1 col))))
    (cond ((all-set? board) board)
          ((not-set? row col board)
           (first-not-null
             (map (lambda (num)
                    (if (valid-move? row col num board)
                      (solve next-row next-col (set-row-col row col num board))
                      '()))
                  (range 1 9))))
          (else (solve next-row next-col board)))))

(define board '((0 2 0 5 0 1 4 0 3)
                (1 0 3 0 6 4 0 0 7)
                (0 0 4 3 0 0 0 5 1)
                (4 0 0 0 0 0 5 1 6)
                (0 6 0 0 1 3 0 4 0)
                (7 1 0 0 4 5 3 0 2)
                (0 9 2 1 0 6 0 0 0)
                (8 7 6 4 3 0 1 2 0)
                (0 0 0 0 7 0 6 0 0)))

(print-board (solve 0 0 board))
