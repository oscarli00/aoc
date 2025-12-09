package day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Part2 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  List<Node> nodes;
  List<long[]> lineSegments;

  public Part2() throws FileNotFoundException {
    nodes = new ArrayList<>();
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      var pair = line.split(",");
      nodes.add(new Node(Long.parseLong(pair[0]), Long.parseLong(pair[1])));
    }

    lineSegments = new ArrayList<>();
    for (int i = 0; i < nodes.size() - 1; i++) {
      var curr = nodes.get(i);
      var next = nodes.get(i + 1);
      lineSegments.add(new long[] {curr.col, curr.row, next.col, next.row});
    }
    lineSegments.add(
        new long[] {
          nodes.get(nodes.size() - 1).col,
          nodes.get(nodes.size() - 1).row,
          nodes.get(0).col,
          nodes.get(0).row
        });

    long ans = 0;
    for (int i = 0; i < nodes.size(); i++) {
      System.out.println(i);
      var node1 = nodes.get(i);
      for (int j = i + 1; j < nodes.size(); j++) {
        var node2 = nodes.get(j);
        var minCol = Math.min(node1.col, node2.col);
        var maxCol = Math.max(node1.col, node2.col);
        var minRow = Math.min(node1.row, node2.row);
        var maxRow = Math.max(node1.row, node2.row);
        var area = (maxCol - minCol + 1) * (maxRow - minRow + 1);
        if (area <= ans) {
          continue;
        }
        boolean success = true;
        for (var col = minCol; col <= maxCol; col++) {
          if (!withinBounds(col, minRow) || !withinBounds(col, maxRow)) {
            success = false;
            break;
          }
        }
        if (!success) {
          continue;
        }
        for (var row = minRow; row <= maxRow; row++) {
          if (!withinBounds(minCol, row) || !withinBounds(maxCol, row)) {
            success = false;
            break;
          }
        }
        if (success) {
          System.out.println(minCol + " " + minRow + " " + maxCol + " " + maxRow);
          ans = area;
        }
      }
    }
    System.out.println(ans);
  }

  boolean withinBounds(long col, long row) {
    int intersections = 0;
    // check if the point lies on a line
    for (var line : lineSegments) {
      // rows are the same -> horizontal line, check if point lies on the line
      if (line[1] == line[3]
          && line[1] == row
          && (line[0] <= col && col <= line[2] || line[2] <= col && col <= line[0])) {
        return true;
      }

      // columns are the same -> vertical line
      if (line[0] == line[2]) {
        // point lies on the line
        if (line[0] == col
            && (line[1] <= row && row <= line[3] || line[3] <= row && row <= line[1])) {
          return true;
        }
        // Ray Casting Algorithm
        // - project a ray from the point rightwards -> i.e. extend the col very far
        // - count the number of vertical lines that the ray intersects with
        // - if the number is odd, then the point is within bounds
        // - if the number is even, then the point is out of bounds
        if (line[0] > col && (line[1] < row && row < line[3] || line[3] < row && row < line[1])) {
          intersections++;
        }
      }
    }

    return intersections % 2 == 1;
  }

  private static class Node {
    long col;
    long row;

    public Node(long col, long row) {
      this.col = col;
      this.row = row;
    }
  }
}

// 34318488 too low
