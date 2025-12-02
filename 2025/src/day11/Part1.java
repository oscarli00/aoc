package day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());
  private final HashMap<Long, long[]> dp = new HashMap<>();
  private int maxBlinks;

  public Part1() throws FileNotFoundException {
    solve(25);
  }

  public Part1(int blinksRemaining) throws FileNotFoundException {
    solve(blinksRemaining);
  }

  void solve(int blinksRemaining) throws FileNotFoundException {
    maxBlinks = blinksRemaining;

    var scanner = new Scanner(file);
    var line = scanner.nextLine();
    scanner.close();

    long ans = 0;
    var stones = Arrays.stream(line.split(" ")).map(Long::parseLong).toList();
    for (var n : stones) {
      ans += dfs(n, blinksRemaining);
    }
    System.out.println(ans);
  }

  long dfs(long n, int blinksRemaining) {
    if (blinksRemaining == 0) {
      return 1;
    }
    if (dp.containsKey(n) && dp.get(n)[blinksRemaining] > 0) {
      return dp.get(n)[blinksRemaining];
    }
    if (!dp.containsKey(n)) {
      dp.put(n, new long[maxBlinks + 1]);
    }
    long ans;
    if (n == 0) {
      ans = dfs(1, blinksRemaining - 1);
    } else if (String.valueOf(n).length() % 2 == 0) {
      var str = String.valueOf(n);
      var left = Long.parseLong(str.substring(0, str.length() / 2));
      var right = Long.parseLong(str.substring(str.length() / 2));
      ans = dfs(left, blinksRemaining - 1) + dfs(right, blinksRemaining - 1);
    } else {
      ans = dfs(n * 2024, blinksRemaining - 1);
    }
    dp.get(n)[blinksRemaining] = ans;
    return ans;
  }
}
