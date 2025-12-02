package day16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Part2 {
  record Node(int i, int j) {}

  private final File file = new File(getClass().getResource("input.txt").getPath());
  private static final int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

  private char[][] map;
  private int[][][] costs;
  private int minCost = Integer.MAX_VALUE;
  private Set<Node> ans = new HashSet<>();

  public Part2() throws FileNotFoundException {
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

    int startRow = 0;
    int startCol = 0;
    costs = new int[map.length][map[0].length][];
    var queue = new LinkedList<int[]>();
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[0].length; j++) {
        if (map[i][j] == 'S') {
          startRow = i;
          startCol = j;
          queue.add(new int[] {i, j, 1, 0});
          costs[i][j] = new int[] {1000, 0, 1000, 2000};
        } else {
          costs[i][j] =
              new int[] {
                Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE
              };
        }
      }
    }

    while (!queue.isEmpty()) {
      int[] node = queue.poll();
      int i = node[0];
      int j = node[1];
      int d = node[2];
      int cost = node[3];
      if (map[i][j] == 'E') {
        minCost = Math.min(minCost, cost);
        continue;
      }
      for (int r = 0; r < 4; r++) {
        int i2 = i + dirs[r][0];
        int j2 = j + dirs[r][1];
        int rotations = Math.abs(d - r) == 3 ? 1 : Math.abs(d - r);
        int nextCost = cost + 1 + rotations * 1000;
        if (map[i2][j2] != '#' && nextCost <= costs[i2][j2][r]) {
          costs[i2][j2][r] = nextCost;
          queue.add(new int[] {i2, j2, r, nextCost});
        }
      }
    }

    dfs(startRow, startCol, 1);
    for (var a : ans) {
      map[a.i][a.j] = 'O';
    }
    for (var m : map) {
      System.out.println(new String(m));
    }
    System.out.println(ans.size());
  }

  boolean dfs(int i, int j, int d) {
    if (map[i][j] == 'E' && costs[i][j][d] == minCost) {
      ans.add(new Node(i, j));
      return true;
    }
    boolean success = false;
    for (int r = 0; r < 4; r++) {
      var dir = dirs[r];
      int i2 = i + dir[0];
      int j2 = j + dir[1];
      int rotations = Math.abs(d - r) == 3 ? 1 : Math.abs(d - r);
      int nextCost = costs[i][j][d] + 1 + rotations * 1000;
      if (map[i2][j2] != '#' && nextCost == costs[i2][j2][r] && dfs(i2, j2, r)) {
        ans.add(new Node(i, j));
        success = true;
      }
    }
    return success;
  }
}
