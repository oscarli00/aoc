import math
import time

if __name__ == "__main__":
    start = time.perf_counter()

    with open("input.txt") as f:
        grid = f.read().splitlines()

    row, col = 29, 26

    points = []
    for r in range(len(grid)):
        for c in range(len(grid[0])):
            if r == row and c == col:
                continue
            if grid[r][c] == '#':
                if r < row and c >= col:
                    angle = math.atan((c-col)/(row-r))
                elif r >= row and c > col:
                    angle = math.pi/2 + math.atan((r-row)/(c-col))
                elif r > row and c <= col:
                    angle = math.pi + math.atan((col-c)/(r-row))
                elif r <= row and c < col:
                    angle = 3*math.pi/2 + math.atan((row-r)/(col-c))
                points.append((angle, (row-r)**2 + (col-c)**2, r, c))

    points.sort(key=lambda x: (x[0], x[1]))

    rem = 200
    while rem > 0:
        next = []
        for i, p in enumerate(points):
            if i > 0 and p[0] == points[i-1][0]:
                next.append(p)
                continue
            rem -= 1
            if rem == 0:
                print(p[3]*100+p[2])
                break
        points = next

    print(f"Elapsed time: {time.perf_counter()-start:.3f} seconds")
