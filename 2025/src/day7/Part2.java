package day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Part2 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  public Part2() throws FileNotFoundException {
    var scanner = new Scanner(file);
    var start = scanner.nextLine();
    var pos = new long[start.length()];
    pos[start.indexOf('S')]++;
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      var next = new long[pos.length];
      for (int i = 0; i < line.length(); i++) {
        if (line.charAt(i) == '.') {
          next[i] += pos[i];
        } else if (line.charAt(i) == '^') {
          next[i - 1] += pos[i];
          next[i + 1] += pos[i];
        }
      }
      pos = next;
    }
    long ans = 0;
    for (long n : pos) {
      ans += n;
    }
    System.out.println(ans);
  }
}
