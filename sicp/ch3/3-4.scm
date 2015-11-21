(define (call-the-cops)
  "Cops are incoming")

(define (make-account amount password)
  (let ((wrong-guesses 0))
    (lambda (password-guess request) 
      (lambda (request-amount)
        (cond ((not (eq? password-guess password))
               (begin
                 (set! wrong-guesses (+ 1 wrong-guesses))
                 (if (> wrong-guesses 7)
                   (call-the-cops)
                   "Incorrect Password")))
              (else
                (begin
                  (set! wrong-guesses 0)
                  (cond ((eq? request 'withdraw)
                         (begin
                           (set! amount (- amount request-amount))
                           amount))
                        ((eq? request 'deposit)
                         (begin
                           (set! amount (+ amount request-amount))
                           amount))
                        (else "Invalid Request")))))))))

(define acc (make-account 100 'password))

((acc 'password 'withdraw) 40) ; => 60

((acc 'wrong 'deposit) 40) ; => "Incorrect Password"
((acc 'wrong 'deposit) 40) ; => "Incorrect Password"
((acc 'wrong 'deposit) 40) ; => "Incorrect Password"
((acc 'wrong 'deposit) 40) ; => "Incorrect Password"
((acc 'wrong 'deposit) 40) ; => "Incorrect Password"
((acc 'wrong 'deposit) 40) ; => "Incorrect Password"
((acc 'wrong 'deposit) 40) ; => "Incorrect Password"
((acc 'wrong 'deposit) 40) ; => "Cops are incoming"
