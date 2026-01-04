from collections import defaultdict, deque
import time

DIRS = [(-1, 0), (1, 0), (0, -1), (0, 1)]

if __name__ == "__main__":
    start_ms = time.perf_counter()

    with open("input.txt") as f:
        grid = [list(line) for line in f.read().splitlines()]

    teleporters = defaultdict(list)
    positions = {}
    for i in range(len(grid)-1):
        for j in range(len(grid[i])-1):
            c = grid[i][j]
            if c.isupper():
                if grid[i][j+1].isupper():
                    port = c+grid[i][j+1]
                    if j+2 < len(grid[i]) and grid[i][j+2] == ".":
                        teleporters[port].append((i, j+2))
                        positions[(i, j+2)] = port
                    elif j-1 >= 0 and grid[i][j-1] == ".":
                        teleporters[port].append((i, j-1))
                        positions[(i, j-1)] = port
                    else:
                        raise Exception()
                elif grid[i+1][j].isupper():
                    port = c+grid[i+1][j]
                    if i+2 < len(grid) and grid[i+2][j] == ".":
                        teleporters[port].append((i+2, j))
                        positions[(i+2, j)] = port
                    elif i-1 >= 0 and grid[i-1][j] == ".":
                        teleporters[port].append((i-1, j))
                        positions[(i-1, j)] = port
                    else:
                        raise Exception()

    # for kv in teleporters.items():
    #     print(kv)
    # print(positions)

    start = teleporters["AA"][0]
    end = teleporters["ZZ"][0]
    steps = 0
    queue = deque()
    visited = set()
    queue.append(start)
    visited.add(start)
    while len(queue) > 0:
        size = len(queue)
        for _ in range(size):
            i, j = queue.popleft()
            if (i, j) == end:
                print(steps)
                queue.clear()
                break
            for di, dj in DIRS:
                i2, j2 = i+di, j+dj
                if grid[i2][j2] == "#":
                    continue
                if grid[i2][j2] == "." and (i2, j2) not in visited:
                    queue.append((i2, j2))
                    visited.add((i2, j2))
                elif grid[i2][j2].isupper():
                    portal = positions[(i, j)]
                    for pos in teleporters[portal]:
                        if pos != (i, j) and pos not in visited:
                            queue.append(pos)
                            visited.add(pos)
        steps += 1

    print(f"Elapsed time: {time.perf_counter()-start_ms:.3f} seconds")
