package day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  public Part1() throws FileNotFoundException {
    long ans = 0;
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      var target = line.substring(line.indexOf('[') + 1, line.indexOf(']'));
      var buttonsString = line.substring(line.indexOf(']') + 2, line.indexOf('{') - 1).split(" ");
      var buttons = new ArrayList<List<Integer>>();
      for (var s : buttonsString) {
        var lights = s.substring(1, s.length() - 1).split(",");
        var list = new ArrayList<Integer>();
        for (var light : lights) {
          list.add(Integer.parseInt(light));
        }
        buttons.add(list);
      }

      var sb = new StringBuilder();
      sb.append(".".repeat(target.length()));
      var start = sb.toString();

      int presses = 0;
      var queue = new LinkedList<String>();
      queue.add(start);
      while (!queue.isEmpty()) {
        presses++;
        boolean success = false;
        int size = queue.size();
        for (int i = 0; i < size; i++) {
          var curr = queue.poll();
          for (var button : buttons) {
            sb = new StringBuilder(curr);
            for (var light : button) {
              sb.setCharAt(light, sb.charAt(light) == '.' ? '#' : '.');
            }
            var next = sb.toString();
            if (next.equals(target)) {
              success = true;
              break;
            }
            queue.add(next);
          }
          if (success) {
            break;
          }
        }
        if (success) {
          ans += presses;
          break;
        }
      }
    }
    System.out.println(ans);
  }
}
