import java.util.*;

class Main {
	static int[] points;

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		int numberOfPoints;
		int caseNum = 1;

		while ((numberOfPoints = input.nextInt()) != 0) {
			points = new int[numberOfPoints];

			for (int i = 0; i < numberOfPoints; i++) {
				points[i] = input.nextInt();
			}

			int position = points[0];
			int pointsFound = 0;
			int furthestPointRight = position;
			for (int i = 1; i <= 180; i++) {
				position = (position + 1) % 360;	
				if (containsPoint(position)) {
					pointsFound++;
					furthestPointRight = position;
				}
			}

			int wiggleRoom = 180 - (furthestPointRight - points[0] + 360) % 360;
			if (pointsFound == 1 && wiggleRoom == 0) {
				wiggleRoom = 180;
			}

			position = points[0];
			int furthestPointLeft = position;
			for (int i = 1; i < 180; i++) {
				position = (position - 1 + 360) % 360;	
				if (containsPoint(position)) {
					furthestPointLeft = position;
				}
			}
			int distance = (points[0] - furthestPointLeft + 360) % 360; 

			boolean yes = distance <= wiggleRoom;

			System.out.printf("Case %d: %s\n", caseNum++, yes ? "YES" : "NO");
		}
	}

	public static boolean containsPoint(int position) {
		for (int point : points) {
			if (point == position) return true;
		}
		return false;
	}
}
