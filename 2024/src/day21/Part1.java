package day21;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());
  private static final String[][] directionalKeypadSteps = {
    // 0 = up arrow,
    // 1 = right arrow
    // 2 = down arrow
    // 3 = left arrow
    // 4 = A

    // from up arrow
    {"", "v>", "v", "v<", ">"},
    // from right arrow
    {"<^", "", "<", "<<", "^"},
    // from down arrow
    {"^", ">", "", "<", ">^"},
    // from left arrow
    {">^", ">>", ">", "", ">>^"},
    // from A
    {"<", "v", "v<", "v<<", ""},
  };

  private static final Map<Character, Integer> indexOfDirection =
      Map.of(
          '^', 0,
          '>', 1,
          'v', 2,
          '<', 3,
          'A', 4);

  private static final Map<Character, int[]> keyToPos =
      Map.ofEntries(
          Map.entry('7', new int[] {0, 0}),
          Map.entry('8', new int[] {0, 1}),
          Map.entry('9', new int[] {0, 2}),
          Map.entry('4', new int[] {1, 0}),
          Map.entry('5', new int[] {1, 1}),
          Map.entry('6', new int[] {1, 2}),
          Map.entry('1', new int[] {2, 0}),
          Map.entry('2', new int[] {2, 1}),
          Map.entry('3', new int[] {2, 2}),
          Map.entry('0', new int[] {3, 1}),
          Map.entry('A', new int[] {3, 2}));

  public Part1() throws FileNotFoundException {
    var linesToCodes = new HashMap<String, String>();
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var code = scanner.nextLine();
      var lines = dfs(code, 0, keyToPos.get('A'), new StringBuilder());
      for (var line : lines) {
        linesToCodes.put(line, code);
      }
    }

    var codesToSequence2 = new HashMap<String, String>();
    var codesToSequence1 = new HashMap<String, String>();
    for (var entry : linesToCodes.entrySet()) {
      var line = entry.getKey();
      var code = entry.getValue();
      var sb1 = new StringBuilder();
      char prev = 'A';
      for (int i = 0; i < line.length(); i++) {
        char c = line.charAt(i);
        sb1.append(directionalKeypadSteps[indexOfDirection.get(prev)][indexOfDirection.get(c)]);
        sb1.append('A');
        prev = c;
      }
      var sb2 = new StringBuilder();
      prev = 'A';
      for (int i = 0; i < sb1.length(); i++) {
        char c = sb1.charAt(i);
        sb2.append(directionalKeypadSteps[indexOfDirection.get(prev)][indexOfDirection.get(c)]);
        sb2.append('A');
        prev = c;
      }

      if (codesToSequence2.get(code) == null
          || sb2.length() < codesToSequence2.get(code).length()) {
        codesToSequence1.put(code, sb1.toString());
        codesToSequence2.put(code, sb2.toString());
      }
    }

    int ans = 0;
    for (var entry : codesToSequence2.entrySet()) {
      System.out.println(entry.getKey());
      System.out.println(codesToSequence1.get(entry.getKey()));
      System.out.println(entry.getValue());
      ans +=
          Integer.parseInt(entry.getKey().substring(0, entry.getKey().indexOf('A')))
              * entry.getValue().length();
    }
    System.out.println("ans: " + ans);
  }

  private List<String> dfs(String line, int index, int[] prev, StringBuilder sb) {
    if (index == line.length()) {
      return List.of(sb.toString());
    }
    var res = new ArrayList<String>();
    char c = line.charAt(index);
    var pos = keyToPos.get(c);
    int di = pos[0] - prev[0];
    int dj = pos[1] - prev[1];
    var vert = (di > 0 ? "v" : "^").repeat(Math.abs(di));
    var horiz = (dj > 0 ? ">" : "<").repeat(Math.abs(dj));
    if (di != 0 && !(prev[1] == 0 && pos[0] == 3)) {
      sb.append(vert).append(horiz).append('A');
      res.addAll(dfs(line, index + 1, pos, sb));
      sb.setLength(sb.length() - vert.length() - horiz.length() - 1);
    }
    if (dj != 0 && !(pos[1] == 0 && prev[0] == 3)) {
      sb.append(horiz).append(vert).append('A');
      res.addAll(dfs(line, index + 1, pos, sb));
      sb.setLength(sb.length() - vert.length() - horiz.length() - 1);
    }
    return res;
  }
}
