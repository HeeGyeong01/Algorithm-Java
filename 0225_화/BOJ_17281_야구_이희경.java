import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

/**
 * 야구 룰에 따라 야구 점수를 계산하는 문제.
 * 
 * 1. 이닝에 따른 타자의 결과 입력받음.
 * 2. 2~9번 8명의 선수로 가능한 순열 찾기.
 *    2-1. 기저조건: 8명의 선수로 순열 완성하면 return
 *    2-2. 2~9번 선수 순회 -> 순열에 배치한 선수가 아니면 4번째 자리는 띄우고 순열에 배치.
 *         방문처리 -> 파라미터값 +1해서 재귀호출 -> 방문처리 취소
 * 3. 기저조건 만족할 때마다 해당 순열에 따른 점수를 계산해서 maxScore 업데이트 함.
 * 4. maxScore 출력.
 */
public class BOJ_17281_야구_이희경 {
  static int N, maxScore;
  static int[][] playerHitResult;
  static StringBuilder sb = new StringBuilder();
  static StringTokenizer st;
  static boolean[] visited = new boolean[9];
  static int[] playerOrder; // 플레이어 순서

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    N = Integer.parseInt(br.readLine()); // 이닝 수
    playerHitResult = new int[9][N]; // [선수 행][이닝 열] = hit결과
    playerOrder = new int[9];
    maxScore = Integer.MIN_VALUE;

    // 1. playerHitResult 정보 입력받음.
    for (int inning = 0; inning < N; inning++) {
      st = new StringTokenizer(br.readLine());
      for (int player = 0; player < 9; player++) {
        playerHitResult[player][inning] = Integer.parseInt(st.nextToken());
      }
    }

    // 2. 모든 순열 찾기.
    makePermutation(0);

    // 3. 최대 점수 출력
    System.out.println(maxScore);

  }

  // 2️. 2~9번 8명의 선수로 가능한 순열 찾기.
  public static void makePermutation(int playerCount) {
    // 8명의 선수 다 순열에 배열하면 return
    if (playerCount == 8) {
      maxScore = Math.max(maxScore, calcScore());
      return;
    }
    // 2~9번 선수 배치
    for (int idx = 1; idx < 9; idx++) {
      if (visited[idx] == false) {
        int realOrder = playerCount >= 3 ? playerCount + 1 : playerCount; // 4번째 자리는 띄우고 채운다.
        playerOrder[realOrder] = idx;
        visited[idx] = true;
        makePermutation(playerCount + 1);
        visited[idx] = false;
      }

    }

  }

  // 3. 플레이어 순서를 바탕으로 각 이닝의 점수 계산하기
  public static int calcScore() {
    playerOrder[3] = 0; // 1번 선수는 4번 타자로 고정.
    int bitmask = 0, outCount = 0, totalScore = 0, nextPlayer = 0;

    // 1~N 이닝 순회
    for (int inning = 0; inning < N; inning++) {
      for (int playCount = nextPlayer;; playCount++) {
        // 해당 이닝에서 현재 순서 타자의 결과 0~4
        int hitResult = playerHitResult[playerOrder[playCount % 9]][inning];
        // 3out 된 경우
        if (hitResult == 0 && ++outCount == 3) {
          outCount = 0;
          nextPlayer = (playCount + 1) % 9;
          bitmask = 0;
          break;
        } else {
          // 안타=1 ~ 홈런4 인 경우
          bitmask = (bitmask << hitResult) | (1 << (hitResult));
          // 비트마스크로 홈에 도착하는 선수 몇명인지 계산.
          totalScore += Integer.bitCount(bitmask & (0b1111 << 4));
          bitmask = bitmask & ~(0b1111 << 4); // 다음 계산을 위해 비트마스크 4루 넘어가는 부분 초기화함.

        }

      }
    }
    return totalScore;
  }

}