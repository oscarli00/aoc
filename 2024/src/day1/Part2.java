package day1;

import com.google.common.collect.HashMultiset;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Part2 {
  private File file = new File(getClass().getResource("input.txt").getPath());

  public Part2() throws FileNotFoundException {
    var list1 = new ArrayList<Integer>();
    var bag2 = HashMultiset.create();
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      list1.add(scanner.nextInt());
      bag2.add(scanner.nextInt());
    }
    int ans = 0;
    for (var n : list1) {
      ans += n * bag2.count(n);
    }
    System.out.println(ans);
  }
}
