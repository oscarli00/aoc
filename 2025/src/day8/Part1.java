package day8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());
  int[] parent;

  public Part1() throws FileNotFoundException {
    var boxes = new ArrayList<long[]>();
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      var pos = line.split(",");
      boxes.add(
          new long[] {Long.parseLong(pos[0]), Long.parseLong(pos[1]), Long.parseLong(pos[2])});
    }

    var pq = new PriorityQueue<long[]>(Comparator.comparingLong(a -> a[0]));
    for (int i = 0; i < boxes.size(); i++) {
      long x1 = boxes.get(i)[0];
      long y1 = boxes.get(i)[1];
      long z1 = boxes.get(i)[2];
      for (int j = i + 1; j < boxes.size(); j++) {
        long x2 = boxes.get(j)[0];
        long y2 = boxes.get(j)[1];
        long z2 = boxes.get(j)[2];
        long dist = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) + (z2 - z1) * (z2 - z1);
        pq.add(new long[] {dist, i, j});
      }
    }

    parent = new int[boxes.size()];
    for (int i = 0; i < parent.length; i++) {
      parent[i] = i;
    }

    for (int i = 0; i < 1000; i++) {
      var connection = pq.poll();
      int pi = find((int) connection[1]);
      int pj = find((int) connection[2]);
      union(pi, pj);
    }

    var freq = new int[parent.length];
    for (int j : parent) {
      freq[find(j)]++;
    }

    long ans = 1;
    var circuits = Arrays.stream(freq).boxed().sorted(Comparator.reverseOrder()).limit(3).toList();
    for (var n : circuits) {
      ans *= n;
    }
    System.out.println(ans);
  }

  int find(int i) {
    if (parent[i] == i) {
      return i;
    }
    return parent[i] = find(parent[i]);
  }

  void union(int i, int j) {
    int pi = find(i);
    int pj = find(j);
    if (pi != pj) {
      parent[pi] = pj;
    }
  }
}

