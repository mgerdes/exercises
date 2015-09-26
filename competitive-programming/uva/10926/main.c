#include <stdio.h>
#include <stdbool.h>

#define MAX_NODES 105

struct node;
int numOfDependencies(int);
int setVisitedFalse();

struct node {
	int index, numOfConnections;
	int connections[MAX_NODES];
};

struct node nodes[MAX_NODES];
bool visited[MAX_NODES];

int main() {
	int numOfNodes, nodeNum, numOfConnections, connectionNum, caseNum, i;
	
	scanf("%d", &numOfNodes);
	while (numOfNodes != 0) {

		for (nodeNum = 1; nodeNum <= numOfNodes; nodeNum++) {

			scanf("%d", &numOfConnections);				

			nodes[nodeNum].index = nodeNum;
			nodes[nodeNum].numOfConnections = numOfConnections;

			for (connectionNum = 1; connectionNum <= numOfConnections; connectionNum++) {
				scanf("%d", &(nodes[nodeNum].connections[connectionNum])); 	
			}

		}

		int maxNumDeps = -1;
		int maxNodeIndex = -1;
		for (nodeNum = 1; nodeNum <= numOfNodes; nodeNum++) {
			setVisitedFalse();
			int numOfDeps = numOfDependencies(nodeNum);
			if (numOfDeps > maxNumDeps) {
				maxNumDeps = numOfDeps;
				maxNodeIndex = nodeNum;
			}
		}

		printf("%d\n", maxNodeIndex);

		caseNum++;	
		scanf("%d", &numOfNodes);
	}

	return 0;
}

int setVisitedFalse() {
	int i;
	for (i = 0; i < MAX_NODES; i++) {
		visited[i] = false;
	}
}

int numOfDependencies(int nodeIndex) {
	visited[nodeIndex] = true;

	if (nodes[nodeIndex].numOfConnections == 0) return 0;

	int numOfDeps = 0, connectionNum;
	for (connectionNum = 1; connectionNum <= nodes[nodeIndex].numOfConnections; connectionNum++) {
		if (!visited[nodes[nodeIndex].connections[connectionNum]]) {
			numOfDeps += 1 + numOfDependencies(nodes[nodeIndex].connections[connectionNum]);
		}
	}

	return numOfDeps;
}
