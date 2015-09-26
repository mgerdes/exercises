import java.util.*;

class Word {

	String word;
	int depth;

	Word(String word, int depth) {
		this.word = word;
		this.depth = depth;
	}
}

class Main {

	static long startTime = System.currentTimeMillis();

	static ArrayList<String> dictionary = new ArrayList<String>(200);
	static boolean[] visited = new boolean[200];

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		int n = Integer.parseInt(input.nextLine()); // num of cases.

		for (int i = 0; i < n; i++) {
			dictionary.clear();

			String word = input.nextLine();

			int w = 0; // num of words.

			while (!word.equals("*")) {
				dictionary.add(word);
				w++;
				word = input.nextLine();
			}

			String line = input.nextLine();

			while (true) {
				for (int j = 0; j < w; j++) {
					visited[j] = false;
				}

				String[] arr = line.split(" ");
				if (arr.length != 2) break;

				String source = arr[0];
				String dest = arr[1];
				System.out.printf("%s %s %d\n", source, dest, dist(source,dest));

				if (!input.hasNextLine()) break;

				line = input.nextLine();
			}
			if (i < n - 1)
				System.out.println();
		}
	}

	static int dist(String source, String dest) {

		if (source.equals(dest)) return 0;

		LinkedList<Word> q = new LinkedList<Word>();

		q.add(new Word(source, 0));
		visited[indexOf(source)] = true;

		while(!q.isEmpty()) {
			Word current = q.poll();
			for (int i = 0; i < dictionary.size(); i++) {
				String word = dictionary.get(i);

				if (connected(current.word, word) && !visited[indexOf(word)]) {
					if(dest.equals(word)) return current.depth + 1;
					q.add(new Word(word, current.depth + 1));
					visited[indexOf(word)] = true;
				}
			}
		}

		return -1;
	}

	static boolean connected(String one, String two) {
		char[] oneArr = one.toCharArray();
		char[] twoArr = two.toCharArray();

		if (oneArr.length != twoArr.length) return false;

		int difference = 0;

		for (int i = 0; i < oneArr.length; i++) {
			if (oneArr[i] != twoArr[i]) difference++;
		}

		if (difference > 1) return false;

		return true;
	}

	static int indexOf(String s) {
		for (int i = 0; i < dictionary.size(); i++) {
			if (dictionary.get(i).equals(s)) return i;
		}
		return -1;
	}
}
