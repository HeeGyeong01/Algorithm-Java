import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 주어지는 회의 시작-종료시간을 보고 정해진
 * 겹치지 않는 시간 내에서 배정받을 수 있는 회의의 최대 개수를 알아내는 문제.
 * 
 * 1. Meeting 클래스를 선언하여 compareTo()를 오버라이딩해서
 *    종료시간이 빠른순, 종료시간이 같다면 시작시간이 빠른순으로 오름차순 정렬할 수 있도록 함.
 * 2. Meeting클래스 배열에 입력받은 시작, 종료시간을 저장함.
 * 3. compareTo()에 선언한 기준대로 오름차순 정렬함.
 * 4. 가장 빨리 끝나는 회의를 첫번째회의로 배정함.
 * 5. Meeting클래스 배열을 순회하면서 
 *    마지막으로 배정받은 회의의 종료시간이 현재 고려중인 회의의 시작시간보다 작거나 같은 경우
 *    해당 회의를 배정한다.
 * 6. 배정한 회의들의 개수를 출력한다.
 */
public class BOJ_1931_회의실배정_이희경 {
  static Meeting[] meetings;
  static int N, maxMeeting;
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));;
  static StringTokenizer st;
  static List<Meeting> schedule = new ArrayList<>();

  static class Meeting implements Comparable<Meeting> {
    int start, end;

    public Meeting(int start, int end) {
      this.start = start;
      this.end = end;
    }

    // 종료시간이 빠른순, 종료시간이 같다면 시작시간이 빠른순 [오름차순]
    @Override
    public int compareTo(Meeting o) {
      return this.end != o.end ? this.end - o.end : this.start - o.start;
    }
  }

  public static void main(String[] args) throws IOException {
    N = Integer.parseInt(br.readLine());
    meetings = new Meeting[N];

    // 미팅 시작, 종료 시각 입력받음.
    for (int idx = 0; idx < N; idx++) {
      st = new StringTokenizer(br.readLine());
      meetings[idx] = new Meeting(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
    }

    Arrays.sort(meetings); // 오름차순 정렬
    schedule.add(meetings[0]); // 가장 빨리 끝나는 회의를 첫번째회의로 배정함.

    for (int idx = 1; idx < N; idx++) {
      // 마지막으로 배정받은 회의의 종료시간이 현재 고려중인 회의의 시작시간보다 작거나 같은 경우
      if (schedule.get(schedule.size() - 1).end <= meetings[idx].start) {
        // 현재 고려중인 회의를 배정한다.
        schedule.add(meetings[idx]);
      }
    }

    System.out.println(schedule.size());
  }

}
