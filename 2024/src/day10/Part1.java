package day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());
  private static final int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
  private int[][] map;

  public Part1() throws FileNotFoundException {
    var scanner = new Scanner(file);
    var lineCount = 0;
    while (scanner.hasNextLine()) {
      lineCount++;
      scanner.nextLine();
    }
    scanner.close();

    map = new int[lineCount][];
    scanner = new Scanner(file);
    for (int i = 0; i < map.length; i++) {
      map[i] = Arrays.stream(scanner.nextLine().split("")).mapToInt(Integer::parseInt).toArray();
    }
    scanner.close();

    int ans = 0;
    var queue = new LinkedList<int[]>();
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[0].length; j++) {
        if (map[i][j] != 0) {
          continue;
        }
        queue.add(new int[] {i, j});
        var visited = new boolean[map.length][map[0].length];
        while (!queue.isEmpty()) {
          int[] node = queue.poll();
          if (visited[node[0]][node[1]]) {
            continue;
          }
          visited[node[0]][node[1]] = true;
          int val = map[node[0]][node[1]];
          if (val == 9) {
            ans++;
            continue;
          }
          for (var d : dirs) {
            int i2 = node[0] + d[0];
            int j2 = node[1] + d[1];
            if (i2 >= 0
                && j2 >= 0
                && i2 < map.length
                && j2 < map[0].length
                && map[i2][j2] == val + 1) {
              queue.add(new int[] {i2, j2});
            }
          }
        }
      }
    }
    System.out.println(ans);
  }
}
