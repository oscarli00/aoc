package day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Part1 {

  public Part1() throws FileNotFoundException {
    var list1 = new ArrayList<Integer>();
    var list2 = new ArrayList<Integer>();
    var file = new File(getClass().getResource("input.txt").getPath());
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      list1.add(scanner.nextInt());
      list2.add(scanner.nextInt());
    }
    assert list1.size() == list2.size();
    list1.sort(Integer::compare);
    list2.sort(Integer::compare);
    int ans = 0;
    for (int i = 0; i < list1.size(); i++) {
      ans += Math.abs(list1.get(i) - list2.get(i));
    }
    System.out.println(ans);
  }
}
