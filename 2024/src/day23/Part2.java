package day23;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Part2 {

  private final File file = new File(getClass().getResource("input.txt").getPath());
  private final Map<String, Set<String>> adj = new HashMap<>();
  private int max = -1;
  private Set<String> ans = null;

  public Part2() throws FileNotFoundException {
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var edge = scanner.nextLine().split("-");
      if (!adj.containsKey(edge[0])) {
        adj.put(edge[0], new HashSet<>());
      }
      adj.get(edge[0]).add(edge[1]);
      if (!adj.containsKey(edge[1])) {
        adj.put(edge[1], new HashSet<>());
      }
      adj.get(edge[1]).add(edge[0]);
    }
    maxClique(new HashSet<>(adj.keySet()), new HashSet<>());
    System.out.println(String.join(",", ans.stream().sorted().toList()));
  }

  // Use old algorithm from https://www.sciencedirect.com/science/article/pii/S0166218X01002906
  void maxClique(Set<String> u, Set<String> clique) {
    if (u.isEmpty()) {
      if (clique.size() > max) {
        max = clique.size();
        ans = new HashSet<>(clique);
      }
      return;
    }
    while (!u.isEmpty()) {
      if (u.size() + clique.size() <= max) {
        return;
      }
      var i = u.iterator().next();
      u.remove(i);
      var intersection = new HashSet<String>();
      for (var neighbour : adj.get(i)) {
        if (u.contains(neighbour)) {
          intersection.add(neighbour);
        }
      }
      clique.add(i);
      maxClique(intersection, clique);
      clique.remove(i);
    }
  }
}
