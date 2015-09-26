import java.util.*;

class Main {
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);

		int numberOfMoves;
		int caseNum = 1;

		while ((numberOfMoves = input.nextInt()) != 0) {
			String race = input.next();

			int toadsPosition = 0;
			int frogsPosition = 1;

			for (int i = 0; i < numberOfMoves; i++) {
				if (race.charAt(i) == 'T') {
					toadsPosition++;	
					if (toadsPosition == frogsPosition) toadsPosition++;
				} else {
					frogsPosition++;	
					if (toadsPosition == frogsPosition) frogsPosition++;
				}
			}

			System.out.printf("Case %d: %s\n", caseNum++, frogsPosition > toadsPosition ? "Frog" : "Toad");
		}

	}
}
