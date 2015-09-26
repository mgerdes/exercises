import java.util.*;


class Main {

	static int N;
	static int t;

	static int d[] = new int[105]; 
	static int l[] = new int[105];
	static int p[] = new int[105];

	static boolean ap[] = new boolean[105];
	static boolean visited[] = new boolean[105];
	static boolean adj[][] = new boolean[105][105];

	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);

		while ((N = Integer.parseInt(in.nextLine())) != 0) {

			memset();
			
			String stuff[];
			while ((stuff = in.nextLine().split(" ")).length > 1) {
				int i = Integer.parseInt(stuff[0]);
				for (int j = 1; j < stuff.length; j++) {
					int to = Integer.parseInt(stuff[j]);
					adj[i][to] = adj[to][i] = true;
				}
			}
			
			t = 1;
			dfs(1);	
				
			int num = 0;
			for (int i = 1; i <= N; i++) {
				if (ap[i]) num++;
			}
			System.out.println(num);

		}

	}

	static void memset() {
		for (int i = 0; i < 105; i++) {
			d[i] = 0;
			l[i] = 0;
			p[i] = 0;

			ap[i] = false;
			visited[i] = false;
			for (int j = 0; j < 105; j++) {
				adj[i][j] = false;
			}
		}
	}

	static void dfs(int x) {
		
		visited[x] = true;
		d[x] = l[x] = t++;
		int children = 0;

		for (int i = 1; i <= N; i++) {
			if (adj[x][i]) {
				if (!visited[i]) {
					children++;

					p[i] = x;
					dfs(i);

					l[x] = Math.min(l[x], l[i]);

					if (p[x] == 0 && children > 1) {
						ap[x] = true;
					}

					if (p[x] != 0 && l[i] >= d[x]) {
						ap[x] = true;
					}
				} else if (i != p[x]) {
					l[x] = Math.min(l[x], d[i]);
				}
			}
		}

	}

}
