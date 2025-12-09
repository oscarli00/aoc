package day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Part2 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  private static final int[][] dirs = {
    {1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
  };

  public Part2() throws FileNotFoundException {
    var grid = new ArrayList<List<Character>>();
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      var charList =
          Arrays.stream(line.split(""))
              .map(s -> s.charAt(0))
              .collect(Collectors.toCollection(ArrayList::new));
      grid.add(charList);
    }

    int ans = 0;
    int removed;
    do {
      removed = 0;
      for (int i = 0; i < grid.size(); i++) {
        for (int j = 0; j < grid.get(i).size(); j++) {
          if (!grid.get(i).get(j).equals('@')) {
            continue;
          }
          int rolls = 0;
          for (var d : dirs) {
            int i2 = i + d[0];
            int j2 = j + d[1];
            if (i2 >= 0
                && i2 < grid.size()
                && j2 >= 0
                && j2 < grid.get(i).size()
                && grid.get(i2).get(j2).equals('@')) {
              rolls++;
            }
          }
          if (rolls < 4) {
            removed++;
            grid.get(i).set(j, '.');
          }
        }
      }
      ans += removed;
    } while (removed > 0);

    System.out.println(ans);
  }
}
