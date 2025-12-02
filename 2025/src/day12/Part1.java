package day12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());
  private static final int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
  private char[][] map;

  public Part1() throws FileNotFoundException {
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
    long perimeter = 0;
    char c = map[i][j];
    for (var d : dirs) {
      int i2 = i + d[0];
      int j2 = j + d[1];
      if (i2 < 0 || j2 < 0 || i2 >= map.length || j2 >= map[0].length) {
        perimeter++;
        continue;
      }
      if (!visited[i2][j2] && map[i2][j2] == c) {
        visited[i2][j2] = true;
        var res = calculateRegion(i2, j2, visited);
        area += res[0];
        perimeter += res[1];
      } else if (map[i2][j2] != c) {
        perimeter++;
      }
    }
    return new long[] {area, perimeter};
  }
}
