/*
 * Replaces multiples blanks with a single blank from stdin to stdout
 */

#include <stdio.h>

int main() {
    int c, was_prev_blank;

    was_prev_blank == 0;
    
    while ((c = getchar()) != EOF) {
        if (c != ' ' || !was_prev_blank) {
            putchar(c);
        }
        was_prev_blank = (c == ' ');
    }
}
