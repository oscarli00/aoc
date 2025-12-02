package day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  public Part1() throws FileNotFoundException {
    var scanner = new Scanner(file);
    int ans = 0;
    int pos = 50;
    while (scanner.hasNextLine()) {
      var s = scanner.nextLine();
      var dir = s.charAt(0);
      int diff = Integer.parseInt(s.substring(1)) * (dir == 'L' ? -1 : 1);
      pos = Math.floorMod(pos + diff, 100);
      if (pos == 0) {
        ans++;
      }
    }
    System.out.println(ans);
  }
}
