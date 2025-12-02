package day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  public Part1() throws FileNotFoundException {
    var scanner = new Scanner(file);
    var line = scanner.nextLine();
    long ans = 0;
    int pos = 0;
    int l = 0;
    int r = line.length() + 1;
    int rem = 0;
    for (; l < r - 1; l++) {
      int n = line.charAt(l) - '0';
      if (l % 2 == 0) {
        int id = l / 2;
        while (n-- > 0) {
          ans += (long) id * pos++;
        }
        continue;
      }
      while (n-- > 0) {
        if (rem == 0) {
          r -= 2;
          rem = line.charAt(r) - '0';
        }
        int id = r / 2;
        ans += (long) id * pos++;
        rem--;
      }
    }
    while (rem-- > 0) {
      int id = r / 2;
      ans += (long) id * pos++;
    }
    System.out.println(ans);
  }
}
