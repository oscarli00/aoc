package day13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  public Part1() throws FileNotFoundException {
    int ans = 0;

    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      var ax = getX(line);
      var ay = getY(line);
      line = scanner.nextLine();
      var bx = getX(line);
      var by = getY(line);
      line = scanner.nextLine();
      var px = getX(line);
      var py = getY(line);
      if (scanner.hasNextLine()) {
        scanner.nextLine();
      }
      int minCost = Integer.MAX_VALUE;
      for (int a = 0; a <= 100; a++) {
        for (int b = 0; b <= 100; b++) {
          if (a * ax + b * bx == px && a * ay + b * by == py) {
            minCost = Math.min(minCost, a * 3 + b);
          }
        }
      }
      ans += minCost < Integer.MAX_VALUE ? minCost : 0;
    }
    scanner.close();

    System.out.println(ans);
  }

  int getX(String line) {
    return Integer.parseInt(line.substring(line.indexOf("X") + 2, line.indexOf(',')));
  }

  int getY(String line) {
    return Integer.parseInt(line.substring(line.indexOf("Y") + 2));
  }
}
