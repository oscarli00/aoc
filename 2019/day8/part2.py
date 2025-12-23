import time

if __name__ == "__main__":
    start = time.perf_counter()

    with open("input.txt") as f:
        pixels = [int(x) for x in f.readline()]

    cols = 25
    rows = 6

    image = [[None for _ in range(cols)] for _ in range(rows)]

    for i, p in enumerate(pixels):
        offset = i % (25*6)
        row = offset//25
        col = offset % 25
        if image[row][col] is None and p != 2:
            image[row][col] = "X" if p == 1 else "."

    for row in image:
        print(row)

    print(f"Elapsed time: {time.perf_counter()-start:.3f} seconds")
