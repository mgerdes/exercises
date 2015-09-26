import java.util.*;
class Node
{
	public Node(int source, int ttl) 
	{
		this.source = source;
		this.ttl = ttl;
	}
	public int source;
	public int ttl;
}

class Main 
{

	static HashMap<Integer, Integer> nodeMap = new HashMap<Integer, Integer>();
	static boolean[][] nodeGraph = new boolean[30][30];
	static boolean[] visited = new boolean[30];

	public static void main(String[] args)
	{
		
		Scanner input = new Scanner(System.in);
		
		int n = input.nextInt();
		
		int caseNum = 1;
		
		
		while (n != 0)
		{			
			setGraphFalse();
					
			int k = 0;
			
			nodeMap.clear();
			
			for (int i = 0; i < n; i++)
			{
				int one = input.nextInt();
				int two = input.nextInt();
				
				if (!nodeMap.containsKey(one))
				{
					nodeMap.put(one, k);	
					k++;
				}
					
				if (!nodeMap.containsKey(two))
				{
					nodeMap.put(two, k);
					k++;
				}
					
				nodeGraph[nodeMap.get(one)][nodeMap.get(two)] = true;	
				nodeGraph[nodeMap.get(two)][nodeMap.get(one)] = true;				
			}
			
			while (true)
			{
				
				setVisitedFalse();
								
				int source = input.nextInt();
				int ttl = input.nextInt();
				if (source == 0 && ttl == 0) break;
								
				System.out.println("Case " + caseNum + ": " + (nodeMap.size() - bfs(nodeMap.get(source), ttl)) + " nodes not reachable from node " + source + " with TTL = " + ttl + ".");
				
				caseNum++;
			
			}
			
			n = input.nextInt();
			
		}
	
	}
	
	static int bfs(int source, int ttl)
	{
		int connected = 1;
		
		LinkedList<Node> q = new LinkedList<Node>();
		
		q.add(new Node(source, 0));
		
		visited[source] = true;
				
		while(!q.isEmpty())
		{
			Node current = q.poll();
			
			if (current.ttl == ttl) 
				continue;
			
			for (int i = 0; i < 30; i++)
			{
				if(nodeGraph[current.source][i] && !visited[i])
				{
					visited[i] = true;
					q.add(new Node(i, current.ttl + 1));
					connected++;
				}
			}
		}
		
		return connected;
	}
	
	static void setVisitedFalse()
	{
		for (int row = 0; row < 30; row++)
		{
			visited[row] = false;
		}
	}
	
	static void setGraphFalse()
	{
		for (int row = 0; row < 30; row++)
		{
			for (int col = 0; col < 30; col++)
			{
				nodeGraph[row][col] = false;
			}
		}
	}

}
