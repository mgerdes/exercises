#include <stdio.h>

int main() {

	int  i, count = 0;

	for (i = 0; 3 * i < 1000; i++) {
		if (5 * i < 1000) count += 5 *i;
		if ((3 * i % 5) != 0) count += 3 * i;
	}

	printf("%d\n", count);

	return 0;
}
