package day22;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());
  private static final long mod = 16777216;

  public Part1() throws FileNotFoundException {
    var nums = new ArrayList<Long>();
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      nums.add(Long.parseLong(scanner.nextLine()));
    }

    long ans = 0;
    for (var n : nums) {
      for (int i = 0; i < 2000; i++) {
        n = ((n << 6) ^ n) % mod;
        n = ((n >> 5) ^ n) % mod;
        n = ((n << 11) ^ n) % mod;
      }
      // System.out.println(n);
      ans += n;
    }
    System.out.println(ans);
  }
}
