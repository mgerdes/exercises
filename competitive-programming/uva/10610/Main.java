import java.util.*;

class Hole {
	float x;
	float y;
	int depth = 0;
	boolean visited = false;

	Hole(float x, float y) {
		this.x = x;
		this.y = y;
	}
}

class Main {
	static Hole[] holes = new Hole[1050];

	static int v;
	static int m;
	static int numberOfHoles; // number of holes.

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		long startTime = System.currentTimeMillis();

		while (true) {
			String line = input.nextLine();
			String[] tokens = line.split(" ");

			v = Integer.parseInt(tokens[0]);
			m = Integer.parseInt(tokens[1]);

			if (v == 0 && m == 0) break;

			int i = 0;
			while (true) {
				line = input.nextLine();
				if (line.equals("")) break;

				tokens = line.split(" ");

				holes[i] = new Hole(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]));

				i++;
			}

			numberOfHoles = i;

			int holesTraversed = bfs();

			if (holesTraversed != -1) {
				System.out.printf("Yes, visiting %d other holes.", holesTraversed);
			} else {
				System.out.print("No.");
			}

			System.out.println();
		}

		//System.out.println(System.currentTimeMillis() - startTime);
	}

	static int bfs() {
		Hole dest = holes[1];
		float destX = dest.x; // x coord of destination
		float destY = dest.y; // y coord

		LinkedList<Hole> q = new LinkedList<Hole>();

		q.add(holes[0]); // add begining hole to queue.

		while (!q.isEmpty()) {
			Hole cur = q.poll();

			float curX = cur.x;
			float curY = cur.y;
			int curDepth = cur.depth;

			for (int i = 1; i < numberOfHoles; i++) {

				Hole next = holes[i];

				if (!next.visited) {

					float nextX = next.x;
					float nextY = next.y;

					double distSqrd = (curX - nextX) * (curX - nextX) + (curY - nextY) * (curY - nextY);
					double oSqrd = v * v * m * m * 3600;

					if (distSqrd <= oSqrd) {

						if (nextX == destX && nextY == destY) return curDepth;

						next.depth = curDepth + 1;
						next.visited = true;

						q.add(next);

					}

				}

			}

		}

		return -1;
	}
}
