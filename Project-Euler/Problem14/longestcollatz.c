#include <stdio.h>

#define MAX 1000000

int main() {
	int maxChainLength = 1;
	int maxCollatz = 1;
		
	int i;
	for (i = 1; i < MAX; i++) {
		long current_collatz = i;			
		int chainLength = 1;

		while (current_collatz != 1) {
			if (current_collatz % 2 == 0) {
				current_collatz = current_collatz / 2;
			} else {
				current_collatz = 3 * current_collatz + 1;
			}
			chainLength++;
		}

		if (chainLength > maxChainLength) {
			maxChainLength = chainLength;
			maxCollatz = i;
		}
	}

	printf("%d\n", maxCollatz);
}
