package day22;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Part2 {
  private final File file = new File(getClass().getResource("input.txt").getPath());
  private static final long mod = 16777216;

  public Part2() throws FileNotFoundException {
    var nums = new ArrayList<Long>();
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      nums.add(Long.parseLong(scanner.nextLine()));
    }

    var bananas = new int[20][20][20][20];
    for (var n : nums) {
      var sequence = new LinkedList<Integer>();
      var visited = new boolean[20][20][20][20];
      int prevPrice = (int) (n % 10);
      for (int i = 0; i < 2000; i++) {
        long curr = n;
        curr = ((curr << 6) ^ curr) % mod;
        curr = ((curr >> 5) ^ curr) % mod;
        curr = ((curr << 11) ^ curr) % mod;
        int price = (int) (curr % 10);
        sequence.add(10 + price - prevPrice);
        if (sequence.size() > 4) {
          sequence.poll();
        }
        if (sequence.size() == 4) {
          var s0 = sequence.get(0);
          var s1 = sequence.get(1);
          var s2 = sequence.get(2);
          var s3 = sequence.get(3);
          if (!visited[s0][s1][s2][s3]) {
            visited[s0][s1][s2][s3] = true;
            bananas[s0][s1][s2][s3] += price;
          }
        }
        prevPrice = price;
        n = curr;
      }
    }
    int ans = 0;
    for (int i = 0; i < 20; i++) {
      for (int j = 0; j < 20; j++) {
        for (int k = 0; k < 20; k++) {
          for (int l = 0; l < 20; l++) {
            ans = Math.max(ans, bananas[i][j][k][l]);
          }
        }
      }
    }
    System.out.println(ans);
  }
}
