package day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Part2 {
  private final File file = new File(getClass().getResource("input.txt").getPath());
  private final char[][] map = new char[130][];
  private final int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

  public Part2() throws FileNotFoundException {
    int[] start = null;
    int i = 0, j;
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      map[i] = line.toCharArray();
      if (line.indexOf('^') > -1) {
        start = new int[] {i, line.indexOf('^')};
      }
      i++;
    }
    assert start != null;
    map[start[0]][start[1]] = '.';
    int ans = 0;
    for (int row = 0; row < map.length; row++) {
      for (int col = 0; col < map[0].length; col++) {
        if (row == start[0] && col == start[1] || map[row][col] != '.') {
          continue;
        }
        map[row][col] = '#';
        i = start[0];
        j = start[1];
        int d = 0;
        boolean stuck = true;
        int n = 0;
        while (n < map.length * map[0].length) {
          int i2 = i + directions[d][0];
          int j2 = j + directions[d][1];
          if (i2 < 0 || i2 >= map.length || j2 < 0 || j2 >= map[0].length) {
            stuck = false;
            break;
          }
          if (map[i2][j2] == '.') {
            i = i2;
            j = j2;
          } else {
            d = (d + 1) % directions.length;
            n++;
          }
        }
        if (stuck) {
          ans++;
        }
        map[row][col] = '.';
      }
    }
    System.out.println(ans);
  }
}
