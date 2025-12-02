package day13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Part2 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  public Part2() throws FileNotFoundException {
    long ans = 0;

    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      var ax = getX(line);
      var ay = getY(line);
      line = scanner.nextLine();
      var bx = getX(line);
      var by = getY(line);
      line = scanner.nextLine();
      var px = 10_000_000_000_000L + getX(line);
      var py = 10_000_000_000_000L + getY(line);
      if (scanner.hasNextLine()) {
        scanner.nextLine();
      }
      long a = (px * by - py * bx) / (ax * by - ay * bx);
      if (a < 0) {
        continue;
      }
      long b = (px - a * ax) / bx;
      if (b < 0) {
        continue;
      }
      if (a * ax + b * bx == px && a * ay + b * by == py) {
        ans += a * 3 + b;
      }
    }
    scanner.close();

    System.out.println(ans);
  }

  long getX(String line) {
    return Integer.parseInt(line.substring(line.indexOf("X") + 2, line.indexOf(',')));
  }

  long getY(String line) {
    return Integer.parseInt(line.substring(line.indexOf("Y") + 2));
  }
}
