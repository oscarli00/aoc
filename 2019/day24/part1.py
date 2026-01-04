import time

DIRS = [(-1, 0), (1, 0), (0, -1), (0, 1)]

if __name__ == "__main__":
    start = time.perf_counter()

    with open("input.txt") as f:
        grid = [list(line) for line in f.read().splitlines()]

    def calculate_rating(grid: list[list[str]]) -> int:
        res = 0
        idx = 0
        for i in range(len(grid)):
            for j in range(len(grid[0])):
                if grid[i][j] == "#":
                    res += 1 << idx
                idx += 1
        return res

    visited = set()
    visited.add(calculate_rating(grid))
    while True:
        next_grid = [["." for _ in range(len(grid[0]))]
                     for _ in range(len(grid))]
        for i in range(len(grid)):
            for j in range(len(grid[0])):
                count = 0
                for di, dj in DIRS:
                    i2, j2 = i+di, j+dj
                    if i2 >= 0 and i2 < len(grid) and j2 >= 0 and j2 < len(grid[0]) and grid[i2][j2] == "#":
                        count += 1
                if grid[i][j] == "#":
                    next_grid[i][j] = "#" if count == 1 else "."
                else:
                    next_grid[i][j] = "#" if count == 1 or count == 2 else "."
        rating = calculate_rating(next_grid)
        if rating in visited:
            print(rating)
            break
        visited.add(rating)
        grid = next_grid

    print(f"Elapsed time: {time.perf_counter()-start:.3f} seconds")
