package day15;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Part1 {
  private final File file = new File(getClass().getResource("input.txt").getPath());
  private static final int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

  public Part1() throws FileNotFoundException {
    var scanner = new Scanner(file);
    var lists = new ArrayList<char[]>();
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      if (line.isEmpty()) {
        break;
      }
      lists.add(line.toCharArray());
    }
    var map = new char[lists.size()][];
    for (int i = 0; i < map.length; i++) {
      map[i] = lists.get(i);
    }
    var moves = new ArrayList<Character>();
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      for (int i = 0; i < line.length(); i++) {
        moves.add(line.charAt(i));
      }
    }
    scanner.close();

    int row = -1;
    int col = -1;
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[0].length; j++) {
        if (map[i][j] == '@') {
          row = i;
          col = j;
          break;
        }
      }
    }

    for (char c : moves) {
      int[] res =
          switch (c) {
            case '^' -> move(map, row, col, dirs[0]);
            case '>' -> move(map, row, col, dirs[1]);
            case 'v' -> move(map, row, col, dirs[2]);
            case '<' -> move(map, row, col, dirs[3]);
            default -> throw new IllegalStateException("Unexpected value: " + c);
          };
      row = res[0];
      col = res[1];
    }

    for (var arr : map) {
      System.out.println(Arrays.toString(arr));
    }

    int ans = 0;
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[0].length; j++) {
        if (map[i][j] == 'O') {
          ans += 100 * i + j;
        }
      }
    }
    System.out.println(ans);
  }

  int[] move(char[][] map, int row, int col, int[] dir) {
    int targetRow = row + dir[0];
    int targetCol = col + dir[1];
    if (map[targetRow][targetCol] != '.') {
      int currRow = targetRow;
      int currCol = targetCol;
      while (map[currRow][currCol] != '.') {
        if (map[currRow][currCol] == '#') {
          return new int[] {row, col};
        }
        currRow += dir[0];
        currCol += dir[1];
      }
      map[currRow][currCol] = 'O';
    }
    map[targetRow][targetCol] = '@';
    map[row][col] = '.';
    return new int[] {targetRow, targetCol};
  }
}
