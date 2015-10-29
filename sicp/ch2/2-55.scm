(car ''abracadabra) ; => quote

; ''abracadabra is really (quote (quote abracadabra)), which will
; evaluate to (quote abradabara), which has a car of quote.
