#include <stdio.h>

#define MAX 10000

long q[MAX + 1];
int qSize = 0;
int qIndex = 0;

int COUNT[1000005];

main() {
	
	int i;
	int j;

	for (i = 0; i < 1000005; i++) {
		COUNT[i] = 0;
	}

	while(scanf("%d %d", &i, &j) != EOF) {
		
		int low = i;
		int high = j;
		
		if (i > j) {
			low = j;
			high = i;
		}
		
		int max = 0;
		int k;
		for (k = low; k <= high; k++) {
			qSize = 0;
			qIndex = 0;

			int count = 1;
			long x = k;		

			while (x != 1) {

				if (x < 1000005) {
					if (COUNT[x] != 0) {
						count += COUNT[x] - 1;
						break;
					}
				}

				if (x > 1000004) {
					add(-1);
				} else {
					add(x);
				}

				if (x % 2 == 0) {
					x = x / 2;
				} else {
					x = 3 * x + 1;
				}

				count++;
			}

			int dist = 0;
			
			while (qSize != 0) {
				int cur = poll();

				if (cur != -1) {
					COUNT[cur] = count - dist;
				}

				dist++;
			}

			COUNT[k] = count;

			if (count > max) max = count;

		}

		printf("%d %d %d\n", i, j, max);

	}
}

int poll() {
	if (qIndex < MAX) {
		qSize--;
		qIndex++;
		return q[qIndex - 1];
	}	
	return -1;
}

int add(long x) {
	if (qIndex + qSize < MAX) {
		q[qIndex + qSize] = x;
		qSize++;
		return 1;
	} else {
		return -1;
	}
}

int clearQueue() {
	qIndex = 0;
	qSize = 0;
	return 1;
}
