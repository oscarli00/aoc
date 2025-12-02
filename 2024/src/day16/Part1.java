package day16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());
  private static final int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

  private char[][] map;
  private int[][][] costs;

  public Part1() throws FileNotFoundException {
    var scanner = new Scanner(file);
    var lists = new ArrayList<char[]>();
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      if (line.isEmpty()) {
        break;
      }
      lists.add(line.toCharArray());
    }
    map = new char[lists.size()][];
    for (int i = 0; i < map.length; i++) {
      map[i] = lists.get(i);
    }
    scanner.close();

    costs = new int[map.length][map[0].length][];
    var queue = new LinkedList<int[]>();
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[0].length; j++) {
        if (map[i][j] == 'S') {
          queue.add(new int[] {i, j, 1, 0});
        }
        costs[i][j] =
            new int[] {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
      }
    }
    int ans = Integer.MAX_VALUE;
    while (!queue.isEmpty()) {
      System.out.println(queue.size());
      int[] node = queue.poll();
      int i = node[0];
      int j = node[1];
      int d = node[2];
      int cost = node[3];
      if (map[i][j] == 'E') {
        ans = Math.min(ans, cost);
        continue;
      }
      for (int r = 0; r < 4; r++) {
        int i2 = i + dirs[r][0];
        int j2 = j + dirs[r][1];
        int rotations = Math.abs(d - r) == 3 ? 1 : Math.abs(d - r);
        int nextCost = cost + 1 + rotations * 1000;
        if (map[i2][j2] != '#' && nextCost < costs[i2][j2][r]) {
          costs[i2][j2][r] = nextCost;
          queue.add(new int[] {i2, j2, r, nextCost});
        }
      }
    }
    System.out.println(ans);
  }
}
