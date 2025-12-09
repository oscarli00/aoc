package day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Part2 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  public Part2() throws FileNotFoundException {
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
    intervals.sort(Comparator.comparing(a -> a[0]));

    var mergedIntervals = new ArrayList<long[]>();
    for (var interval : intervals) {
      var n = mergedIntervals.size();
      if (n > 0 && interval[0] <= mergedIntervals.get(n - 1)[1]) {
        mergedIntervals.get(n - 1)[1] = Math.max(mergedIntervals.get(n - 1)[1], interval[1]);
      } else {
        mergedIntervals.add(interval);
      }
    }

    long ans = 0;
    for (var interval : mergedIntervals) {
      ans += interval[1] - interval[0] + 1;
    }
    System.out.println(ans);
  }
}
