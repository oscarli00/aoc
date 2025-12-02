package day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  public Part1() throws FileNotFoundException {
    var puzzle = new ArrayList<List<Character>>();
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      var charList = Arrays.stream(line.split("")).map(s -> s.charAt(0)).toList();
      puzzle.add(charList);
    }

    System.out.println("rows : " + puzzle.size());
    System.out.println("cols : " + puzzle.get(0).size());

    System.out.println(
        countHorizontal(puzzle)
            + countVertical(puzzle)
            + countBottomLeftToTopRightDiagonal(puzzle)
            + countTopLeftToBottomRightDiagonal(puzzle));
  }

  int countHorizontal(List<List<Character>> puzzle) {
    int count = 0;
    for (var list : puzzle) {
      for (int j = 0; j < list.size() - 3; j++) {
        if (list.get(j).equals('X')
                && list.get(j + 1).equals('M')
                && list.get(j + 2).equals('A')
                && list.get(j + 3).equals('S')
            || list.get(j).equals('S')
                && list.get(j + 1).equals('A')
                && list.get(j + 2).equals('M')
                && list.get(j + 3).equals('X')) {
          count++;
        }
      }
    }
    return count;
  }

  int countVertical(List<List<Character>> puzzle) {
    int count = 0;
    for (int j = 0; j < puzzle.get(0).size(); j++) {
      for (int i = 0; i < puzzle.size() - 3; i++) {
        if (puzzle.get(i).get(j).equals('X')
                && puzzle.get(i + 1).get(j).equals('M')
                && puzzle.get(i + 2).get(j).equals('A')
                && puzzle.get(i + 3).get(j).equals('S')
            || puzzle.get(i).get(j).equals('S')
                && puzzle.get(i + 1).get(j).equals('A')
                && puzzle.get(i + 2).get(j).equals('M')
                && puzzle.get(i + 3).get(j).equals('X')) {
          count++;
        }
      }
    }
    return count;
  }

  int countBottomLeftToTopRightDiagonal(List<List<Character>> puzzle) {
    int count = 0;
    // top left half
    for (int j = 3; j < puzzle.get(0).size(); j++) {
      for (int i = 0; i <= j - 3; i++) {
        if (puzzle.get(i).get(j - i).equals('X')
                && puzzle.get(i + 1).get(j - i - 1).equals('M')
                && puzzle.get(i + 2).get(j - i - 2).equals('A')
                && puzzle.get(i + 3).get(j - i - 3).equals('S')
            || puzzle.get(i).get(j - i).equals('S')
                && puzzle.get(i + 1).get(j - i - 1).equals('A')
                && puzzle.get(i + 2).get(j - i - 2).equals('M')
                && puzzle.get(i + 3).get(j - i - 3).equals('X')) {
          count++;
        }
      }
    }
    // bottom right half
    for (int j = 1; j < puzzle.get(0).size() - 3; j++) {
      for (int i = puzzle.size() - 1; i > j + 2; i--) {
        if (puzzle.get(i).get(j + puzzle.size() - 1 - i).equals('X')
                && puzzle.get(i - 1).get(j + puzzle.size() - 1 - i + 1).equals('M')
                && puzzle.get(i - 2).get(j + puzzle.size() - 1 - i + 2).equals('A')
                && puzzle.get(i - 3).get(j + puzzle.size() - 1 - i + 3).equals('S')
            || puzzle.get(i).get(j + puzzle.size() - 1 - i).equals('S')
                && puzzle.get(i - 1).get(j + puzzle.size() - 1 - i + 1).equals('A')
                && puzzle.get(i - 2).get(j + puzzle.size() - 1 - i + 2).equals('M')
                && puzzle.get(i - 3).get(j + puzzle.size() - 1 - i + 3).equals('X')) {
          count++;
        }
      }
    }

    return count;
  }

  int countTopLeftToBottomRightDiagonal(List<List<Character>> puzzle) {
    int count = 0;
    // bottom left half
    for (int i = 0; i < puzzle.size() - 3; i++) {
      for (int j = 0; j < puzzle.get(0).size() - i - 3; j++) {
        if (puzzle.get(i + j).get(j).equals('X')
                && puzzle.get(i + j + 1).get(j + 1).equals('M')
                && puzzle.get(i + j + 2).get(j + 2).equals('A')
                && puzzle.get(i + j + 3).get(j + 3).equals('S')
            || puzzle.get(i + j).get(j).equals('S')
                && puzzle.get(i + j + 1).get(j + 1).equals('A')
                && puzzle.get(i + j + 2).get(j + 2).equals('M')
                && puzzle.get(i + j + 3).get(j + 3).equals('X')) {
          count++;
        }
      }
    }
    // top right half
    for (int j = 1; j < puzzle.get(0).size() - 3; j++) {
      for (int i = 0; i < puzzle.size() - j - 3; i++) {
        if (puzzle.get(i).get(j + i).equals('X')
                && puzzle.get(i + 1).get(j + i + 1).equals('M')
                && puzzle.get(i + 2).get(j + i + 2).equals('A')
                && puzzle.get(i + 3).get(j + i + 3).equals('S')
            || puzzle.get(i).get(j + i).equals('S')
                && puzzle.get(i + 1).get(j + i + 1).equals('A')
                && puzzle.get(i + 2).get(j + i + 2).equals('M')
                && puzzle.get(i + 3).get(j + i + 3).equals('X')) {
          count++;
        }
      }
    }
    return count;
  }
}
