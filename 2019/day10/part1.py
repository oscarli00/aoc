import time


def gcd(a, b):
    while b:
        a, b = b, a % b
    return a


if __name__ == "__main__":
    start = time.perf_counter()

    with open("input.txt") as f:
        grid = f.read().splitlines()

    ans = 0
    for row in range(len(grid)):
        for col in range(len(grid[0])):
            if grid[row][col] != '#':
                continue
            count = 0
            for di in range(-row, len(grid)-row):
                for dj in range(-col, len(grid[0])-col):
                    if (di == 0 and dj == 0) or gcd(abs(di), abs(dj)) != 1:
                        continue
                    r = row+di
                    c = col+dj
                    while r >= 0 and r < len(grid) and c >= 0 and c < len(grid[0]):
                        if grid[r][c] == "#":
                            count += 1
                            break
                        r += di
                        c += dj
            if count > ans:
                ans = count
                station = (row, col)
    print("ans: ", ans)
    print("station: ", station)
    print(f"Elapsed time: {time.perf_counter()-start:.3f} seconds")
