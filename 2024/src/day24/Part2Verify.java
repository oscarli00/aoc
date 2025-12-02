package day24;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Part2Verify {
  private final File incorrect = new File(getClass().getResource("input.txt").getPath());
  private final File correct = new File(getClass().getResource("input2.txt").getPath());

  private final Map<String, String> system = new HashMap<>();
  private final int[] x = new int[45];
  private final int[] y = new int[45];

  public Part2Verify() throws FileNotFoundException {
    var scanner = new Scanner(incorrect);
    while (scanner.hasNextLine()) {
      if (scanner.nextLine().isEmpty()) {
        break;
      }
    }
    while (scanner.hasNextLine()) {
      var arr = scanner.nextLine().split(" -> ");
      system.put(arr[1], arr[0]);
    }

    for (int i = 0; i < 45; i++) {
      String wire = "z" + String.format("%02d", i);

      x[i] = 1;
      y[i] = 0;
      verify(wire, 1);

      x[i] = 0;
      y[i] = 1;
      verify(wire, 1);

      x[i] = 1;
      y[i] = 1;
      verify(wire, 0);

      if (i > 0) {
        x[i - 1] = 1;
        y[i - 1] = 1;
        verify(wire, 1);
        x[i - 1] = 0;
        y[i - 1] = 0;
      }

      x[i] = 0;
      y[i] = 0;
    }
  }

  private void verify(String wire, int expected) {
    int result = dfs(wire);
    if (result != expected) {
      System.out.println(
          "Incorrect result for " + wire + ". Expected: " + expected + "   Actual: " + result);
    }
  }

  private int dfs(String wire) {
    if (wire.startsWith("x")) {
      return x[Integer.parseInt(wire.substring(1))];
    }
    if (wire.startsWith("y")) {
      return y[Integer.parseInt(wire.substring(1))];
    }
    var equation = system.get(wire).split(" ");
    return switch (equation[1]) {
      case "AND" -> dfs(equation[0]) & dfs(equation[2]);
      case "OR" -> dfs(equation[0]) | dfs(equation[2]);
      case "XOR" -> dfs(equation[0]) ^ dfs(equation[2]);
      default -> throw new IllegalStateException("Unexpected value: " + equation[1]);
    };
  }
}

//  Incorrect result for z21. Expected: 1   Actual: 0
//  Incorrect result for z21. Expected: 1   Actual: 0
//  Incorrect result for z21. Expected: 1   Actual: 0
//  Incorrect result for z26. Expected: 1   Actual: 0
//  Incorrect result for z26. Expected: 1   Actual: 0
//  Incorrect result for z26. Expected: 0   Actual: 1
//  Incorrect result for z26. Expected: 1   Actual: 0
//  Incorrect result for z27. Expected: 1   Actual: 0
//  Incorrect result for z33. Expected: 1   Actual: 0
//  Incorrect result for z33. Expected: 1   Actual: 0
//  Incorrect result for z33. Expected: 0   Actual: 1
//  Incorrect result for z34. Expected: 1   Actual: 0
//  Incorrect result for z39. Expected: 1   Actual: 0
//  Incorrect result for z39. Expected: 1   Actual: 0
//  Incorrect result for z39. Expected: 0   Actual: 1
//  Incorrect result for z40. Expected: 1   Actual: 0
