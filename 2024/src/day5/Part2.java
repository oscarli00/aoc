package day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Part2 {
  private final File file = new File(getClass().getResource("input2.txt").getPath());
  private final Map<Integer, Set<Integer>> dependencyFor = new HashMap<>();

  public Part2() throws FileNotFoundException {
    int ans = 0;
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      if (line.isEmpty()) {
        break;
      }
      var pair = line.split("\\|");
      var num1 = Integer.parseInt(pair[0]);
      var num2 = Integer.parseInt(pair[1]);
      if (!dependencyFor.containsKey(num1)) {
        dependencyFor.put(num1, new HashSet<>());
      }
      dependencyFor.get(num1).add(num2);
    }
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      var nums = Arrays.stream(line.split(",")).map(Integer::parseInt).toList();
      var included = new HashSet<>(nums);
      var corrected = correctOrder(nums, included);
      ans += corrected.get(corrected.size() / 2);
    }
    System.out.println(ans);
  }

  List<Integer> correctOrder(List<Integer> nums, Set<Integer> included) {
    var depCounts = new HashMap<Integer, Integer>();
    for (int n : nums) {
      for (int m : dependencyFor.get(n)) {
        if (included.contains(m)) {
          depCounts.put(m, depCounts.getOrDefault(m, 0) + 1);
        }
      }
    }
    var queue = new LinkedList<Integer>();
    for (int n : nums) {
      if (!depCounts.containsKey(n)) {
        queue.add(n);
      }
    }
    var order = new ArrayList<Integer>();
    while (!queue.isEmpty()) {
      int n = queue.poll();
      for (int m : dependencyFor.get(n)) {
        var count = depCounts.get(m);
        if (count != null) {
          depCounts.put(m, --count);
          if (count == 0) {
            queue.add(m);
          }
        }
      }
      order.add(n);
    }
    return order;
  }
}
