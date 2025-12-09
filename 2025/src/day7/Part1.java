package day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  public Part1() throws FileNotFoundException {
    long ans = 0;
    var scanner = new Scanner(file);
    var start = scanner.nextLine();
    var pos = new HashSet<Integer>();
    pos.add(start.indexOf('S'));
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      var next = new HashSet<Integer>();
      for (int i = 0; i < line.length(); i++) {
        if (line.charAt(i) == '.') {
          if (pos.contains(i)) {
            next.add(i);
          }
        } else if (line.charAt(i) == '^') {
          if (pos.contains(i)) {
            next.add(i - 1);
            next.add(i + 1);
            ans++;
          }
        }
      }
      pos = next;
    }
    System.out.println(ans);
  }
}
