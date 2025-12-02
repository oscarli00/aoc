package day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Part2 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  public Part2() throws FileNotFoundException {
    var scanner = new Scanner(file);
    int ans = 0;
    int pos = 50;
    while (scanner.hasNextLine()) {
      var s = scanner.nextLine();
      var dir = s.charAt(0);
      int diff = Integer.parseInt(s.substring(1)) * (dir == 'L' ? -1 : 1);
      int r = 0;
      if (pos + diff >= 100) {
        r = (pos + diff) / 100;
      } else if (pos + diff <= 0) {
        if (pos > 0) {
          r++;
        }
        r += (pos + diff) / -100;
      }
      ans += r;
      pos = Math.floorMod(pos + diff, 100);
    }
    System.out.println(ans);
  }
}
