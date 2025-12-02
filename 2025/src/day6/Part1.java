package day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());
  private final char[][] map = new char[130][];
  private final int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

  public Part1() throws FileNotFoundException {
    int[] start = null;
    int i = 0, j = 0;
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

    i = start[0];
    j = start[1];
    map[i][j] = '.';
    int d = 0;
    int ans = 1;
    var visited = new boolean[map.length][map[0].length];
    visited[i][j] = true;
    while (true) {
      int i2 = i + directions[d][0];
      int j2 = j + directions[d][1];
      if (i2 < 0 || i2 >= map.length || j2 < 0 || j2 >= map[0].length) {
        break;
      }
      if (map[i2][j2] == '.') {
        if (!visited[i2][j2]) {
          ans++;
          visited[i2][j2] = true;
        }
        i = i2;
        j = j2;
      } else {
        d = (d + 1) % directions.length;
      }
    }
    System.out.println(ans);
  }
}
