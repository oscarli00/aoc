package day14;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());
  private final int width = 101;
  private final int height = 103;

  public Part1() throws FileNotFoundException {
    var quadrants = new int[4];
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      var p =
          Arrays.stream(line.substring(line.indexOf("p=") + 2, line.indexOf(" ")).split(","))
              .mapToInt(Integer::parseInt)
              .toArray();

      var v =
          Arrays.stream(line.substring(line.indexOf("v=") + 2).split(","))
              .mapToInt(Integer::parseInt)
              .toArray();

      var finalX = Math.floorMod(p[0] + 100 * v[0], width);
      var finalY = Math.floorMod(p[1] + 100 * v[1], height);
      if (finalX == width / 2 || finalY == height / 2) {
        continue;
      }
      var idx = 2 * (finalY > height / 2 ? 1 : 0) + (finalX > width / 2 ? 1 : 0);
      quadrants[idx]++;
    }
    scanner.close();
    System.out.println(quadrants[0] * quadrants[1] * quadrants[2] * quadrants[3]);
  }
}
