#include <stdio.h>

#define MAX 4000000

int main() {

	int prev1 = 1, prev2 = 2, cur, sum = 2;

	while ((cur = prev1 + prev2) <= MAX) {
		if (cur % 2 == 0) sum += cur;
		
		prev1 = prev2;
		prev2 = cur;
	}

	printf("%d\n", sum);

	return 0;
}
