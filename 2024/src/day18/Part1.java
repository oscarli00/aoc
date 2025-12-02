package day18;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());
  private static final int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

  private boolean[][] corrupted = new boolean[71][71];

  public Part1() throws FileNotFoundException {
    var scanner = new Scanner(file);
    for (int i = 0; i < 1024; i++) {
      var line = scanner.nextLine();
      var pos = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
      corrupted[pos[1]][pos[0]] = true;
    }
    //        for (var cor : corrupted) {
    //            System.out.println(Arrays.toString(cor));
    //        }
    var visited = new boolean[corrupted.length][corrupted[0].length];
    var queue = new LinkedList<int[]>();
    queue.add(new int[] {0, 0});
    visited[0][0] = true;
    int steps = 0;
    while (!queue.isEmpty()) {
      int size = queue.size();
      for (int i = 0; i < size; i++) {
        var node = queue.poll();
        // System.out.println(Arrays.toString(node));
        if (node[0] == corrupted.length - 1 && node[1] == corrupted[0].length - 1) {
          System.out.println(steps);
          return;
        }
        for (var d : dirs) {
          int i2 = node[0] + d[0];
          int j2 = node[1] + d[1];
          if (i2 >= 0
              && j2 >= 0
              && i2 < corrupted.length
              && j2 < corrupted[0].length
              && !visited[i2][j2]
              && !corrupted[i2][j2]) {
            visited[i2][j2] = true;
            queue.add(new int[] {i2, j2});
          }
        }
      }
      steps++;
    }
  }
}
