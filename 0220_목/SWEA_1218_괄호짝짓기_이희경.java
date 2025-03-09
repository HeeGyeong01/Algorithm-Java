import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

/**
 * 1. N개의 괄호가 이어진 문자열을 입력받음.
 * 2. 스택 자료구조를 이용해서 짝이 맞는지 확인함
 * 2-1. 여는 괄호면 push()해서 스택에 넣고
 * 2-2. 닫는 괄호면 스택에서 pop()한 요소과 짝이 맞는지 확인.
 * 3. 스택에 요소가 남아있거나 짝이 맞지 않으면 flag의 값은 flase이다.
 * - flag의 값이 true면 1을, false이면 0을 출력한다.
 */

public class SWEA_1218_괄호짝짓기_이희경 {
  static int length, isValid;
  static Deque<Character> stack;
  static String inputStr;
  static StringTokenizer st;
  static BufferedReader br;
  static StringBuilder sb;

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    sb = new StringBuilder();

    for (int tc = 1; tc <= 10; tc++) {
      isValid = 1; // 초기 값 1(유효)로 설정
      // 문자열 길이, 괄호 문자열 입력받고 스택과 flag 초기화.
      length = Integer.parseInt(br.readLine());
      inputStr = br.readLine();
      stack = new ArrayDeque<>();
      boolean flag = true;

      // 괄호 문자열 순회
      for (int idx = 0; idx < length; idx++) {
        char curBracket = inputStr.charAt(idx);

        if (curBracket == '(' || curBracket == '[' || curBracket == '{' || curBracket == '<') {
          // 여는 기호일 경우 스택에 넣음.
          stack.push(curBracket);
        } else {
          // 닫는 기호일 경우 스택에서 pop()해서 짝이 맞는지 확인.
          if (stack.isEmpty()) {
            // 비어있는 경우 반복문 끝냄.
            flag = false;
            break;
          }
          char top = stack.pop();
          if ((curBracket == ')' && top != '(') ||
              (curBracket == ']' && top != '[') ||
              (curBracket == '}' && top != '{') ||
              (curBracket == '>' && top != '<')) {
            flag = false;
            break;
          }
        }
      }

      if (!stack.isEmpty()) {
        // 스택에 요소가 남아있으면 유효한 문자열이 아님.
        flag = false;
      }

      // 결과 StringBuilder에 더함.
      sb.append("#").append(tc).append(" ").append(flag ? "1" : "0").append("\n");
    }

    System.out.println(sb);
  }
}