import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

/**
 * 1. 주어지는 알파벳들을 char[]에 저장한 수 사전순으로 정렬함.
 * 2. 비트연산 위해 모음의 인덱스에 1의 값을 넣어서 isVowel 초기화
 * 3. 가능한 부분 집합 찾는 메소드 호출
 *    3-1. 선택된 문자의 개수가 암호의 길이와 같고, 모음의 개수가 1이상, 자음의 개수가 2이상인 경우 -> StringBuilder에 추가해서 출력.
 *    3-2. 모든 요소를 다 고려했을 경우 재귀 호출 종료.
 *    3-3. 현재 고려요소 + 1, 현재 요소 선택한 다음 재귀 호출
 *    3-4. 고려요소에 +1 해주고 재귀 호출.
 */
public class BOJ_1759_암호만들기_이희경 {
  static StringBuilder sb = new StringBuilder();
  static StringTokenizer st;
  static char chars[];
  static int isVowel, L, C;

  public static void main(String[] args) throws IOException {

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    st = new StringTokenizer(br.readLine());
    L = Integer.parseInt(st.nextToken()); // 암호의 길이
    C = Integer.parseInt(st.nextToken()); // 가능성 있는 문자의 개수
    chars = new char[C];

    // 1. 주어지는 알파벳을 입력받음.
    st = new StringTokenizer(br.readLine());
    for (int idx = 0; idx < C; idx++) {
      chars[idx] = st.nextToken().charAt(0);
    }
    Arrays.sort(chars); // 사전순으로 정렬.

    // 2. 비트연산 위해 모음의 인덱스에 1의 값을 넣은 isVowel 초기화
    for (int idx = 0; idx < C; idx++) {
      int c = chars[idx];
      if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
        isVowel |= (1 << idx);
      }
    }

    // 3. 가능한 부분 집합 찾는 메소드 호출
    makeSubset(0, 0);

    System.out.println(sb);

  }

  // 3. 가능한 부분 집합 찾기. (현재 고려중인 요소의 순서, 선택된 문자들)
  public static void makeSubset(int count, int selectedInfo) {

    int selectedCharCount = Integer.bitCount(selectedInfo); // 선택된 문자 개수
    int vowelCount = Integer.bitCount(selectedInfo & isVowel); // 선택된 문자 중 모음의 수

    // 선택된 문자의 개수가 암호의 길이와 같은 경우
    if (selectedCharCount == L) {
      // 모음의 개수가 1이상, 자음의 개수가 2이상인 경우 -> StringBuilder에 추가
      if (vowelCount >= 1 && (selectedCharCount - vowelCount) >= 2) {
        for (int idx = 0; idx < C; idx++) {
          if ((selectedInfo & (1 << idx)) != 0) {
            sb.append(chars[idx]);
          }
        }
        sb.append('\n');
      }

      return;
    }

    // 모든 요소를 다 고려했을 경우 재귀 호출 종료.
    if (count == C) {
      return;
    }

    // 현재 고려요소 + 1, 현재 요소 선택한 다음 재귀 호출
    makeSubset(count + 1, selectedInfo | (1 << count));
    // 고려요소에 +1 해주고 재귀 호출.
    makeSubset(count + 1, selectedInfo);

  }
}
