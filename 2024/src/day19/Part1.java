package day19;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  public Part1() throws FileNotFoundException {
    int ans = 0;
    var scanner = new Scanner(file);
    var patterns = scanner.nextLine().split(", ");
    scanner.nextLine();
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      var dp = new boolean[line.length()];
      for (var p : patterns) {
        if (line.startsWith(p)) {
          dp[p.length() - 1] = true;
        }
      }
      for (int i = 0; i < dp.length; i++) {
        if (dp[i]) {
          for (var p : patterns) {
            if (line.startsWith(p, i + 1)) {
              dp[i + p.length()] = true;
            }
          }
        }
      }
      if (dp[dp.length - 1]) {
        ans++;
      }
    }
    System.out.println(ans);
  }
}
