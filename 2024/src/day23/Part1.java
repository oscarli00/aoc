package day23;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Stream;

public class Part1 {
    record Triangle(String v1, String v2, String v3) {
    }

    private final File file = new File(getClass().getResource("input.txt").getPath());

    public Part1() throws FileNotFoundException {
        Multimap<String, String> adj = HashMultimap.create();
        var tset = new HashSet<String>();
        var scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            var edge = scanner.nextLine().split("-");
            adj.put(edge[0], edge[1]);
            adj.put(edge[1], edge[0]);
            if (edge[0].startsWith("t")) {
                tset.add(edge[0]);
            }
            if (edge[1].startsWith("t")) {
                tset.add(edge[1]);
            }
        }

        var ans = new HashSet<Triangle>();
        for (var t : tset) {
            var neighbours = adj.get(t);
            for (var pc : neighbours) {
                for (var next : adj.get(pc)) {
                    if (neighbours.contains(next)) {
                        var sorted = Stream.of(t,pc,next).sorted().toList();
                        ans.add(new Triangle(sorted.get(0),sorted.get(1),sorted.get(2)));
                    }
                }
            }
        }
        System.out.println(ans.size());
    }
}
