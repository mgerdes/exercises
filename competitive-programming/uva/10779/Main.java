/*
The first line of input contains the number of cases T (T<=20).
The first line of each case contains two integers n and m (2<=n<=10, 5<=m<=25). n is the number of people involved (including Bob), and m is the number of different stickers available.
The next n lines describe each person's stickers; the first of these lines describes Bob's stickers.
The i-th of these lines starts with a number ki<=50 indicating how many stickers person i has.
Then follows ki numbers between 1 and m indicating which stickers person i possesses.

*/


import java.util.*;

class Trade {
	int source, destination, sticker;
	Trade parent;

	public Trade(int source, int destination, int sticker) {
		this.source = source;
		this.destination = destination;
		this.sticker = sticker;
	}
	
	void printIt() {
		System.out.printf("src - %d, dest = %d, sticker = %d\n", source, destination, sticker);
	}

}

class Person {
	int[] stickerCount = new int[26];
	int[] stickerCountFromTrades = new int[26];
	ArrayList<Trade> toHere = new ArrayList<Trade>();
	ArrayList<Trade> fromHere = new ArrayList<Trade>();

}

class Main {

	static Scanner input;
	static Person[] people = new Person[11];
	static boolean[][] personDoesNotHaveSticker = new boolean[10][26];

	static int numOfPeople;
	static int numOfStickers;

	static int caseNum;

	public static void main(String args[]) {

		input = new Scanner(System.in);

		int numOfCases = input.nextInt();

		for (int i = 0; i < 11; i++) {
			people[i] = new Person();
		}

		for (int i = 0; i < numOfCases; i++) {
			caseNum = i + 1;
			solveProblem();

			for (int j = 0; j <= numOfPeople; j++) {
				people[j].toHere.clear();
				people[j].fromHere.clear();
			
				for (int k = 1; k <= numOfStickers; k++) {
					people[j].stickerCount[k] = people[j].stickerCountFromTrades[k] = 0;
				}
			}
		}

	}

	static void solveProblem() {
		int n, m;
		n = input.nextInt();
		m = input.nextInt();

		numOfPeople = n;
		numOfStickers = m;

		for (int i = 0; i < n; i++) {
			int stickerCount = input.nextInt();
			for (int j = 0; j < stickerCount; j++) {
				int stickerNumber = input.nextInt();
				people[i].stickerCount[stickerNumber]++;

				if (i == 0)
					people[n].stickerCount[stickerNumber]++;
			}
		}

		printOriginalStickers();

		//--Figure out what stickers people don't have
		for (int i = 0; i < n; i++) {
			for (int j = 1; j <= m; j++) {
				if (people[i].stickerCount[j] == 0)
					personDoesNotHaveSticker[i][j] = true;
			}
		}

		//--Build graph
		for (int i = 0; i < n; i++) {
			for (int j = 1; j <= m; j++) {
				if (people[i].stickerCount[j] > 1) {
					for (int k = 0; k < n; k++) {
						if (personDoesNotHaveSticker[k][j]) {
							int destination = k;
							if (k == 0) {
								destination = n;
							}

							Trade trade = new Trade(i, destination, j);
							people[i].fromHere.add(trade);

							//--Bob is person 0 and person n
							//trades from bob are from person 0
							//trades to bob are to person n
							if (k == 0) {
								people[n].toHere.add(trade);
							} else {
								people[k].toHere.add(trade);
							}
						}

					}

				}
			}
		}

		// go from person 0 to person n.

		while (true) {
			Trade finalTrade = null;
			LinkedList<Trade> q = new LinkedList<Trade>();
			boolean visited[] = new boolean[n + 1];

			for (Trade trade : people[0].fromHere) {
				if (tradeIsValid(trade)) {
					q.add(trade);
				}
			}

			while (!q.isEmpty()) {
				Trade currentTrade = q.poll();	
				if (currentTrade.destination == n) {
					finalTrade = currentTrade;
					break;	
				}
				visited[currentTrade.source] = true;
				
				for (Trade trade : people[currentTrade.destination].fromHere) {
					if (!visited[trade.destination]
						&& tradeIsValid(trade)) {
						trade.parent = currentTrade;
						q.add(trade);
					}
				}
			}

			if (finalTrade != null) {
				processAllTrades(finalTrade);
			}
			else {
				break;
			}
		}

		int originalStickers = 0;
		for (int i = 1; i <= numOfStickers; i++) {
			if (people[0].stickerCount[i] != 0)
				originalStickers++;
		}

		int newStickers = 0;
		for (int i = 0; i <= numOfStickers; i++) {
			if (people[n].stickerCountFromTrades[i] != 0)
				newStickers++;
		}

		int result = originalStickers + newStickers;
		System.out.println("Case #" + caseNum + ": " +  result);
	}

	private static int getUniqueStickers(int n, int numStickers) {
		Person person = people[n];
		int count = 0;

		for (int i = 1; i <= numStickers; i++) {
			if (person.stickerCount[i] != 0
				|| person.stickerCountFromTrades[i] != 0) {
				count++;
			}
		}

		return count;
	}

	private static void processAllTrades(Trade trade) {
		Trade current = trade;
		while (current != null) {
			//printStickerCounts();
			//current.printIt();
			adjustStickerCount(current);
			makeResidual(current);
			current = current.parent;
		}
	}

	private static void makeResidual(Trade trade) {
		Trade residual = new Trade(trade.destination, trade.source, trade.sticker);

		people[trade.destination].fromHere.add(residual);
		people[trade.source].toHere.add(residual);

		people[trade.source].fromHere.remove(trade);
		people[trade.destination].fromHere.remove(trade);
	}

	private static boolean tradeIsValid(Trade trade) {
		Person source = people[trade.source];
		Person dest = people[trade.destination];
		int sticker = trade.sticker;
		boolean sourceIsValid = source.stickerCount[sticker] > 1
					|| source.stickerCountFromTrades[sticker] > 0;
		boolean destinationIsValid = dest.stickerCount[sticker] == 0
						&& dest.stickerCountFromTrades[sticker] == 0;
		return sourceIsValid && destinationIsValid;
	}

	private static void adjustStickerCount(Trade trade) {

		if (people[trade.source].stickerCount[trade.sticker] == 0) {
			people[trade.source].stickerCountFromTrades[trade.sticker]--;
		} else {
			people[trade.source].stickerCount[trade.sticker]--;
		}

		people[trade.destination].stickerCountFromTrades[trade.sticker]++;
	}

	private static void printStickerCounts() {
		for (int i = 0; i <= numOfPeople; i++) {
			if (i == 0 || i == numOfPeople) {
				for (int j = 0; j <= numOfStickers; j++) {
					System.out.printf("%d, ", people[i].stickerCount[j]);
				}
				System.out.println();
				for (int j = 0; j <= numOfStickers; j++) {
					System.out.printf("%d, ", people[i].stickerCountFromTrades[j]);
				}
				System.out.println();
			}
		}
	}

	private static void printOriginalStickers() {
		for (int i = 0; i < numOfPeople; i++) {
			for (int j = 1; j <= numOfStickers; j++) {
				System.out.printf("%d, ", people[i].stickerCount[j]);
			}
			System.out.println();
		}
	}
}
