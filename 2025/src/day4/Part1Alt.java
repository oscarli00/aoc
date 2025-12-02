package day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Part1Alt {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  public Part1Alt() throws FileNotFoundException {
    var puzzle = new ArrayList<List<Character>>();
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      var charList = Arrays.stream(line.split("")).map(s -> s.charAt(0)).toList();
      puzzle.add(charList);
    }

    var ans = countHorizontal(puzzle) + countVertical(puzzle);
    for (int i = 3; i < puzzle.size(); i++) {
      ans += countBottomLeftToTopRightDiagonal(puzzle, i, 0);
    }
    for (int j = 1; j < puzzle.get(0).size(); j++) {
      ans += countBottomLeftToTopRightDiagonal(puzzle, puzzle.size() - 1, j);
    }
    for (int i = 0; i < puzzle.size(); i++) {
      ans += countTopLeftToBottomRightDiagonal(puzzle, i, 0);
    }
    for (int j = 1; j < puzzle.get(0).size(); j++) {
      ans += countTopLeftToBottomRightDiagonal(puzzle, 0, j);
    }
    System.out.println(ans);
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

  int countBottomLeftToTopRightDiagonal(List<List<Character>> puzzle, int i, int j) {
    int count = 0;
    while (i >= 3 && j < puzzle.get(0).size() - 3) {
      if (puzzle.get(i).get(j).equals('X')
              && puzzle.get(i - 1).get(j + 1).equals('M')
              && puzzle.get(i - 2).get(j + 2).equals('A')
              && puzzle.get(i - 3).get(j + 3).equals('S')
          || puzzle.get(i).get(j).equals('S')
              && puzzle.get(i - 1).get(j + 1).equals('A')
              && puzzle.get(i - 2).get(j + 2).equals('M')
              && puzzle.get(i - 3).get(j + 3).equals('X')) {
        count++;
      }
      i--;
      j++;
    }
    return count;
  }

  int countTopLeftToBottomRightDiagonal(List<List<Character>> puzzle, int i, int j) {
    int count = 0;
    while (i < puzzle.size() - 3 && j < puzzle.get(0).size() - 3) {
      if (puzzle.get(i).get(j).equals('X')
              && puzzle.get(i + 1).get(j + 1).equals('M')
              && puzzle.get(i + 2).get(j + 2).equals('A')
              && puzzle.get(i + 3).get(j + 3).equals('S')
          || puzzle.get(i).get(j).equals('S')
              && puzzle.get(i + 1).get(j + 1).equals('A')
              && puzzle.get(i + 2).get(j + 2).equals('M')
              && puzzle.get(i + 3).get(j + 3).equals('X')) {
        count++;
      }
      i++;
      j++;
    }
    return count;
  }
}
