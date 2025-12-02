package day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import javax.annotation.Nullable;

public class Part2 {
  private File file = new File(getClass().getResource("input.txt").getPath());

  public Part2() throws FileNotFoundException {
    int ans = 0;
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      var nums = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
      if (isSafe(nums, 0, null, false) || isSafe(nums, 1, null, true)) {
        ans++;
      }
    }
    System.out.println(ans);
  }

  private boolean isSafe(int[] nums, int i, @Nullable Boolean increasing, boolean removed) {
    if (i == nums.length - 1) {
      return true;
    }
    int absDiff = Math.abs(nums[i + 1] - nums[i]);
    if (absDiff >= 1
        && absDiff <= 3
        && (increasing == null || increasing == nums[i + 1] > nums[i])) {
      if (isSafe(nums, i + 1, nums[i + 1] > nums[i], removed)) {
        return true;
      }
    }
    if (!removed) {
      if (i == nums.length - 2) {
        return true;
      }
      absDiff = Math.abs(nums[i + 2] - nums[i]);
      if (absDiff >= 1
          && absDiff <= 3
          && (increasing == null || increasing == nums[i + 2] - nums[i] > 0)) {
        return isSafe(nums, i + 2, nums[i + 2] - nums[i] > 0, true);
      }
    }
    return false;
  }
}
