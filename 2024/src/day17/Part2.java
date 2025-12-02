package day17;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Part2 {

  int[] binaryDigits = new int[48];
  List<Integer> program =
      new java.util.ArrayList<>(List.of(2, 4, 1, 5, 7, 5, 1, 6, 0, 3, 4, 1, 5, 5, 3, 0));

  public Part2() {
    dfs(binaryDigits.length - 1);
  }

  void dfs(int i) {
    if (i == -1) {
      var reversed = new ArrayList<>(Arrays.stream(binaryDigits).boxed().toList());
      Collections.reverse(reversed);
      System.out.println(reversed);
      var bigInt = BigInteger.valueOf(0);
      for (int b = binaryDigits.length - 1; b >= 0; b--) {
        bigInt = bigInt.multiply(BigInteger.TWO).add(BigInteger.valueOf(binaryDigits[b]));
      }
      System.out.println(bigInt);
      // answer = 105981155568026
      runProgram(bigInt.longValue());
      return;
    }
    // Every dfs, find A such that ((A%8) xor 3 xor (A>>(A%8 xor 5))%8) = i/3th digit of program,
    // set A = A>>3, repeat
    int target = program.get(i / 3) ^ 3;
    for (int num = 0; num < 8; num++) {
      var shift = num ^ 5;
      int xor = 0;
      for (int j = i + shift, k = 0; k < 3; j--, k++) {
        xor <<= 1;
        if (j == i) {
          xor += (num & 4) > 0 ? 1 : 0;
        } else if (j == i - 1) {
          xor += (num & 2) > 0 ? 1 : 0;
        } else if (j == i - 2) {
          xor += (num & 1) > 0 ? 1 : 0;
        } else if (j < binaryDigits.length) {
          xor += binaryDigits[j];
        }
      }
      if ((num ^ xor) == target) {
        binaryDigits[i] = (num & 4) > 0 ? 1 : 0;
        binaryDigits[i - 1] = (num & 2) > 0 ? 1 : 0;
        binaryDigits[i - 2] = (num & 1) > 0 ? 1 : 0;
        dfs(i - 3);
        binaryDigits[i] = 0;
        binaryDigits[i - 1] = 0;
        binaryDigits[i - 2] = 0;
      }
    }
  }

  void runProgram(long a) {
    long b = 0;
    long c = 0;

    var program =
        Arrays.stream("2,4,1,5,7,5,1,6,0,3,4,1,5,5,3,0".split(","))
            .mapToInt(Integer::parseInt)
            .toArray();

    var output = new StringBuilder();
    for (int i = 0; i < program.length; i += 2) {
      int opcode = program[i];
      int operand = program[i + 1];
      switch (opcode) {
        case 0 -> a = adv(operand, a, b, c);
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
        case 6 -> b = adv(operand, a, b, c);
        case 7 -> c = adv(operand, a, b, c);
      }
    }
    System.out.println(String.join(",", output.toString().split("")));
  }

  long adv(int operand, long a, long b, long c) {
    return switch (operand) {
      case 0, 1, 2, 3 -> a >> operand;
      case 4 -> a >> a;
      case 5 -> a >> b;
      case 6 -> a >> c;
      default -> throw new IllegalStateException("Unexpected value: " + operand);
    };
  }
}

// 105995297297433
// 52997648648716
// 26498824324358
