package day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Part2 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  public Part2() throws FileNotFoundException {
    long total = 0;
    var numbers = new Long[4000];
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      if (line.charAt(0) != '*' && line.charAt(0) != '+') {
        for (int i = 0; i < line.length(); i++) {
          if (line.charAt(i) != ' ') {
            if (numbers[i] == null) {
              numbers[i] = 0L;
            }
            numbers[i] = numbers[i] * 10 + line.charAt(i) - '0';
          }
        }
      } else {
        int i = 0;
        while (i < line.length()) {
          assert line.charAt(i) == '*' || line.charAt(i) == '+';
          int j = i;
          if (line.charAt(i) == '*') {
            long product = 1L;
            while (j < numbers.length && numbers[j] != null) {
              product *= numbers[j];
              j++;
            }
            total += product;
          } else if (line.charAt(i) == '+') {
            while (j < numbers.length && numbers[j] != null) {
              total += numbers[j];
              j++;
            }
          }
          i = j + 1;
        }
      }
    }
    System.out.println(total);
  }
}
