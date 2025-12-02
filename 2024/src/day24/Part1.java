package day24;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  private final TreeMap<String, Integer> state = new TreeMap<>();

  public Part1() throws FileNotFoundException {
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      if (line.isEmpty()) {
        break;
      }
      var wire = line.substring(0, line.indexOf(':'));
      state.put(wire, line.charAt(line.indexOf(':') + 2) - '0');
    }
    var connections = new ArrayList<String>();
    while (scanner.hasNextLine()) {
      connections.add(scanner.nextLine());
    }
    for (int i = 0; i < connections.size(); i++) {
      for (var s : connections) {
        var arr = s.split(" ");
        if (state.containsKey(arr[4])) {
          continue;
        }
        if (state.containsKey(arr[0]) && state.containsKey(arr[2])) {
          state.put(
              arr[4],
              switch (arr[1]) {
                case "AND" -> state.get(arr[0]) & state.get(arr[2]);
                case "OR" -> state.get(arr[0]) | state.get(arr[2]);
                case "XOR" -> state.get(arr[0]) ^ state.get(arr[2]);
                default -> throw new IllegalStateException("Unexpected value: " + arr[1]);
              });
        }
      }
    }
    System.out.println(state);
    long ans = 0;
    for (var entry : state.descendingMap().entrySet()) {
      if (entry.getKey().startsWith("z")) {
        ans <<= 1;
        ans |= entry.getValue();
      }
    }
    System.out.println(ans);
  }
}
