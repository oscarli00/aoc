from collections import defaultdict
from functools import cache
import time

DIRS = [(-1, 0), (1, 0), (0, -1), (0, 1)]


def index(c):
    if c.islower():
        return ord(c)-ord("a")
    if c.isupper():
        return ord(c)-ord("A")
    raise Exception()


if __name__ == "__main__":
    start = time.perf_counter()

    with open("input2.txt") as f:
        grid = [list(line) for line in f.read().splitlines()]

    paths = defaultdict(lambda: defaultdict(list))

    def backtrack(i: int, j: int, source: str, target: str, steps: int, keys: int, doors: int, visited: set):
        if grid[i][j] == target:
            paths[source][target].append((steps, keys, doors))
            paths[target][source].append((steps, keys, doors))
            return
        for d in DIRS:
            di, dj, c = i+d[0], j+d[1], grid[i+d[0]][j+d[1]]
            if c == "#" or (di, dj) in visited:
                continue
            visited.add((di, dj))
            next_keys = keys
            next_doors = doors
            if c.islower():
                next_keys |= 1 << (ord(c)-ord("a"))
            elif c.isupper():
                next_doors |= 1 << (ord(c)-ord("A"))
            backtrack(di, dj, source, target, steps+1,
                      next_keys, next_doors, visited)
            visited.remove((di, dj))

    keys = []
    starts = []
    for i, line in enumerate(grid):
        for j, c in enumerate(line):
            if c == "@":
                starts.append((i, j))
            elif c.islower():
                keys.append((c, i, j))

    # precompute distances between points of interest (starting position + keys)
    for idx, (c1, i, j) in enumerate(keys):
        for s, (start_row, start_col) in enumerate(starts, 1):
            backtrack(start_row, start_col, "@"+str(s), c1, 0, 0, 0, set())
        for (c2, _, _) in keys[idx+1:]:
            backtrack(i, j, c1, c2, 0, 1 << (ord(c1)-ord("a")), 0, set())

    for path in paths.values():
        for list in path.values():
            list.sort()

    TARGET = (1 << len(keys))-1

    @cache
    def dfs(positions, mask):
        if mask == TARGET:
            return 0

        res = float("inf")
        for p, c1 in enumerate(positions):
            for c2, list in paths[c1].items():
                if c2[0] == "@":
                    continue
                if 1 << index(c2) & mask > 0:
                    # key already picked up
                    continue
                for (steps, keys, doors) in list:
                    if mask | doors != mask:
                        # don't have the sufficient keys to open the required doors
                        continue
                    res = min(
                        res, steps+dfs(positions[0:p] + (c2,) + positions[p+1:], mask | keys))
                    break
        return res

    # takes about 0.8 seconds
    print(dfs(("@1", "@2", "@3", "@4"), 0))

    print(f"Elapsed time: {time.perf_counter()-start:.3f} seconds")
