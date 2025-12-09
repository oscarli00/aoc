package day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  public Part1() throws FileNotFoundException {
    var problems = new ArrayList<List<Long>>();
    var scanner = new Scanner(file);
    String[] nums = {};
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      nums = line.trim().split("\\s+");
      if (nums[0].equals("*") || nums[0].equals("+")) {
        break;
      } else {
        for (int i = 0; i < nums.length; i++) {
          if (i >= problems.size()) {
            problems.add(new ArrayList<>());
          }
          problems.get(i).add(Long.parseLong(nums[i]));
        }
      }
    }
    long total = 0;
    for (int i = 0; i < nums.length; i++) {
      var symbol = nums[i];
      if (symbol.equals("*")) {
        long product = 1;
        for (var n : problems.get(i)) {
          product *= n;
        }
        total += product;
      } else {
        for (var n : problems.get(i)) {
          total += n;
        }
      }
    }
    System.out.println(total);
  }
}
