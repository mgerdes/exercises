/*
 * Copies stdin to stdout, replacing tabs with \t,
 * backspaces with \b, and backslashes with \\
 */

#include <stdio.h>

int main() {
    int c;

    while ((c = getchar()) != EOF) {
        if (c == '\b') {
            putchar('\\');
            putchar('b');
        }
        else if (c == '\t') {
            putchar('\\');
            putchar('t');
        }
        else if (c == '\\') {
            putchar('\\');
            putchar('\\');
        }
        else {
            putchar(c);
        }
    }

    return 0;
}
