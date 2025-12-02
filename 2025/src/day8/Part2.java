package day8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Part2 {
  private final File file = new File(getClass().getResource("input.txt").getPath());
  private final char[][] map = new char[50][];
  private final Map<Character, List<int[]>> antennae = new HashMap<>();

  public Part2() throws FileNotFoundException {
    var scanner = new Scanner(file);
    int i = 0;
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      map[i] = line.toCharArray();
      for (int j = 0; j < line.length(); j++) {
        char c = line.charAt(j);
        if (c != '.') {
          if (!antennae.containsKey(c)) {
            antennae.put(c, new ArrayList<>());
          }
          antennae.get(c).add(new int[] {i, j});
        }
      }
      i++;
    }

    int ans = 0;
    var visited = new boolean[map.length][map[0].length];
    for (var positions : antennae.values()) {
      for (i = 0; i < positions.size() - 1; i++) {
        var pos1 = positions.get(i);
        for (int j = i + 1; j < positions.size(); j++) {
          var pos2 = positions.get(j);
          var di = pos2[0] - pos1[0];
          var dj = pos2[1] - pos1[1];
          var pos3 = new int[] {pos1[0], pos1[1]};
          while (pos3[0] >= 0 && pos3[1] >= 0 && pos3[0] < map.length && pos3[1] < map[0].length) {
            if (!visited[pos3[0]][pos3[1]]) {
              ans++;
              map[pos3[0]][pos3[1]] = '#';
              visited[pos3[0]][pos3[1]] = true;
            }
            pos3[0] -= di;
            pos3[1] -= dj;
          }
          var pos4 = new int[] {pos2[0], pos2[1]};
          while (pos4[0] >= 0 && pos4[1] >= 0 && pos4[0] < map.length && pos4[1] < map[0].length) {
            if (!visited[pos4[0]][pos4[1]]) {
              ans++;
              map[pos4[0]][pos4[1]] = '#';
              visited[pos4[0]][pos4[1]] = true;
            }
            pos4[0] += di;
            pos4[1] += dj;
          }
        }
      }
    }
    System.out.println(ans);
  }
}
