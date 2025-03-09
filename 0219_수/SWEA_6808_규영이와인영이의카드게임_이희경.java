import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

/**
 * 인영이가 내는 카드에 따라 규영이가 게임에서 이기는 경우의 수를 구하는 문제.
 * 
 * 이하 규영 = a, 인영 = b
 * 0. [init] b가 카드를 내는 경우의 수를 9! = 362,880를 저장해놓음.
 * 1. 1~18까지 순회하며 카드 배열에 없는 수를 bCard[]에 추가함.
 * 2. playGame() 재귀호출
 *  2-1. 종료조건: b카드에 대한 순열(길이 9)이 다 만들어진 경우. a의 총점이 더 큰 경우 winCount에 +1한다.
 *  2-2. idx를 이용해 bCard[]를 순회하면서 아직 낸 적이 없는 카드인 경우
 *      현재 Round의 a카드와 bCard[idx]로 방금 순열에 추가된 b카드를
 *      비교해서 더 높은 수의 카드를 가지고 있는 사람에게 카드의 합을 더해준다.
 *  2-3. playGame() 에 기존 매개변수에 +1해서 호출.
 *  2-4. 현재 판에서 이긴사람의 총점에서 현재 얻은 점수를 빼고 방문처리도 취소함.
 * 3. winCount 와 allCases - winCount를 출력한다.
 */

public class SWEA_6808_규영이와인영이의카드게임_이희경 {
  static int[] aCard; // 입력값으로 받은 규영이가 낼 카드.
  static int[] bCard;
  static boolean[] visited;
  static int winCount;
  static final int allCases = 362_880;
  static int aScore;
  static int bScore;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st;
    StringBuilder sb = new StringBuilder();

    int T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      // 각 테스트케이스에 필요한 변수들 초기화함.
      aCard = new int[9];
      bCard = new int[9];
      visited = new boolean[9];
      winCount = 0;
      aScore = 0;
      bScore = 0;
      st = new StringTokenizer(br.readLine());

      // a가 게임에서 내는 카드 정보 입력받음.
      for (int idx = 0; idx < 9; idx++) {
        aCard[idx] = Integer.parseInt(st.nextToken());
      }
      /**
       * 1️⃣ b가 게임에서 내는 카드 배열 생성.
       * 1~18까지 순회하며 a카드 배열에 없는 수를 bCard[]에 추가함.
       */
      int bIdx = 0;
      for (int num = 1; num <= 18; num++) {
        boolean isExist = false;
        for (int idx = 0; idx < 9; idx++) {
          if (aCard[idx] == num) {
            isExist = true;
            break;
          }
        }
        if (isExist == false) {
          bCard[bIdx++] = num;
        }
      }

      // 2️⃣ 재귀함수 Start
      playGame(0);

      // 3️⃣ 이기는 경우의 수와 지는 경우의 수 출력.
      sb.append('#').append(tc).append(' ').append(winCount).append(' ').append(allCases - winCount).append('\n');

    }

    System.out.println(sb);
  }

  public static void playGame(int length) {
    // 종료조건: b카드에 대한 순열 다 만들어진 경우
    if (length == 9) {
      if (aScore > bScore) {
        winCount += 1;
      }
      return;
    }
    /**
     * idx를 이용해 bCard[]를 순회하면서
     * visited[idx]가 false인 경우
     * aCard[length]로 현재 Round의 a카드와 bCard[idx]로 방금 순열에 추가된 b카드를
     * 비교해서 더 높은 수의 카드를 가지고 있는 사람에게 카드의 합을 더해준다.
     * 
     * 그 후에 length+1을 파라미터로 하여 재귀 호출을 실행하고
     * 
     * 이긴 사람의 총점에서 현재 Round에서 얻은 점수를 빼준다.
     */
    for (int idx = 0; idx < 9; idx++) {
      if (visited[idx] == false) {
        int scoreSum = aCard[length] + bCard[idx];
        boolean aWin = aCard[length] > bCard[idx];

        if (aWin) {
          aScore += scoreSum;
        } else {
          bScore += scoreSum;
        }

        visited[idx] = true;
        playGame(length + 1);

        if (aWin) {
          aScore -= scoreSum;
        } else {
          bScore -= scoreSum;
        }

        visited[idx] = false;

      }
    }

  }

}