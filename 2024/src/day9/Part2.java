package day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Part2 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  public Part2() throws FileNotFoundException {
    var scanner = new Scanner(file);
    var line = scanner.nextLine();
    var sizes = new int[(line.length() + 1) / 2];
    for (int i = 0; i < line.length(); i += 2) {
      sizes[i / 2] = line.charAt(i) - '0';
    }
    var visited = new boolean[(line.length() + 1) / 2];
    long ans = 0;
    int pos = 0;
    for (int i = 0; i < line.length(); i++) {
      int n = line.charAt(i) - '0';
      if (i % 2 == 0) {
        int id = i / 2;
        if (!visited[id]) {
          while (n > 0) {
            ans += (long) id * pos++;
            n--;
          }
        }
      } else {
        for (int id = sizes.length - 1; id > i / 2; id--) {
          if (!visited[id] && sizes[id] <= n) {
            while (sizes[id]-- > 0) {
              ans += (long) id * pos++;
              n--;
            }
            visited[id] = true;
          }
        }
      }
      assert n >= 0;
      pos += n;
    }
    System.out.println(ans);
  }
}
