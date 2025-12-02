package day19;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Part2 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  public Part2() throws FileNotFoundException {
    long ans = 0;
    var scanner = new Scanner(file);
    var patterns = scanner.nextLine().split(", ");
    scanner.nextLine();
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      var dp = new long[line.length()];
      for (var p : patterns) {
        if (line.startsWith(p)) {
          dp[p.length() - 1] = 1;
        }
      }
      for (int i = 0; i < dp.length; i++) {
        if (dp[i] > 0) {
          for (var p : patterns) {
            if (line.startsWith(p, i + 1)) {
              dp[i + p.length()] += dp[i];
            }
          }
        }
      }
      ans += dp[dp.length - 1];
    }
    System.out.println(ans);
  }
}
