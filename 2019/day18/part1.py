from collections import deque
from functools import cache
import time

DIRS = [(-1, 0), (1, 0), (0, -1), (0, 1)]

if __name__ == "__main__":
    start = time.perf_counter()

    with open("input.txt") as f:
        grid = [list(line) for line in f.read().splitlines()]

    keys = []
    for i, line in enumerate(grid):
        for j, c in enumerate(line):
            if c == "@":
                start_row, start_col = i, j
            elif "a" <= c and c <= "z":
                keys.append(c)
    target = (1 << len(keys))-1

    def index(c):
        if c >= "a" and c <= "z":
            return ord(c)-ord("a")
        if c >= "A" and c <= "Z":
            return ord(c)-ord("A")
        raise Exception()

    @cache
    def dfs(row, col, mask):
        if mask == target:
            return 0
        queue = deque()
        visited = set()
        dist = {}
        queue.append((row, col))
        visited.add((row, col))
        curr = 0
        while len(queue) > 0:
            size = len(queue)
            for _ in range(size):
                i, j = queue.popleft()
                c1 = grid[i][j]
                if c1 >= "a" and c1 <= "z" and 1 << index(c1) & mask == 0:
                    dist[c1] = (curr, i, j)
                for d in DIRS:
                    di, dj, c2 = i+d[0], j+d[1], grid[i+d[0]][j+d[1]]
                    if (di, dj) in visited:
                        continue
                    visited.add((di, dj))
                    if c2 != "#" and (c2 < "A" or c2 > "Z" or 1 << index(c2) & mask > 0):
                        queue.append((di, dj))
            curr += 1

        res = float("inf")
        for key, (steps, i, j) in dist.items():
            res = min(res, steps+dfs(i, j, mask | 1 << (ord(key)-ord("a"))))
        return res

    # takes about 3 minutes
    print(dfs(start_row, start_col, 0))

    print(f"Elapsed time: {time.perf_counter()-start:.3f} seconds")
