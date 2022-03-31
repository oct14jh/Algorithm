import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class SWEA_5653_줄기세포배양 {
	private static class Cell implements Comparable<Cell> {
		int row;
		int col;
		int x;
		int time; 
		int status; // 0-비활성화, 1-활성화, 2-죽음

		public Cell() {
		};

		public Cell(int row, int col, int x, int time, int status) {
			this.row = row;
			this.col = col;
			this.x = x;
			this.time = time;
			this.status = status;
		}

		@Override
		public int compareTo(Cell o) {
			return Integer.compare(o.x, this.x); // 동시처리
		}
	}

	private static int N, M, K, result;
	private static int[][] map;
	private static boolean[][] visited;
	private static PriorityQueue<Cell> pq;

	private static int[] dr = { -1, 1, 0, 0 };
	private static int[] dc = { 0, 0, -1, 1 };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine().trim());

		for (int testCase = 1; testCase <= T; testCase++) {
			result = 0;
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());

			int marginSizeRow = N + K + 10;
			int marginSizeCol = M + K + 10;
			map = new int[marginSizeRow][marginSizeCol];
			visited = new boolean[marginSizeRow][marginSizeCol];

			pq = new PriorityQueue<Cell>();

			int centerIdxRow = marginSizeRow / 2;
			int centerIdxCol = marginSizeCol / 2;


			for (int i = centerIdxRow - (N / 2), stepI=0; stepI < N; ++i, ++stepI) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int j = centerIdxCol - (M / 2), stepJ=0; stepJ < M; ++j, ++stepJ) {
					map[i][j] = Integer.parseInt(st.nextToken());

					if (map[i][j] > 0) {
						pq.offer(new Cell(i, j, map[i][j], 0, 0));
						visited[i][j] = true;
					}
				}
			}
			result = process();
			System.out.println("#" + testCase + " " + result);
		}
	}

	private static int process() {
		ArrayList<Cell> tempList = new ArrayList<>();
		Cell current = null;

		while (K-- > 0) {

			while (!pq.isEmpty()) {
				current = pq.poll();

				// 번식
				if (current.status == 1 && current.time == current.x) {
					for (int move = 0; move < 4; move++) {
						int nr = current.row + dr[move];
						int nc = current.col + dc[move];

						if (visited[nr][nc])
							continue;

							// 동시처리 여다 처리해줘야하나?
						tempList.add(new Cell(nr, nc, current.x, 0, 0));
						visited[nr][nc] = true;
					}
				}

				current.time++;
				if (current.time == current.x || current.time == 2 * current.x) // 0~x~2x
					current.status++; // 0,1,2 비활성화,활성화, 죽음

				if (current.status == 2)
					continue;
				tempList.add(current);
			}
			// pq 세포정보들
			pq.addAll(tempList); // pq
			tempList.clear();
			//K--;
		}

		return pq.size();
	}

}

/**
 * 1. 조건 처리 (시간, 상태, 맵처리 등)
 * 2. 최초 읽을 때 0이 아닌 것들은 피큐에 집어넣는다. (피큐는 작업할 세포)
 * 3. 시간이 한정하는 동안 피큐의 데이터 처리
 * 4. 활성화 상태면서 시간이 딱 x초 지났는 세포에 한해서 번식시작
 * 5. 
 */