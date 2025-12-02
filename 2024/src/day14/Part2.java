package day14;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Part2 {
  private final File file = new File(getClass().getResource("input.txt").getPath());
  private final int width = 101;
  private final int height = 103;

  public Part2() throws FileNotFoundException {
    var scanner = new Scanner(file);
    var input = new Scanner(System.in);

    var pos = new ArrayList<int[]>();
    var vel = new ArrayList<int[]>();
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      pos.add(
          Arrays.stream(line.substring(line.indexOf("p=") + 2, line.indexOf(" ")).split(","))
              .mapToInt(Integer::parseInt)
              .toArray());
      vel.add(
          Arrays.stream(line.substring(line.indexOf("v=") + 2).split(","))
              .mapToInt(Integer::parseInt)
              .toArray());
    }

    int s = 0;
    while (true) {
      var map = new ArrayList<StringBuilder>();
      for (int i = 0; i < height; i++) {
        map.add(new StringBuilder(".".repeat(width)));
      }
      for (var p : pos) {
        map.get(p[1]).setCharAt(p[0], '#');
      }

      var display = false;
      for (int i = 0; i < height - 3; i++) {
        for (int j = 3; j < width - 3; j++) {
          if (map.get(i).charAt(j) == '#'
              && map.get(i + 1).charAt(j - 1) == '#'
              && map.get(i + 1).charAt(j + 1) == '#'
              && map.get(i + 2).charAt(j - 2) == '#'
              && map.get(i + 2).charAt(j + 2) == '#'
              && map.get(i + 3).charAt(j - 3) == '#'
              && map.get(i + 3).charAt(j + 3) == '#') {
            display = true;
          }
        }
      }

      if (display) {
        for (StringBuilder stringBuilder : map) {
          System.out.println(stringBuilder);
        }
        System.out.println("Seconds elapsed: " + s);
        // input.nextLine();
        if (s == 6587) {
          break;
        }
      }

      for (int i = 0; i < pos.size(); i++) {
        var p = pos.get(i);
        var v = vel.get(i);
        p[0] = Math.floorMod(p[0] + v[0], width);
        p[1] = Math.floorMod(p[1] + v[1], height);
      }
      s++;
    }
  }
}
