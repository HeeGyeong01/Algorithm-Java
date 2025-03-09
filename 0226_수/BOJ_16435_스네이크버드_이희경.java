import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 1. 입력받은 과일높이 배열을 오름차순 정렬함
 * 2. 과일높이 배열을 차례로 순회하면서 스네이크버드의 키보다 작거나 같으면 스네이크버드의 키에 +1한다.
 */

public class BOJ_16435_스네이크버드_이희경 {
  static int[] fruitHeight;
  static StringTokenizer st;
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) throws IOException {
    st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken()); // 과일 개수
    int snakeBirdHeight = Integer.parseInt(st.nextToken()); // 스네이크버드 키
    fruitHeight = new int[N];

    // 과일 높이 입력 받음
    st = new StringTokenizer(br.readLine());
    for (int idx = 0; idx < N; idx++) {
      fruitHeight[idx] = Integer.parseInt(st.nextToken());
    }

    // 과일 높이 오름차순 정렬
    Arrays.sort(fruitHeight);

    for (int fruit : fruitHeight) {
      if (fruit <= snakeBirdHeight) {
        snakeBirdHeight += 1;
      }
    }

    System.out.println(snakeBirdHeight);

  }

}
