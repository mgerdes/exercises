#include <stdio.h>
#include <stdbool.h>

bool divisibleByAll(int num) {
	int i;
	for (i = 1; i <= 20; i++) {
		if (num % i != 0) return false;
	}
	return true;
}

int main() {
	int num = 20;
	
	while (true) {
		if (divisibleByAll(num)) break;
		num++;	
	}

	printf("%d\n", num);

	return 0;
}
