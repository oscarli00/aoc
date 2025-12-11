package day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Part2 {
  private final File file = new File(getClass().getResource("input.txt").getPath());
  Map<String, List<String>> graph = new HashMap<>();
  Map<String, Long[]> cache = new HashMap<>();

  public Part2() throws FileNotFoundException {
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      var from = line.substring(0, line.indexOf(':'));
      var to = line.substring(line.indexOf(':') + 2).split(" ");
      var list = new ArrayList<>(Arrays.asList(to));
      graph.put(from, list);
    }
    for (var entry : graph.entrySet()) {
      System.out.println(entry.getKey() + ": " + entry.getValue());
    }
    System.out.println(dfs("svr", 0));
  }

  long dfs(String s, int mask) {
    if (cache.containsKey(s) && cache.get(s)[mask] != null) {
      return cache.get(s)[mask];
    }
    if (s.equals("out")) {
      return mask == 3 ? 1 : 0;
    }
    long res = 0;
    for (var neighbour : graph.get(s)) {
      int nextMask = mask;
      if (neighbour.equals("dac")) {
        nextMask += 1;
      } else if (neighbour.equals("fft")) {
        nextMask += 2;
      }
      res += dfs(neighbour, nextMask);
    }
    if (!cache.containsKey(s)) {
      cache.put(s, new Long[4]);
    }
    return cache.get(s)[mask] = res;
  }
}
