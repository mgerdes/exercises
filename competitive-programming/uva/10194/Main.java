import java.util.*;

class Main {
	public static void main (String[] args) {
		Map<String, Integer> teamMap = new HashMap<String, Integer>();

		Scanner input = new Scanner(System.in);

		int n = Integer.parseInt(input.nextLine());

		for (int tournNum = 0; tournNum < n; tournNum++) {
			System.out.println(input.nextLine());

			int numTeams = Integer.parseInt(input.nextLine());
			Team[] teams = new Team[numTeams];
			for (int teamNum = 0; teamNum < numTeams; teamNum++) {
				teams[teamNum] = new Team(input.nextLine());
				teamMap.put(teams[teamNum].name, teamNum);
			}

			int numGames = Integer.parseInt(input.nextLine());
			for (int gameNum = 0; gameNum < numGames; gameNum++) {
				String[] results = input.nextLine().split("#");
				String[] scores = results[1].split("@");

				int team1ID = teamMap.get(results[0]);
				int team2ID = teamMap.get(results[2]);
				int team1Goals = Integer.parseInt(scores[0]);
				int team2Goals = Integer.parseInt(scores[1]);

				teams[team1ID].gamesPlayed++;
				teams[team2ID].gamesPlayed++;

				teams[team1ID].goalsScored += team1Goals;
				teams[team1ID].goalsAgainst += team2Goals;
				teams[team2ID].goalsScored += team2Goals;
				teams[team2ID].goalsAgainst += team1Goals;

				if (team1Goals > team2Goals) {
					teams[team1ID].wins++;
					teams[team2ID].losses++;
				} else if (team2Goals > team1Goals) {
					teams[team2ID].wins++;
					teams[team1ID].losses++;
				} else {
					teams[team1ID].ties++;
					teams[team2ID].ties++;
				}

				teams[team1ID].update();
				teams[team2ID].update();
			}

			PriorityQueue<Team> ranking = new PriorityQueue<Team>();

			for (int teamNum = 0; teamNum < numTeams; teamNum++) {
				ranking.add(teams[teamNum]);
			}

			int rank = 1;
			while (!ranking.isEmpty()) {
				System.out.println(rank + ") " + ranking.poll());
				rank++;
			}

			if (tournNum + 1 != n) System.out.println();
		}

	}
}

class Team implements Comparable<Team> {
	String name;

	int wins = 0;
	int losses = 0;
	int ties = 0;
	int gamesPlayed = 0;

	int goalsScored = 0;
	int goalsAgainst = 0;
	int goalDifference = 0;

	int totalPoints = 0;

	Team(String name) {
		this.name = name;
	}

	void update() {
		goalDifference = goalsScored - goalsAgainst;
		totalPoints = 3 * wins + ties;
	}

	@Override
	public String toString() {
		//Brazil 6p, 3g (2-0-1), 3gd (6-3)


		String re = this.name + " " + this.totalPoints + "p, " + this.gamesPlayed + "g (" 
			+ this.wins + "-" + this.ties + "-" + this.losses + "), " + this.goalDifference 
			+ "gd (" + this.goalsScored + "-" + this.goalsAgainst + ")";
			
		return re;
			
	}

	@Override
	public int compareTo(Team other) {
		if (this.totalPoints > other.totalPoints) {
			return -1;
		} else if (other.totalPoints > this.totalPoints) {
			return 1;
		}

		if (this.wins > other.wins) {
			return -1;
		} else if (other.wins > this.wins) {
			return 1;
		}

		if (this.goalDifference > other.goalDifference) {
			return -1;
		} else if (other.goalDifference > this.goalDifference) {
			return 1;
		}

		if (this.goalsScored > other.goalsScored) {
			return -1;
		} else if (other.goalsScored > this.goalsScored) {
			return 1;
		}

		if (this.gamesPlayed < other.gamesPlayed) {
			return -1;
		} else if (other.gamesPlayed < this.gamesPlayed) {
			return 1;
		}	

		return this.name.toLowerCase().compareTo(other.name.toLowerCase());
	}
}
