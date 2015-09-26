import java.util.*;

class Main {

	static char[] parking;
	static int numberOfCarsLeft;

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		int numberOfCars;
		int numberOfDepartures;

		while((numberOfCars = input.nextInt()) != 0) {
			numberOfCarsLeft = numberOfCars;
			parking = new char[numberOfCars];
			numberOfDepartures = input.nextInt();

			for (int i = 0; i < numberOfCars; i++) {
				parking[i] = (char)((int)'A' + i);
			}

			System.out.println("*");
			for (int i = 0; i < numberOfDepartures; i++) {
				depart(input.next().charAt(0));
				for (int j = 0; j < numberOfCarsLeft; j++) {
					System.out.print(parking[j]);
				}
				System.out.println();
			}
		}
	}

	public static void depart(char car) {
		int position = positionOfCar(car);		

		// closer to left or right;
		if (position <= numberOfCarsLeft - 1 - position) {
			// closer to left
			removeLeft(position);
		} else {
			// closer to right
			removeRight(position);
		}

		numberOfCarsLeft--;
	}

	public static void removeRight(int position) {
		for (int i = position; i < numberOfCarsLeft - 1; i++) {
			parking[i] = parking[i + 1];
		}
		reverse(position, numberOfCarsLeft - 1);
	}

	public static void removeLeft(int position) {
		for (int i = position; i < numberOfCarsLeft - 1; i++) {
			parking[i] = parking[i + 1];
		}
		reverse(0, position);
	}

	public static void reverse(int i, int j) {
		for (int k = 0; k < (j - i) / 2; k++) {
			swap(k + i, j - k - 1);
		}
	}

	public static void swap(int i, int j) {
		char temp = parking[i];
		parking[i] = parking[j];
		parking[j] = temp;
	}

	public static int positionOfCar(char car) {
		for (int i = 0; i < numberOfCarsLeft; i++) {
			if (car == parking[i]) return i;
		}
		return -1;
	}
}
