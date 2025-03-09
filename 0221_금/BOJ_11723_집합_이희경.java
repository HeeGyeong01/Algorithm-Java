import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

/**
 * 1~20까지의 수가 포함될 수 있는 집합에 대해
 * 주어진 6개의 연산을 수행하는 문제.
 * 
 * 0. 주어지는 1~20 사이의 숫자의 집합 포함 여부를 저장할 2진수 sSet 변수를 초기화.
 * 1. 입력으로 주어지는 수행해야 하는 연산 수 만큼 for문 반복.
 *  1-1. 우선 연산자를 입력받고 해당 연산자가 add, remove, check, toggle인 경우 피연산자를 추가로 입력받는다.
 *  1-2. 해당 연산을 비트연산으로 수행하고 난 결과를 다시 sSet변수에 저장.
 *      - check연산의 경우는 추가로 결과를 1과 0으로 출력한다.
 * 
 */
public class BOJ_11723_집합_이희경 {
  static int operand, M, sSet;
  static String operator;
  static StringTokenizer st;
  static BufferedReader br;
  static StringBuilder sb;

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    sb = new StringBuilder();
    M = Integer.parseInt(br.readLine()); // 수행해야 하는 연산의 수
    sSet = 0b0; // 입력 피연산자를 비트연산에서 그대로 쓰기 위해 21크기로 생각한다.

    for (int count = 1; count <= M; count++) {
      st = new StringTokenizer(br.readLine());
      operator = st.nextToken(); // 연산자

      switch (operator) {
        case "add":
          operand = Integer.parseInt(st.nextToken()); // 피연산자 입력받음
          sSet |= 1 << operand; // 1과의 OR 연산으로 해당 위치의 값을 1로 지정.
          break;

        case "remove":
          operand = Integer.parseInt(st.nextToken()); 
          sSet &= ~(1 << operand); // 해당 위치의 값을 0으로 지정.
          break;

        case "check":
          operand = Integer.parseInt(st.nextToken());
          // 해당 위치의 값이 1인 경우 1을, 0인 경우 0 출력.
          sb.append(((sSet >> operand) & 1) == 1 ? 1 : 0).append('\n'); 
          break;

        case "toggle":
          operand = Integer.parseInt(st.nextToken()); 
          // 해당 위치의 값이 0인 경우 1로, 1인 경우 0으로 바꿈.
          sSet ^= (1 << operand);
          break;

        case "all":
          sSet = 0b111111111111111111111; // 1~20이 다 포함되어 있음을 표현
          break;

        case "empty":
          sSet = 0b000000000000000000000;  // 공집합임을 표현
          break;
      }

    }

    System.out.println(sb);
  }

}