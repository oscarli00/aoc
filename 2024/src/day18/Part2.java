package day18;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Part2 {
  record Node(int i, int j) {}

  private final File file = new File(getClass().getResource("input.txt").getPath());
  private static final int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

  private boolean[][] corrupted = new boolean[71][71];
  private Node[][] parent = new Node[71][71];

  public Part2() throws FileNotFoundException {
    var reversedCoords = new LinkedList<int[]>();
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      var pos = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
      reversedCoords.addFirst(new int[] {pos[1], pos[0]});
      corrupted[pos[1]][pos[0]] = true;
    }
    for (int i = 0; i < corrupted.length; i++) {
      for (int j = 0; j < corrupted[0].length; j++) {
        parent[i][j] = new Node(i, j);
      }
    }
    for (int i = 0; i < corrupted.length; i++) {
      for (int j = 0; j < corrupted[0].length; j++) {
        if (corrupted[i][j]) {
          continue;
        }
        for (var d : dirs) {
          int i2 = i + d[0];
          int j2 = j + d[1];
          if (i2 >= 0
              && j2 >= 0
              && i2 < corrupted.length
              && j2 < corrupted[0].length
              && !corrupted[i2][j2]) {
            union(parent[i][j], parent[i2][j2]);
          }
        }
      }
    }

    assert !find(parent[0][0]).equals(find(parent[parent.length - 1][parent.length - 1]));
    int line = reversedCoords.size();
    for (var coord : reversedCoords) {
      int i = coord[0];
      int j = coord[1];
      corrupted[i][j] = false;
      for (var d : dirs) {
        int i2 = i + d[0];
        int j2 = j + d[1];
        if (i2 >= 0
            && j2 >= 0
            && i2 < corrupted.length
            && j2 < corrupted[0].length
            && !corrupted[i2][j2]) {
          union(parent[i][j], parent[i2][j2]);
        }
      }
      // System.out.println(find(parent[0][0]) + " " + find(parent[parent.length - 1][parent.length
      // - 1]));
      if (find(parent[0][0]).equals(find(parent[parent.length - 1][parent.length - 1]))) {
        System.out.println(line);
        System.out.println(j + "," + i);
        break;
      }
      line--;
    }
  }

  Node find(Node n) {
    if (n.equals(parent[n.i][n.j])) {
      return n;
    }
    return parent[n.i][n.j] = find(parent[n.i][n.j]);
  }

  void union(Node a, Node b) {
    a = find(a);
    b = find(b);
    if (!a.equals(b)) {
      parent[b.i][b.j] = a;
    }
  }
}
