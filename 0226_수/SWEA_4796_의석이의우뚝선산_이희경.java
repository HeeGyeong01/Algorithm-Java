import java.io.IOException;
import java.util.Scanner;

/**
 * 1. 우뚝선 산 구간 수, 우뚝선 산 왼쪽 산 개수, 우뚝 선 산 오른쪽 산 개수를 0으로 초기화 함
 * 2. 첫번째 산 입력받아서 prevHeight의 값으로 초기화.
 * 3. 산 높이를 차례로 입력받음
 *    3-1. 바로 전 산보다 큰 경우
 *        - rightAppend(우뚝 선 산 오른쪽 산 개수)가 0이 아닌 경우 leftAppend와 rightAppend의 값을 0으로 초기화
 *        - leftAppend에 +1 함.
 *    3-2. 바로 전 산보다 작은 경우
 *        - rightAppend +1 하고
 *        - 우뚝 선 산 총 구간수에 현재 leftAppend를 더한다.
 *    3-3. prevHeight을 현재 입력받은 값으로 할당한다.
 * 4. 우뚝 선 산 총 구간수를 출력한다.
 */
public class SWEA_4796_의석이의우뚝선산_이희경 {
  static StringBuilder sb = new StringBuilder();

  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(System.in);
    int T = sc.nextInt();

    for (int tc = 1; tc <= T; tc++) {
      int N = sc.nextInt(); // 산의 개수

      int sectionCount = 0, leftAppend = 0, rightAppend = 0; // 구간 수, 우뚝 선 산 왼쪽, 우뚝 선 산 오른쪽 
      int prevHeight = sc.nextInt(); // 첫번째 산 입력받음.

      // 산 높이를 차례로 입력받음
      for (int idx = 1; idx < N; idx++) {
        int curHeight = sc.nextInt();
        //커지는 구간
        if (prevHeight < curHeight) {
          // 우뚝 선 산 구간 끝난 경우
          if (rightAppend != 0) {
            leftAppend = 0;
            rightAppend = 0;
          }
          leftAppend += 1;

          //작아지는 구간
        } else if (prevHeight > curHeight) {
          rightAppend += 1;
          sectionCount += leftAppend;

        }
        prevHeight = curHeight;

      }
      sb.append('#').append(tc).append(' ').append(sectionCount).append('\n');

    }
    System.out.println(sb);
    sc.close();
  }
}
