package day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Part1 {
  private File file = new File(getClass().getResource("input.txt").getPath());

  public Part1() throws FileNotFoundException {
    int ans = 0;
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      var nums = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
      boolean safe = true;
      boolean increasing = nums[1] > nums[0];
      for (int i = 1; i < nums.length; i++) {
        int diff = nums[i] - nums[i - 1];
        if (increasing && (diff < 1 || diff > 3) || !increasing && (diff < -3 || diff > -1)) {
          safe = false;
          break;
        }
      }
      if (safe) {
        ans++;
      }
    }
    System.out.println(ans);
  }
}
