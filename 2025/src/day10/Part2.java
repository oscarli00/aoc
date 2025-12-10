package day10;

import com.microsoft.z3.Context;
import com.microsoft.z3.IntNum;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Part2 {
  private final File file = new File(getClass().getResource("input.txt").getPath());

  private static List<Integer> target;

  public Part2() throws FileNotFoundException {
    long ans = 0;
    var scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      var targetStr = line.substring(line.indexOf('{') + 1, line.indexOf('}')).split(",");
      target = new ArrayList<>();
      for (var s : targetStr) {
        int n = Integer.parseInt(s);
        target.add(n);
      }

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

      var ctx = new Context();
      var optimize = ctx.mkOptimize();

      for (int i = 0; i < target.size(); i++) {
        int total = target.get(i);
        var sum = ctx.mkAdd(ctx.mkInt(0));
        for (int b = 0; b < buttons.size(); b++) {
          if (buttons.get(b).contains(i)) {
            var bExp = ctx.mkIntConst("b" + b);
            sum = ctx.mkAdd(sum, bExp);
          }
        }
        optimize.Add(ctx.mkEq(sum, ctx.mkInt(total)));
      }

      var xExp = ctx.mkIntConst("x");
      var presses = ctx.mkAdd(ctx.mkInt(0));
      for (int b = 0; b < buttons.size(); b++) {
        var bExp = ctx.mkIntConst("b" + b);
        presses = ctx.mkAdd(presses, bExp);
        optimize.Add(ctx.mkGe(bExp, ctx.mkInt(0)));
      }
      optimize.Add(ctx.mkEq(xExp, presses));

      optimize.MkMinimize(xExp);
      optimize.Check();
      var res = (IntNum) optimize.getModel().eval(xExp, true);
      ans += res.getInt();
    }
    System.out.println(ans);
  }
}
