import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * 1. 마을사람 a, b 입력받은 다음 합집합 연산 실행.
 * 2. a가 속한 집합(대표자)을 찾는 find 메소드
 *    1) 전달받은 파라미터 a의 값과 parent 테이블의 a의 값이 같은 경우 a 리턴.
 *    2) 현재 parent 테이블의 a의 값을 find()의 파라미터로 넣어서 재귀호출 
 *       -> 나온 값을 parent[a]에 저장해가면서 경로 압축 시행
 * 3. a가 속한 집합과 b가 속한 집합을 합치는 union 메소드
 *    1) a가 속한 집합과 b가 속한 집합을 find()로 구한 다음 
 *    2) aRoot와 bRoot 중 더 작은 값을 대표자로 정하여 parent 테이블 수정.
 * 4. 합집합 연산을 다 끝낸 다음 몇개의 무리가 생성되었는지 확인.
 *    1) 1~n을 순회하며 각 사람이 속하는 집합을 HashSet에 추가.
 *    2) HastSet의 사이즈(총 무리의 수) 리턴
 */
public class SWEA_7465_창용마을무리의개수_이희경 {
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
      st = new StringTokenizer(br.readLine());
      n = Integer.parseInt(st.nextToken()); // 마을 사람의 수
      m = Integer.parseInt(st.nextToken()); // 관계의 수
      parent = new int[n + 1];

      // 자기 자신을 부모로 초기화
      for (int idx = 1; idx <= n; idx++) {
        parent[idx] = idx;
      }

      // 1. 관계 정보 입력 받음
      for (int line = 0; line < m; line++) {
        st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());

        // 합집합 연산
        union(a, b);

      }

      // 총 무리 개수 출력
      sb.append('#').append(tc).append(' ').append(countSet()).append('\n');

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

  // x가 속한 집합 찾기
  private static int find(int x) {
    if (x == parent[x]) {
      return x;
    }
    return parent[x] = find(parent[x]); // 경로 압축
  }

  // 몇개의 집합이 생성되었는지 확인
  private static int countSet() {
    HashSet<Integer> sets = new HashSet<>();
    for (int i = 1; i <= n; i++) {
      sets.add(find(i)); // 해당 집합의 대표자를 HashSet에 추가.
    }
    return sets.size(); // 집합 개수 리턴.
  }

}
