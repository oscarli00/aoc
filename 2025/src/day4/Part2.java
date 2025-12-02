package day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Part2 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  public Part2() throws FileNotFoundException {
    var puzzle = new ArrayList<List<Character>>();
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      var charList = Arrays.stream(line.split("")).map(s -> s.charAt(0)).toList();
      puzzle.add(charList);
    }

    int ans = 0;
    for (int i = 1; i < puzzle.size() - 1; i++) {
      for (int j = 1; j < puzzle.get(0).size() - 1; j++) {
        if (puzzle.get(i).get(j).equals('A')
            && (puzzle.get(i - 1).get(j - 1).equals('M') && puzzle.get(i + 1).get(j + 1).equals('S')
                || puzzle.get(i - 1).get(j - 1).equals('S')
                    && puzzle.get(i + 1).get(j + 1).equals('M'))
            && (puzzle.get(i + 1).get(j - 1).equals('M') && puzzle.get(i - 1).get(j + 1).equals('S')
                || puzzle.get(i + 1).get(j - 1).equals('S')
                    && puzzle.get(i - 1).get(j + 1).equals('M'))) {
          ans++;
        }
      }
    }
    System.out.println(ans);
  }
}
