using System;

class Volumes {
	public static void Main(string[] args) {
		double volume;
		if (args.Length > 0) {
			volume = double.Parse(args[0]);	
		} else {
			Console.Write("Input a volume: ");
			volume = double.Parse(Console.ReadLine());
		}
		printDimensions(volume);
	}

	static void printDimensions(double volume) {
		printDimensionsOfCube(volume);
		printDimensionsOfSphere(volume);
		printDimensionsOfCylinder(volume);
		printDimensionsOfCone(volume);
	}

	static void printDimensionsOfCube(double volume) {
		double length = Math.Pow(volume, 1.0 / 3.0);
		Console.WriteLine("Cube: {0:N2}m width, {0:N2}m high, {0:N2}m tall", length);
	}

	static void printDimensionsOfSphere(double volume) {
		double radius = Math.Pow((volume * 3.0) / (4.0 * Math.PI), 1.0 / 3.0);
		Console.WriteLine("Sphere: {0:N2}m radius", radius);
	}

	static void printDimensionsOfCylinder(double volume) {
		double radiusAndHeight = Math.Pow(volume / Math.PI, 1.0 / 3.0); // Assume height and radius are equal.
		Console.WriteLine("Cylinder: {0:N2}m tall, {0:N2}m radius", radiusAndHeight);
	}

	static void printDimensionsOfCone(double volume) {
		double radiusAndHeight = Math.Pow((volume * 3.0) / Math.PI, 1.0 / 3.0);	// Assume height and radius are equal.
		Console.WriteLine("Cone: {0:N2}m tall, {0:N2}m radius", radiusAndHeight); 
	}
}
