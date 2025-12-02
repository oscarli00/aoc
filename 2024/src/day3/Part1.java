package day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());
  private static final Pattern pattern = Pattern.compile("mul\\(([0-9]{1,3}),([0-9]{1,3})\\)");

  public Part1() throws FileNotFoundException {
    long ans = 0;
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      ans += solve(line);
    }
    System.out.println(ans);
  }

  static long solve(String line) {
    long ans = 0;
    var matcher = pattern.matcher(line);
    while (matcher.find()) {
      var str = matcher.group();
      int num1 = Integer.parseInt(str.substring(str.indexOf('(') + 1, str.indexOf(',')));
      int num2 = Integer.parseInt(str.substring(str.indexOf(',') + 1, str.indexOf(')')));
      ans += (long) num1 * num2;
    }
    return ans;
  }
}
