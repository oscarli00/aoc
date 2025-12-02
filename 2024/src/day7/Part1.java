package day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  public Part1() throws FileNotFoundException {
    long ans = 0;
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine().split(": ");
      var test = Long.parseLong(line[0]);
      var nums = Arrays.stream(line[1].split(" ")).map(Integer::parseInt).toList();
      if (valid(test, nums, nums.get(0), 1)) {
        ans += test;
      }
    }
    System.out.println(ans);
  }

  boolean valid(long target, List<Integer> nums, long curr, int i) {
    if (i == nums.size()) {
      return curr == target;
    }
    return valid(target, nums, curr + nums.get(i), i + 1)
        || valid(target, nums, curr * nums.get(i), i + 1);
  }
}
