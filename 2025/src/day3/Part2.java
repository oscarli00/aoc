package day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Part2 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  public Part2() throws FileNotFoundException {
    long ans = 0;
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      long max = 0;
      var jolts = scanner.nextLine();
      var subsequences = new ArrayList<String>();
      subsequences.add("");
      var visited = new long[13];
      for (int i = 0; i < jolts.length(); i++) {
        char c = jolts.charAt(i);
        var next = new ArrayList<String>();
        for (var s : subsequences) {
          next.add(s);
          for (int j = 0; j <= s.length(); j++) {
            if (j == s.length() || c > s.charAt(j)) {
              var candidate = s.substring(0, j) + c;
              if (candidate.length() > 12) {
                continue;
              }
              var candidateVal = Long.parseLong(candidate);
              if (candidateVal > visited[candidate.length()]) {
                if (candidate.length() == 12) {
                  max = Math.max(max, candidateVal);
                }
                next.add(candidate);
                visited[candidate.length()] = candidateVal;
              }
            }
          }
        }
        subsequences = next;
      }
      ans += max;
    }
    System.out.println(ans);
  }
}
