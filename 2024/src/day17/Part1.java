package day17;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());
  long a;
  long b;
  long c;

  public Part1() throws FileNotFoundException {
    var scanner = new Scanner(file);
    var line = scanner.nextLine();
    a = Long.parseLong(line.substring(line.indexOf("A: ") + 3));
    line = scanner.nextLine();
    b = Long.parseLong(line.substring(line.indexOf("B: ") + 3));
    line = scanner.nextLine();
    c = Long.parseLong(line.substring(line.indexOf("C: ") + 3));

    scanner.nextLine();
    line = scanner.nextLine();
    var program =
        Arrays.stream(line.substring(line.indexOf("m: ") + 3).split(","))
            .mapToInt(Integer::parseInt)
            .toArray();
    scanner.close();

    var output = new StringBuilder();
    for (int i = 0; i < program.length; i += 2) {
      int opcode = program[i];
      int operand = program[i + 1];
      switch (opcode) {
        case 0 -> a = adv(operand);
        case 1 -> b ^= operand;
        case 2 ->
            b =
                switch (operand) {
                  case 0, 1, 2, 3 -> operand;
                  case 4 -> a % 8;
                  case 5 -> b % 8;
                  case 6 -> c % 8;
                  default -> throw new IllegalStateException("Unexpected value: " + operand);
                };
        case 3 -> {
          if (a != 0) {
            i = operand - 2;
          }
        }
        case 4 -> b ^= c;
        case 5 -> {
          switch (operand) {
            case 0, 1, 2, 3 -> output.append(operand);
            case 4 -> output.append(a % 8);
            case 5 -> output.append(b % 8);
            case 6 -> output.append(c % 8);
            default -> throw new IllegalStateException("Unexpected value: " + operand);
          }
        }
        case 6 -> b = adv(operand);
        case 7 -> c = adv(operand);
      }
    }
    System.out.println(String.join(",", output.toString().split("")));
  }

  long adv(int operand) {
    return switch (operand) {
      case 0, 1, 2, 3 -> a >> operand;
      case 4 -> a >> a;
      case 5 -> a >> b;
      case 6 -> a >> c;
      default -> throw new IllegalStateException("Unexpected value: " + operand);
    };
  }
}
