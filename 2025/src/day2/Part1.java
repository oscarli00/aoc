package day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Part1 {
  private File file = new File(getClass().getResource("input.txt").getPath());

  public Part1() throws FileNotFoundException {
    long ans = 0;
    var scanner = new Scanner(file);
    var ranges = scanner.nextLine().split(",");
    for (var range : ranges) {
      var split = range.split("-");
      long left = Long.parseLong(split[0]);
      long right = Long.parseLong(split[1]);
      var queue = new LinkedList<Long>();
      for (long i = 1; i <= 9; i++) {
        queue.add(i);
      }
      while (!queue.isEmpty()) {
        var n = queue.poll();
        var s = n.toString();
        long candidate = Long.parseLong(s + s);
        if (candidate >= left && candidate <= right) {
          ans += candidate;
        }
        for (int i = 0; i <= 9; i++) {
          long next = n * 10 + i;
          if (Long.parseLong("" + next + next) <= right) {
            queue.add(next);
          }
        }
      }
    }
    System.out.println(ans);
  }
}
