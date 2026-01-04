import time

DIRS = [(-1, 0), (1, 0), (0, -1), (0, 1)]
LENGTH = 5


def calculate_edges(grid: list[list[str]]) -> tuple[int, int, int, int]:
    top, right,  bottom, left = 0, 0, 0, 0
    for k in range(5):
        top += 1 if grid[0][k] == "#" else 0
        right += 1 if grid[k][4] == "#" else 0
        bottom += 1 if grid[4][k] == "#" else 0
        left += 1 if grid[k][0] == "#" else 0
    return top, right, bottom, left


if __name__ == "__main__":
    start = time.perf_counter()

    grids = {}

    with open("input.txt") as f:
        grids[0] = [list(line) for line in f.read().splitlines()]

    min_layer = 0

    for _ in range(200):
        next_grids = {}

        # outermost layer
        layer = min_layer

        # check if we can create a new outer layer
        candidate_grid = [["." for _ in range(LENGTH)] for _ in range(LENGTH)]
        top, right, bottom, left = calculate_edges(grids[layer])
        success = False
        if top in (1, 2):
            candidate_grid[1][2] = "#"
            success = True
        if right in (1, 2):
            candidate_grid[2][3] = "#"
            success = True
        if bottom in (1, 2):
            candidate_grid[3][2] = "#"
            success = True
        if left in (1, 2):
            candidate_grid[2][1] = "#"
            success = True
        if success:
            min_layer -= 1
            next_grids[min_layer] = candidate_grid

        while layer in grids:
            grid = grids[layer]
            top, right, bottom, left = calculate_edges(
                grids[layer+1]) if layer+1 in grids else [0]*4
            next_grid = [["." for _ in range(LENGTH)]
                         for _ in range(LENGTH)]
            for i in range(LENGTH):
                for j in range(LENGTH):
                    if (i, j) == (2, 2):
                        continue
                    count = 0
                    for di, dj in DIRS:
                        i2, j2 = i+di, j+dj
                        if i2 < 0 and layer-1 in grids:
                            count += 1 if grids[layer-1][1][2] == "#" else 0
                        elif i2 == LENGTH and layer-1 in grids:
                            count += 1 if grids[layer-1][3][2] == "#" else 0
                        elif j2 < 0 and layer-1 in grids:
                            count += 1 if grids[layer-1][2][1] == "#" else 0
                        elif j2 == LENGTH and layer-1 in grids:
                            count += 1 if grids[layer-1][2][3] == "#" else 0
                        elif (i2, j2) == (2, 2):
                            if (i, j) == (1, 2):
                                count += top
                            elif (i, j) == (2, 3):
                                count += right
                            elif (i, j) == (3, 2):
                                count += bottom
                            elif (i, j) == (2, 1):
                                count += left
                        elif i2 >= 0 and i2 < LENGTH and j2 >= 0 and j2 < LENGTH and grid[i2][j2] == "#":
                            count += 1
                    if grid[i][j] == "#":
                        next_grid[i][j] = "#" if count == 1 else "."
                    else:
                        next_grid[i][j] = "#" if count == 1 or count == 2 else "."
            next_grids[layer] = next_grid
            layer += 1

        # check if we can create a new inner layer
        grid = grids[layer-1]
        candidate_grid = [["." for _ in range(LENGTH)] for _ in range(LENGTH)]
        success = False
        if grid[1][2] == "#":
            for j in range(5):
                candidate_grid[0][j] = "#"
            success = True
        if grid[2][3] == "#":
            for i in range(5):
                candidate_grid[i][4] = "#"
            success = True
        if grid[3][2] == "#":
            for j in range(5):
                candidate_grid[4][j] = "#"
            success = True
        if grid[2][1] == "#":
            for i in range(5):
                candidate_grid[i][0] = "#"
            success = True
        if success:
            next_grids[layer] = candidate_grid

        grids = next_grids

    ans = 0
    for grid in grids.values():
        for i in range(LENGTH):
            for j in range(LENGTH):
                if grid[i][j] == "#":
                    ans += 1
    print(ans)

    print(f"Elapsed time: {time.perf_counter()-start:.3f} seconds")
