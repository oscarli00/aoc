package day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());
  Map<String, List<String>> graph = new HashMap<>();

  public Part1() throws FileNotFoundException {
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      var from = line.substring(0, line.indexOf(':'));
      var to = line.substring(line.indexOf(':') + 2).split(" ");
      var list = new ArrayList<String>();
      for (var s : to) {
        list.add(s);
      }
      graph.put(from, list);
    }
    System.out.println(dfs("you"));
  }

  long dfs(String s) {
    if (s.equals("out")) {
      return 1;
    }
    long res = 0;
    for (var neighbour : graph.get(s)) {
      res += dfs(neighbour);
    }
    return res;
  }
}
