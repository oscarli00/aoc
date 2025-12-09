package day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  public Part1() throws FileNotFoundException {
    long ans = 0;
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      int max = 0;
      var jolts = scanner.nextLine().split("");
      int prev = Integer.parseInt(jolts[0]);
      for (int i = 1; i < jolts.length; i++) {
        int curr = Integer.parseInt(jolts[i]);
        max = Math.max(max, prev * 10 + curr);
        prev = Math.max(prev, curr);
      }
      ans += max;
    }
    System.out.println(ans);
  }
}
