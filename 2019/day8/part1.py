import time

if __name__ == "__main__":
    start = time.perf_counter()

    with open("input.txt") as f:
        pixels = [int(x) for x in f.readline()]

    resolution = 25*6
    layers = [[0, 0, 0] for _ in range(len(pixels)//resolution)]
    for i, p in enumerate(pixels):
        layers[i//resolution][p] += 1

    layers.sort(key=lambda l: l[0])
    print(layers[0][1]*layers[0][2])

    print(f"Elapsed time: {time.perf_counter()-start:.3f} seconds")
