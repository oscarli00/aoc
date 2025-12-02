package day20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Part2 {
  private final File file = new File(getClass().getResource("input.txt").getPath());
  private static final int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

  private char[][] map;

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

    int startRow = -1;
    int startCol = -1;
    int targetRow = -1;
    int targetCol = -1;
    var queueStart = new LinkedList<int[]>();
    var queueEnd = new LinkedList<int[]>();
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[0].length; j++) {
        if (map[i][j] == 'S') {
          queueStart.add(new int[] {i, j});
          startRow = i;
          startCol = j;
        } else if (map[i][j] == 'E') {
          queueEnd.add(new int[] {i, j});
          targetRow = i;
          targetCol = j;
        }
      }
    }

    int originalTime = 0;
    var visited = new boolean[map.length][map[0].length];
    visited[startRow][startCol] = true;
    var timesFromStart = new int[map.length][map[0].length];
    int timeFromStart = 0;
    while (!queueStart.isEmpty()) {
      int size = queueStart.size();
      for (int k = 0; k < size; k++) {
        var node = queueStart.poll();
        int i = node[0];
        int j = node[1];
        timesFromStart[i][j] = timeFromStart;
        if (map[i][j] == 'E') {
          originalTime = timeFromStart;
        }
        for (var d : dirs) {
          int i2 = i + d[0];
          int j2 = j + d[1];
          if (map[i2][j2] != '#' && !visited[i2][j2]) {
            visited[i2][j2] = true;
            queueStart.add(new int[] {i2, j2});
          }
        }
      }
      timeFromStart++;
    }

    System.out.println("Time without cheats: " + originalTime);

    visited = new boolean[map.length][map[0].length];
    visited[targetRow][targetCol] = true;
    var timesFromFinish = new int[map.length][map[0].length];
    int timeFromFinish = 0;
    while (!queueEnd.isEmpty()) {
      int size = queueEnd.size();
      for (int k = 0; k < size; k++) {
        var node = queueEnd.poll();
        int i = node[0];
        int j = node[1];
        timesFromFinish[i][j] = timeFromFinish;
        for (var d : dirs) {
          int i2 = i + d[0];
          int j2 = j + d[1];
          if (map[i2][j2] != '#' && !visited[i2][j2]) {
            visited[i2][j2] = true;
            queueEnd.add(new int[] {i2, j2});
          }
        }
      }
      timeFromFinish++;
    }

    int ans = 0;
    for (int i = 1; i < map.length - 1; i++) {
      for (int j = 1; j < map[0].length - 1; j++) {
        if (map[i][j] == '#') {
          continue;
        }
        var queue = new LinkedList<int[]>();
        queue.add(new int[] {i, j, 0});
        visited = new boolean[map.length][map[0].length];
        visited[i][j] = true;
        int cheatTime = 0;
        while (cheatTime <= 20) {
          int size = queue.size();
          for (int k = 0; k < size; k++) {
            var node = queue.poll();
            if (map[node[0]][node[1]] != '#') {
              var time = cheatTime + timesFromStart[i][j] + timesFromFinish[node[0]][node[1]];
              if (originalTime - time >= 100) {
                ans++;
              }
            }
            for (var d : dirs) {
              int i2 = node[0] + d[0];
              int j2 = node[1] + d[1];
              if (i2 >= 0 && j2 >= 0 && i2 < map.length && j2 < map.length && !visited[i2][j2]) {
                visited[i2][j2] = true;
                queue.add(new int[] {i2, j2, node[2]});
              }
            }
          }
          cheatTime++;
        }
      }
    }
    System.out.println(ans);
  }
}
