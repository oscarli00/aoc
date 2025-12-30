from collections import deque
from functools import cache
import time

DIRS = [(-1, 0), (1, 0), (0, -1), (0, 1)]

if __name__ == "__main__":
    start = time.perf_counter()

    with open("input2.txt") as f:
        grid = [list(line) for line in f.read().splitlines()]

    keys = []
    starts = []
    for i, line in enumerate(grid):
        for j, c in enumerate(line):
            if c == "@":
                starts.append((i, j))
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
    def dfs(positions, mask):
        if mask == target:
            return 0

        dist = {}
        for p, (row, col) in enumerate(positions):
            queue = deque()
            visited = set()
            queue.append((row, col))
            visited.add((row, col))
            curr = 0
            while len(queue) > 0:
                size = len(queue)
                for _ in range(size):
                    i, j = queue.popleft()
                    c1 = grid[i][j]
                    if c1 >= "a" and c1 <= "z" and 1 << index(c1) & mask == 0:
                        next_pos = positions[0:p] + ((i, j),) + positions[p+1:]
                        dist[c1] = (curr, next_pos)
                    for d in DIRS:
                        di, dj, c2 = i+d[0], j+d[1], grid[i+d[0]][j+d[1]]
                        if (di, dj) in visited:
                            continue
                        visited.add((di, dj))
                        if c2 != "#" and (c2 < "A" or c2 > "Z" or 1 << index(c2) & mask > 0):
                            queue.append((di, dj))
                curr += 1

        res = float("inf")
        for key, (steps, pos) in dist.items():
            res = min(res, steps+dfs(pos, mask | 1 << (ord(key)-ord("a"))))
        return res

    # takes about 25 minutes
    print(dfs(tuple(starts), 0))

    print(f"Elapsed time: {time.perf_counter()-start:.3f} seconds")
