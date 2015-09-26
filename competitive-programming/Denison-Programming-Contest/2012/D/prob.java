import java.util.*;

class Node {
	int number;
	boolean isRes;
	boolean visited;
	float waterCount = 0;

	Node(int number) {
		this.number = number;
	}

	Node leftChild;
	Node rightChild;
}

class Main {
	static int numberOfNodes;
	static int numberOfRes;

	static Node[] nodes; 
	static int[] res;

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);		
		
		while ((numberOfNodes = input.nextInt()) != 0) {
			numberOfNodes++;
			numberOfRes = input.nextInt();
			res = new int[numberOfRes];

			nodes = new Node[numberOfNodes];
			for (int i = 0; i < numberOfNodes; i++) {
				nodes[i] = new Node(i);
			}

			for (int i = 0; i < numberOfRes; i++) {
				int nodeNum = input.nextInt();
				res[i] = nodeNum;
				nodes[nodeNum].isRes = true;
			}

			for (int i = 0; i < numberOfNodes - numberOfRes; i++) {
				int nodeNum = input.nextInt();
				nodes[nodeNum].leftChild = nodes[input.nextInt()];
				nodes[nodeNum].rightChild = nodes[input.nextInt()];
			}

			nodes[0].waterCount = 1;

			trackWater(nodes[0]);

			for (int i = 0; i < numberOfRes; i++) {
				String waterCount = "" + nodes[res[i]].waterCount;
				System.out.printf("%d:%s", nodes[res[i]].number, waterCount);
				if (i != numberOfRes - 1) System.out.printf(" ");
			}
			System.out.println();
		}
	}

	public static void trackWater(Node firstNode) {
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.add(firstNode);
		
		while (!queue.isEmpty()) {
			Node currentNode = queue.poll();	
			if (!currentNode.isRes) {
				currentNode.leftChild.waterCount += currentNode.waterCount / 2;
				if (!currentNode.leftChild.visited) {
					currentNode.leftChild.visited = true;
					queue.add(currentNode.leftChild);
				}

				currentNode.rightChild.waterCount += currentNode.waterCount / 2;
				if (!currentNode.rightChild.visited) {
					currentNode.rightChild.visited = true;
					queue.add(currentNode.rightChild);
				}
			}
		}
	}
}
