import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 1. m개의 연산들 입력받은 다음 연산자의 값에 따라 0 -> 합집합 연산, 1 -> find 연산
 * 2. x가 속한 집합(대표자)을 찾는 find 메소드
 *    1) 전달받은 파라미터 x의 값과 parent 테이블의 x의 값이 같은 경우 x 리턴.
 *    2) 현재 parent 테이블의 x의 값을 find()의 파라미터로 넣어서 재귀호출 
 *       -> 나온 값을 parent[x]에 저장해가면서 경로 압축 시행
 * 3. a가 속한 집합과 b가 속한 집합을 합치는 union 메소드
 *    1) a가 속한 집합과 b가 속한 집합을 find()로 구한 다음 
 *    2) aRoot와 bRoot 중 더 작은 값을 대표자로 정하여 parent 테이블 수정.
 */
public class SWEA_3289_서로소집합_이희경 {
  static BufferedReader br;
  static StringBuilder sb;
  static StringTokenizer st;
  static int n, m;
  static int[] parent;

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    sb = new StringBuilder();
    int T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      sb.append('#').append(tc).append(' ');

      st = new StringTokenizer(br.readLine());
      n = Integer.parseInt(st.nextToken()); // n 이하의 자연수가 주어짐
      m = Integer.parseInt(st.nextToken()); // 연산의 개수
      parent = new int[n + 1];
      // 자기 자신을 부모로 초기화
      for (int idx = 1; idx <= n; idx++) {
        parent[idx] = idx;
      }

      // 1. 연산들 입력 받음
      for (int line = 0; line < m; line++) {
        st = new StringTokenizer(br.readLine());
        int command = Integer.parseInt(st.nextToken()); // 0 -> 합집합 연산, 1 -> find 연산
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());

        if (command == 0) {
          // 합집합 연산
          union(a, b);
        } else {
          // 같은 집합에 있는지 확인
          sb.append(isSameSet(a, b));
        }
      }

      sb.append('\n');

    }

    // 정답 출력
    System.out.println(sb);

  }

  // 합집합 연산
  private static void union(int a, int b) {
    int aRoot = find(a);
    int bRoot = find(b);
    if (aRoot < bRoot) {
      parent[bRoot] = aRoot;
    } else {
      parent[aRoot] = bRoot;
    }
  }

  // 같은 집합에 있는지 확인하는 연산
  private static int isSameSet(int a, int b) {
    if (find(a) == find(b)) {
      return 1;
    } else {
      return 0;
    }

  }

  // x가 속한 집합 찾기
  private static int find(int x) {
    if (x == parent[x]) {
      return x;
    }
    return parent[x] = find(parent[x]); // 경로 압축
  }

}
