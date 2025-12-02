package day25;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  public Part1() throws FileNotFoundException {
    var locks = new ArrayList<int[]>();
    var keys = new ArrayList<int[]>();
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      if (line.equals("#####")) {
        line = scanner.nextLine();
        var lock = new int[5];
        while (!line.isEmpty()) {
          for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '#') {
              lock[i]++;
            }
          }
          line = scanner.nextLine();
        }
        locks.add(lock);
      } else {
        var key = new int[] {6, 6, 6, 6, 6};
        while (!line.isEmpty()) {
          for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '.') {
              key[i]--;
            }
          }
          if (scanner.hasNextLine()) {
            line = scanner.nextLine();
          } else {
            break;
          }
        }
        keys.add(key);
      }
    }
    int ans = 0;
    for (var key : keys) {
      for (var lock : locks) {
        boolean fit = true;
        for (int i = 0; i < 5; i++) {
          if (key[i] + lock[i] > 5) {
            fit = false;
            break;
          }
        }
        if (fit) {
          ans++;
        }
      }
    }
    System.out.println(ans);
  }
}
