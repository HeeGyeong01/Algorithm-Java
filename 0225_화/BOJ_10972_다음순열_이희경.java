import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

/**
 * 주어진 순열의 사전상 다음 순서의 순열을 찾는 문제.
 * 
 * 1. 다음 순열 알고리즘을 활용
 *   1-1. 교환위치(i-1) 찾기 위해 뒤쪽부터 탐색하여 꼭대기(i) 찾음 
 *        주어진 순열이 가장 큰 순열형태이면 -1 리턴.
 *   1-2. i-1과 교환할 한 단계 큰 수를 뒤쪽부터 찾음
 *   1-3. i-1 자리와 j자리의 값을 교환한다.
 *   1-4. i-1 자리의 한 단계 큰 수로 변화를 줬으니 i 꼭대기 위치 부터 맨 뒤까지 오름차순 정렬함.
 * 
 */
public class BOJ_10972_다음순열_이희경 {
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static StringTokenizer st;
  static int[] numArr;

  public static void main(String[] args) throws IOException {
    int N = Integer.parseInt(br.readLine()); // 순열 길이
    st = new StringTokenizer(br.readLine());
    numArr = new int[N];

    // 초기 순열정보 입력받음
    for (int idx = 0; idx < N; idx++) {
      numArr[idx] = Integer.parseInt(st.nextToken());
    }

    System.out.println(nextPermutation(N, numArr));

  }

  public static StringBuilder nextPermutation(int N, int[] numArr) {
    StringBuilder sb = new StringBuilder();

    // step1 : 뒤쪽부터 탐색하여 꼭대기(i) 찾음 -> 교환위치(i-1) 찾기 위해
    int i = N - 1;
    while (i > 0 && numArr[i - 1] >= numArr[i]) {
      --i;
    }
    // 지금이 가장 큰 순열형태 -> 사전상 다음 순열이 없다.
    if (i == 0) {
      return sb.append("-1");
    }

    // step2 : i-1과 교환할 한 단계 큰 수를 뒤쪽부터 찾음
    int j = N - 1;
    while (numArr[i - 1] >= numArr[j]) {
      --j;
    }

    // step3 : i-1 자리와 j값 교환
    swap(i - 1, j);

    // step4 : i-1 자리의 한 단계 큰 수로 변화를 줬으니 i 꼭대기 위치 부터 맨 뒤까지 오름차순 정렬함.
    int k = N - 1;
    while (i < k) {
      swap(i++, k--);
    }

    for (int num : numArr) {
      sb.append(num).append(' ');
    }
    return sb;
  }

  public static void swap(int leftIdx, int rightIdx) {
    int temp = numArr[rightIdx];
    numArr[rightIdx] = numArr[leftIdx];
    numArr[leftIdx] = temp;
  }
}
