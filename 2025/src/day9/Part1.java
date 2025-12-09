package day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  public Part1() throws FileNotFoundException {
    var coords = new ArrayList<long[]>();
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      var pair = line.split(",");
      coords.add(new long[] {Long.parseLong(pair[0]), Long.parseLong(pair[1])});
    }

    long ans = 0;
    for (int i = 0; i < coords.size(); i++) {
      long x1 = coords.get(i)[0];
      long y1 = coords.get(i)[1];
      for (int j = i + 1; j < coords.size(); j++) {
        long x2 = coords.get(j)[0];
        long y2 = coords.get(j)[1];
        ans = Math.max(ans, (Math.abs(x2 - x1) + 1) * (Math.abs(y2 - y1) + 1));
      }
    }
    System.out.println(ans);
  }
}
