package day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  public Part1() throws FileNotFoundException {
    var scanner = new Scanner(file);
    var intervals = new ArrayList<long[]>();
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      if (line.isEmpty()) {
        break;
      }
      var interval = line.split("-");
      intervals.add(new long[] {Long.parseLong(interval[0]), Long.parseLong(interval[1])});
    }

    int ans = 0;
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      long ingredient = Long.parseLong(line);
      for (var interval : intervals) {
        if (interval[0] <= ingredient && ingredient <= interval[1]) {
          ans++;
          break;
        }
      }
    }
    System.out.println(ans);
  }
}
