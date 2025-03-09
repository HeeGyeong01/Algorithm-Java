import java.util.Scanner;

/**
 * 1. N 이하의 수 중에 5의 배수 중 최댓값에서 -5씩 빼가며 0까지 순회
 * 1-1. (N-(5의 배수)) 값이 3으로 딱 나눠지는 경우 num/5 + ((N - num) / 3)로 봉지 개수 출력
 * 1-2. 1-1에서 for문 탈출한 경우 가능한 봉지 조합이 없다는 의미이므로 -1출력
 */
public class BOJ_2839_설탕배달_이희경 {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int N = sc.nextInt(); // 총 설탕 무게

    // 5kg 가능한 봉지 최대 ~ 0개까지.
    for (int num = (N / 5) * 5; num >= 0; num -= 5) {
      if ((N - num) % 3 == 0) {
        System.out.println(num / 5 + ((N - num) / 3));
        return;
      }
    }

    System.out.println(-1);
    sc.close();
  }
}
