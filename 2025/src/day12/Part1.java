package day12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());
  int[] areas = new int[] {5, 7, 7, 7, 6, 7};

  public Part1() throws FileNotFoundException {
    var scanner = new Scanner(file);
    int lowerLimit = 0;
    int upperLimit = 0;
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      if (line.length() < 3 || line.charAt(2) != 'x') {
        continue;
      }
      var sideLengths =
          Arrays.stream(line.substring(0, line.indexOf(":")).split("x"))
              .mapToInt(Integer::parseInt)
              .toArray();
      var totalArea = sideLengths[0] * sideLengths[1];
      var truncatedArea = (sideLengths[0] / 3 * 3) * (sideLengths[1] / 3 * 3);
      var counts =
          Arrays.stream(line.substring(line.indexOf(':') + 2).split(" "))
              .mapToInt(Integer::parseInt)
              .toArray();
      int minArea = 0;
      int maxArea = 0;
      for (int i = 0; i < counts.length; i++) {
        minArea += areas[i] * counts[i];
        maxArea += counts[i] * 9;
      }
      if (minArea <= totalArea) {
        upperLimit++;
      }
      if (maxArea <= truncatedArea) {
        lowerLimit++;
      }
    }
    System.out.println(lowerLimit + " <= ans <= " + upperLimit);
  }
}
