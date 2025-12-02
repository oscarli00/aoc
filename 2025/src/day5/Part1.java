package day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());
  private final Map<Integer, List<Integer>> dependsOn = new HashMap<>();

  public Part1() throws FileNotFoundException {
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      if (line.isEmpty()) {
        break;
      }
      var pair = line.split("\\|");
      var num1 = Integer.parseInt(pair[0]);
      var num2 = Integer.parseInt(pair[1]);
      if (!dependsOn.containsKey(num2)) {
        dependsOn.put(num2, new ArrayList<>());
      }
      dependsOn.get(num2).add(num1);
    }
    int ans = 0;
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      var nums = Arrays.stream(line.split(",")).map(Integer::parseInt).toList();
      var included = new HashSet<>(nums);
      if (isCorrect(nums, included)) {
        ans += nums.get(nums.size() / 2);
      }
    }
    System.out.println(ans);
  }

  boolean isCorrect(List<Integer> nums, Set<Integer> included) {
    var visited = new HashSet<Integer>();
    for (int n : nums) {
      for (int m : dependsOn.get(n)) {
        if (included.contains(m) && !visited.contains(m)) {
          return false;
        }
      }
      visited.add(n);
    }
    return true;
  }
}
