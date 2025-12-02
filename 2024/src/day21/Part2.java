package day21;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Part2 {
    private final File file = new File(getClass().getResource("input.txt").getPath());
    private static final String[][] directionalKeypadSteps = {
            // 0 = up arrow,
            // 1 = right arrow
            // 2 = down arrow
            // 3 = left arrow
            // 4 = A

            // from up arrow
            {"", "v>", "v", "v<", ">"},
            // from right arrow
            {"<^", "", "<", "<<", "^"},
            // from down arrow
            {"^", ">", "", "<", "^>"}, /* changed from >^ to ^> */
            // from left arrow
            {">^", ">>", ">", "", ">>^"},
            // from A
            {"<", "v", "<v", "v<<", ""}, /* changed from v< to <v */
    }; // wtf changing the ordering of the steps makes a difference

    private static final Map<Character, Integer> indexOfDirection = Map.of(
            '^', 0,
            '>', 1,
            'v', 2,
            '<', 3,
            'A', 4
    );

    private static final Map<Character, int[]> keyToPos = Map.ofEntries(
            Map.entry('7', new int[]{0, 0}),
            Map.entry('8', new int[]{0, 1}),
            Map.entry('9', new int[]{0, 2}),
            Map.entry('4', new int[]{1, 0}),
            Map.entry('5', new int[]{1, 1}),
            Map.entry('6', new int[]{1, 2}),
            Map.entry('1', new int[]{2, 0}),
            Map.entry('2', new int[]{2, 1}),
            Map.entry('3', new int[]{2, 2}),
            Map.entry('0', new int[]{3, 1}),
            Map.entry('A', new int[]{3, 2})
    );

    private Long[][][] cache = new Long[255][255][26];

    public Part2() throws FileNotFoundException {
        Multimap<String, String> codesToSequence = HashMultimap.create();
        var scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            var code = scanner.nextLine();
            var lines = dfs(code, 0, keyToPos.get('A'), new StringBuilder());
            for (var line : lines) {
                codesToSequence.put(code, line);
            }
        }

        var codesToLen = new HashMap<String, Long>();
        for (var entry : codesToSequence.entries()) {
            var code = entry.getKey();
            var line = entry.getValue();
            long res = 0;
            var prev = 'A';
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                res += dfs2(c, prev, 0);
                prev = c;
            }

            if (codesToLen.get(code) == null || res < codesToLen.get(code)) {
                codesToLen.put(code, res);
            }
        }

        long ans = 0;
        for (var entry : codesToLen.entrySet()) {
            System.out.println("code: " + entry.getKey() + " len: " + entry.getValue());
            ans += Integer.parseInt(entry.getKey().substring(0, entry.getKey().indexOf('A'))) * entry.getValue();
        }
        System.out.println("ans: " + ans);
    }

    private List<String> dfs(String line, int index, int[] prev, StringBuilder sb) {
        if (index == line.length()) {
            return List.of(sb.toString());
        }
        var res = new ArrayList<String>();
        char c = line.charAt(index);
        var pos = keyToPos.get(c);
        int di = pos[0] - prev[0];
        int dj = pos[1] - prev[1];
        var vert = (di > 0 ? "v" : "^").repeat(Math.abs(di));
        var horiz = (dj > 0 ? ">" : "<").repeat(Math.abs(dj));
        if (di != 0 && !(pos[0] == 3 && prev[1] == 0)) {
            sb.append(vert).append(horiz).append('A');
            res.addAll(dfs(line, index + 1, pos, sb));
            sb.setLength(sb.length() - vert.length() - horiz.length() - 1);
        }
        if (dj != 0 && !(pos[1] == 0 && prev[0] == 3)) {
            sb.append(horiz).append(vert).append('A');
            res.addAll(dfs(line, index + 1, pos, sb));
            sb.setLength(sb.length() - vert.length() - horiz.length() - 1);
        }
        return res;
    }

    private long dfs2(char c, char prev, int layer) {
        if (layer == 25) {
            return 1;
        }
        if (cache[c][prev][layer] != null) {
            return cache[c][prev][layer];
        }
        long res = 0;
        var steps = directionalKeypadSteps[indexOfDirection.get(prev)][indexOfDirection.get(c)] + "A";
        var p = 'A';
        for (int i = 0; i < steps.length(); i++) {
            char d = steps.charAt(i);
            res += dfs2(d, p, layer + 1);
            p = d;
        }
        return cache[c][prev][layer] = res;
    }
}

// 2904729103348 too low
// 6227550443103 too low
// 12867318002530 too low
// 358872004957366
// 253860136877472
// 223285811665866