package day15;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Part2 {
  private final File file = new File(getClass().getResource("input.txt").getPath());
  private static final int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

  public Part2() throws FileNotFoundException {
    var scanner = new Scanner(file);
    var lists = new ArrayList<List<Character>>();
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      if (line.isEmpty()) {
        break;
      }
      var chars = getCharacters(line);
      lists.add(chars);
    }
    var map = new char[lists.size()][];
    for (int i = 0; i < map.length; i++) {
      var list = lists.get(i);
      var chars = new char[list.size()];
      for (int j = 0; j < list.size(); j++) {
        chars[j] = list.get(j);
      }
      map[i] = chars;
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
            case '^' -> move(map, row, col, 0);
            case '>' -> move(map, row, col, 1);
            case 'v' -> move(map, row, col, 2);
            case '<' -> move(map, row, col, 3);
            default -> throw new IllegalStateException("Unexpected value: " + c);
          };
      row = res[0];
      col = res[1];
    }

    for (var arr : map) {
      System.out.println(new String(arr));
    }

    int ans = 0;
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[0].length; j++) {
        if (map[i][j] == '[') {
          ans += 100 * i + j;
        }
      }
    }
    System.out.println(ans);
  }

  private static ArrayList<Character> getCharacters(String line) {
    var chars = new ArrayList<Character>();
    for (char c : line.toCharArray()) {
      switch (c) {
        case '#' -> {
          chars.add('#');
          chars.add('#');
        }
        case 'O' -> {
          chars.add('[');
          chars.add(']');
        }
        case '.' -> {
          chars.add('.');
          chars.add('.');
        }
        case '@' -> {
          chars.add('@');
          chars.add('.');
        }
      }
    }
    return chars;
  }

  int[] move(char[][] map, int row, int col, int d) {
    int[] dir = dirs[d];
    int targetRow = row + dir[0];
    int targetCol = col + dir[1];

    if (map[targetRow][targetCol] != '.') {
      if (d % 2 == 1) {
        assert row == targetRow;
        int currCol = targetCol;
        while (map[row][currCol] != '.') {
          if (map[row][currCol] == '#') {
            return new int[] {row, col};
          }
          currCol += dir[1];
        }
        map[row][currCol] = d == 1 ? ']' : '[';
        currCol -= dir[1];
        while (currCol != targetCol) {
          map[row][currCol] = map[row][currCol] == ']' ? '[' : ']';
          currCol -= dir[1];
        }
      } else {
        assert col == targetCol;
        int currRow = targetRow;
        var cols = new ArrayList<Integer>();
        cols.add(col);
        while (!cols.isEmpty()) {
          var nextCols = new ArrayList<Integer>();
          for (var checkCol : cols) {
            char next = map[currRow][checkCol];
            if (next == '#') {
              return new int[] {row, col};
            }
            if (next != '.') {
              nextCols.add(checkCol);
              nextCols.add(next == '[' ? checkCol + 1 : checkCol - 1);
            }
          }
          cols = nextCols;
          currRow += dir[0];
        }

        currRow = targetRow;
        var colsToMove = new HashMap<Integer, Character>();
        colsToMove.put(col, map[row][col]);
        while (!colsToMove.isEmpty()) {
          var nextColsToMove = new HashMap<Integer, Character>();
          for (var colToMove : colsToMove.keySet()) {
            char next = map[currRow][colToMove];
            if (next != '.') {
              nextColsToMove.put(colToMove, next);
              nextColsToMove.put(
                  next == '[' ? colToMove + 1 : colToMove - 1, next == '[' ? ']' : '[');
            }
          }
          for (var nextCol : nextColsToMove.keySet()) {
            map[currRow][nextCol] = '.';
          }
          for (var colToMove : colsToMove.entrySet()) {
            map[currRow][colToMove.getKey()] = colToMove.getValue();
          }
          colsToMove = nextColsToMove;
          currRow += dir[0];
        }
      }
    }

    map[targetRow][targetCol] = '@';
    map[row][col] = '.';
    return new int[] {targetRow, targetCol};
  }
}
