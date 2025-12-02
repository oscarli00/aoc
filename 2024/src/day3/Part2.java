package day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Part2 {
  private final File file = new File(getClass().getResource("input.txt").getPath());
  private final String DO = "do()";
  private final String DONT = "don't()";

  public Part2() throws FileNotFoundException {
    var scanner = new Scanner(file);
    var sb = new StringBuilder();
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      sb.append(line);
      if (scanner.hasNextLine()) {
        sb.append('\n');
      }
    }
    var cleaned = new StringBuilder();
    int start = 0;
    int stop = sb.indexOf(DONT);
    while (stop > -1) {
      cleaned.append(sb.subSequence(start, stop));
      start = sb.indexOf(DO, stop + DONT.length());
      if (start < 0) {
        break;
      }
      stop = sb.indexOf(DONT, start);
    }
    if (start > -1) {
      cleaned.append(sb.subSequence(start, sb.length()));
    }
    System.out.println(Part1.solve(cleaned.toString()));
  }
}
