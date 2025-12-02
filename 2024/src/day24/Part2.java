package day24;

import static guru.nidi.graphviz.model.Factory.*;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Part2 {

  private final File incorrect = new File(getClass().getResource("input.txt").getPath());
  private final File correct = new File(getClass().getResource("input2.txt").getPath());

  public Part2() throws FileNotFoundException {
    var scanner = new Scanner(correct);
    while (scanner.hasNextLine()) {
      if (scanner.nextLine().isEmpty()) {
        break;
      }
    }
    var graph = mutGraph().setDirected(true);
    while (scanner.hasNextLine()) {
      var arr = scanner.nextLine().split(" ");
      var in1 = mutNode(arr[0]);
      var in2 = mutNode(arr[2]);
      var out = mutNode(arr[4]);
      if (arr[4].startsWith("z")) {
        out.add(Color.BROWN);
      }
      graph.add(
          in1.addLink(
              to(out)
                  .add(
                      arr[1].equals("XOR")
                          ? Color.RED
                          : arr[1].equals("AND") ? Color.BLUE : Color.GREEN)),
          in2.addLink(
              to(out)
                  .add(
                      arr[1].equals("XOR")
                          ? Color.RED
                          : arr[1].equals("AND") ? Color.BLUE : Color.GREEN)));
    }
    try {
      Graphviz.fromGraph(graph).render(Format.PNG).toFile(new File("src/day24/graph.png"));
    } catch (Exception ignored) {
    }

    System.out.println(ans);
  }

  private static final String ans =
      Stream.of("shh", "z21", "dtk", "vgs", "dqr", "z33", "pfw", "z39")
          .sorted()
          .collect(Collectors.joining(","));
}

// XOR = red, AND = blue, OR = green

// jsq XOR vcj -> z21 instead of shh
// jsq AND vcj -> shh instead of z21

// x26 XOR y26 -> vgs instead of dtk
// x26 AND y26 -> dtk instead of vgs

// vkp XOR jqp -> z33 instead of dqr
// fdv OR mtw -> dqr instead of z33

// gqn XOR sjq -> z39 instead of pfw
// x39 AND y39 -> pfw instead of z39
