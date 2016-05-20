/*
 * Counts blanks, tabs, and newlines from stdin
 */

#include <stdio.h>

int main() {
    long nb, nt, nl;
    int c;

    nb = nt = nl = 0;

    while ((c = getchar()) != EOF) {
        if (c == ' ')
            nb++;
        if (c == '\n')
            nl++;
        if (c == '\t')
            nt++;
    }

    printf("%d blanks, %d newlines, %d tabs\n", nb, nl, nt);
}
