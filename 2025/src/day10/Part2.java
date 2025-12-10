package day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Part2 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  private static List<Integer> target;
  private static int targetTotal = 0;
  private static int longestButtonLength = 0;
  private static int presses = Integer.MAX_VALUE;

  public Part2() throws FileNotFoundException {
    long ans = 0;
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      System.out.println(line);
      var targetStr = line.substring(line.indexOf('{') + 1, line.indexOf('}')).split(",");
      target = new ArrayList<>();
      targetTotal = 0;
      for (var s : targetStr) {
        int n = Integer.parseInt(s);
        target.add(n);
        targetTotal += n;
      }

      var buttonsString = line.substring(line.indexOf(']') + 2, line.indexOf('{') - 1).split(" ");
      var buttons = new ArrayList<List<Integer>>();
      for (var s : buttonsString) {
        var lights = s.substring(1, s.length() - 1).split(",");
        var list = new ArrayList<Integer>();
        for (var light : lights) {
          list.add(Integer.parseInt(light));
        }
        buttons.add(list);
        longestButtonLength = Math.max(longestButtonLength, lights.length);
      }

      var start = new ArrayList<Integer>();
      for (int i = 0; i < target.size(); i++) {
        start.add(0);
      }

      var queue = new PriorityQueue<Node>(Comparator.comparingInt(a -> a.f));
      queue.add(new Node(start, 0));

      presses = Integer.MAX_VALUE;
      //   var visited = new HashSet<List<Integer>>();
      while (!queue.isEmpty()) {
        var curr = queue.poll();
        if (curr.f >= presses) {
          break;
        }
        for (var button : buttons) {
          var next = new ArrayList<>(curr.val);
          for (var light : button) {
            next.set(light, next.get(light) + 1);
          }
          //          if (visited.contains(next)) {
          //            continue;
          //          }
          //          visited.add(next);
          boolean invalid = false;
          for (int j = 0; j < next.size(); j++) {
            if (next.get(j) > target.get(j)) {
              invalid = true;
              break;
            }
          }
          if (invalid) {
            continue;
          }
          if (next.equals(target)) {
            presses = Math.min(presses, curr.g + 1);
            break;
          }
          queue.add(new Node(next, curr.g + 1));
        }
      }
      ans += presses;
    }
    System.out.println(ans);
  }

  private static class Node {
    List<Integer> val;
    int g; // g = current cost
    int f; // f = g + heuristic

    Node(List<Integer> val, int g) {
      this.val = val;
      this.g = g;
      int rem = 0;
      for (int i = 0; i < val.size(); i++) {
        rem = Math.max(rem, target.get(i) - val.get(i));
      }
      f = g + rem;
    }
  }
}
