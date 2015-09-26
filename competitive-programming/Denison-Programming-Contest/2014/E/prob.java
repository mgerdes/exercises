import java.util.*;

class House implements Comparable<House> {
	boolean baught;
	int price;
	int sqFootage;

	public House(int price, int sqFootage) {
		this.price = price;
		this.sqFootage = sqFootage;
	}

	@Override
	public int compareTo(House otherHouse) {
		return this.sqFootage - otherHouse.sqFootage;
	}
}

class Main {
	static House[] houses;
	static int[] buyers;

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);	

		int caseNum = 1;
		int numberOfHouses;
		while ((numberOfHouses = input.nextInt()) != 0) {
			houses = new House[numberOfHouses];
			for (int i = 0; i < numberOfHouses; i++) {
				int sqFootage = input.nextInt();
				int price = input.nextInt();
				houses[i] = new House(price, sqFootage);		
			}
			int numberOfBuyers = input.nextInt();
			buyers = new int[numberOfBuyers];
			for (int i = 0; i < numberOfBuyers; i++) {
				buyers[i] = input.nextInt();
			}
			Arrays.sort(houses);
			Arrays.sort(buyers);
			
			int maxMoney = 0;
			for (int i = numberOfBuyers - 1; i >= 0; i--) {
				maxMoney += mostExpensiveHouseFor(i);	
			}
			System.out.printf("Case %d: %d\n", caseNum++, maxMoney);
		}
	}

	public static int mostExpensiveHouseFor(int buyerNum) {
		int maxCost = 0;
		int maxCostNum = -1;
		int minSqFootage = buyers[buyerNum];
		for (int i = houses.length - 1; i >= 0; i--) {
			if (houses[i].sqFootage < minSqFootage) break;	
			if (!houses[i].baught && houses[i].price > maxCost) {
				maxCost = houses[i].price;
				maxCostNum = i;
			}
		}
		if (maxCostNum != -1) {
			houses[maxCostNum].baught = true;
		}
		return maxCost;
	}
}
