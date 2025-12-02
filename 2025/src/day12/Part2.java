package day12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Part2 {
  private final File file = new File(getClass().getResource("input.txt").getPath());
  private static final int[][] dirs8 = {
    {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}
  };
  private char[][] map;

  public Part2() throws FileNotFoundException {
    var scanner = new Scanner(file);
    var lineCount = 0;
    while (scanner.hasNextLine()) {
      lineCount++;
      scanner.nextLine();
    }
    scanner.close();

    map = new char[lineCount][];
    scanner = new Scanner(file);
    for (int i = 0; i < map.length; i++) {
      map[i] = scanner.nextLine().toCharArray();
    }
    scanner.close();

    long ans = 0;
    var visited = new boolean[map.length][map[0].length];
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[0].length; j++) {
        if (!visited[i][j]) {
          visited[i][j] = true;
          var res = calculateRegion(i, j, visited);
          ans += res[0] * res[1];
        }
      }
    }
    System.out.println(ans);
  }

  long[] calculateRegion(int i, int j, boolean[][] visited) {
    long area = 1;
    long corners = 0;
    var matches = new boolean[8];
    char c = map[i][j];
    for (int r = 0; r < 8; r++) {
      var d = dirs8[r];
      int i2 = i + d[0];
      int j2 = j + d[1];
      if (i2 < 0 || j2 < 0 || i2 >= map.length || j2 >= map[0].length) {
        continue;
      }
      if (map[i2][j2] == c) {
        matches[r] = true;
        if (r % 2 == 0 && !visited[i2][j2]) {
          visited[i2][j2] = true;
          var res = calculateRegion(i2, j2, visited);
          area += res[0];
          corners += res[1];
        }
      }
    }
    for (int r = 0; r < 8; r += 2) {
      if (!matches[r] && !matches[(r + 2) % 8]
          || matches[r] && !matches[(r + 1) % 8] && matches[(r + 2) % 8]) {
        corners++;
      }
    }
    return new long[] {area, corners};
  }
}
