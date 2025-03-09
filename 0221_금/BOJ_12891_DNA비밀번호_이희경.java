import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

/**
 * 주어진 문자열에서 일정 길이 이상의 부분 문자열에서 
 * A, C, G, T의 최소 등장 횟수가 충족되는 부분문자열의 개수를 구하는 문제
 * 
 * 0. [init] 각 문자에 대한 idx를 HashMap에 미리 지정해 놓는다.
 * 1. 주어지는 문자열의 길이와, 부분문자열의 길이, 문자열, 최소등장 횟수를 입력받는다.
 *  2. 맨 처음의 부분 문자열의 각 문자의 등장 횟수를 curChar[]에 저장한다.
 *  2-1. 조건에 만족하는 부분문자열이면 sumPossiblePw +1.sumPossiblePw +1.
 * 3. 문자열 끝까지 순회
 *  3-1. 맨 왼쪽의 문자의 빈도를 -1 하고 오른쪽에 추가되는 문자의 빈도를 +1함.
 *  3-2. 조건에 만족하는 부분 문자열이면 sumPossiblePw +1.
 */

public class BOJ_12891_DNA비밀번호_이희경 {
  static int strLength, subLength, sumPossiblePw = 0;
  static int[] minChar = new int[4], curChar = new int[4];
  static String inputStr;
  static Map<Character, Integer> strToIdx = new HashMap<>();
  static BufferedReader br;
  static StringTokenizer st;

  public static void main(String[] args) throws IOException {
    strToIdx.put('A', 0);
    strToIdx.put('C', 1);
    strToIdx.put('G', 2);
    strToIdx.put('T', 3);

    br = new BufferedReader(new InputStreamReader(System.in));
    st = new StringTokenizer(br.readLine());
    strLength = Integer.parseInt(st.nextToken()); // 문자열의 길이
    subLength = Integer.parseInt(st.nextToken()); // 부분 문자열의 길이
    inputStr = br.readLine();

    st = new StringTokenizer(br.readLine());
    for (int idx = 0; idx < 4; idx++) {
      minChar[idx] = Integer.parseInt(st.nextToken()); // 각 문자의 최소 등장 횟수
    }

    int start = 0, end = 0;
    // 맨 처음의 윈도우에서 char 빈도 계산
    while (end < subLength) {
      curChar[strToIdx.get(inputStr.charAt(end++))] += 1;
    }
    if (isPossiblePw()) {
      sumPossiblePw += 1;
    }

    // 윈도우를 끝까지 순회
    while (end < strLength) {

      // 맨 앞에 char에 해당하는거 빈도수 -1줌
      char removedChar = inputStr.charAt(start++);
      curChar[strToIdx.get(removedChar)] -= 1;

      // 뒤에 문자열 추가하는거에 빈도수 +1 줌.
      char appendChar = inputStr.charAt(end++);
      curChar[strToIdx.get(appendChar)] += 1;

      if (isPossiblePw()) {
        sumPossiblePw += 1;
      }
    }

    System.out.println(sumPossiblePw);
  }

  // 각 문자의 최소 등장 횟수를 만족시키는 부분문자열인지 확인.
  public static boolean isPossiblePw() {
    for (int idx = 0; idx < 4; idx++) {
      if (curChar[idx] < minChar[idx]) {
        return false;
      }
    }
    return true;
  }

}