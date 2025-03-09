import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

/**
 * 1. nums[]에 8개의 숫자 입력받음.
 * 2. 8개의 일련의 숫자를 1~5 사이클로 감소시키므로 나머지 연산 활용해서
 *    0 이하의 값이 나올때까지 반복.
 *  2-1. 0이하의 수가 나오면 해당 자리+1에서 끝까지 출력
 *       그 다음에 맨 앞에서 해당자리-1까지 출력 한 후 마지막에 0을 출력해준다.
 */
public class SWEA_1225_암호생성기_이희경 {
  static int[] nums = new int[8];
  static StringBuilder sb = new StringBuilder();
  static StringTokenizer st;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    for (int tc = 1; tc <= 10; tc++) {
      br.readLine();
      st = new StringTokenizer(br.readLine());
      // 1. nums[]에 8개의 숫자 입력받음.
      for (int idx = 0; idx < 8; idx++) {
        nums[idx] = Integer.parseInt(st.nextToken());
      }

      sb.append('#').append(tc).append(' ');
      // 2. 8개의 일련의 숫자를 1~5 사이클로 감소시키므로 나머지 연산 활용해서 
      // 0 이하의 결과가 나올때까지 반복.
      for (int idx = 0;; idx++) {
        nums[idx % 8] -= (idx % 5) + 1;
        // 0이하의 수가 나오면 해당 자리 +1에서 끝까지 출력
        // 그 다음에 맨 앞에서 해당자리 앞까지 출력 한 후 마지막에 0을 출력해준다.
        if (nums[idx % 8] <= 0) {
          for (int partition = (idx % 8) + 1; partition < 8; partition++) {
            sb.append(nums[partition]).append(' ');
          }
          for (int partition = 0; partition < (idx % 8); partition++) {
            sb.append(nums[partition]).append(' ');
          }
          sb.append(0);
          break;
        }

      }
      sb.append('\n');

    }
    System.out.println(sb);

  }

}
