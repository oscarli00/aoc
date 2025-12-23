import time


def lcm(a, b):
    return a*b/gcd(a, b)


def gcd(a, b):
    while b:
        a, b = b, a % b
    return a


def cycle_steps(initial):
    curr = initial.copy()
    steps = 0
    while True:
        steps += 1
        next = []
        for i, (x, dx) in enumerate(curr):
            for j, (x1, _) in enumerate(curr):
                if i == j:
                    continue
                dx += max(-1, min(1, x1-x))
            x += dx
            next.append((x, dx))
        curr = next
        if (curr == initial):
            break
    return steps


# calculate how many steps until x returns to original position
# calculate how many steps until y returns to original position
# calculate how many steps until z returns to original position
# ans = lowest common multiple of the above
if __name__ == "__main__":
    start = time.perf_counter()

    initial_x, initial_y, initial_z = [], [], []
    with open("input.txt") as f:
        lines = f.read().splitlines()
        for line in lines:
            x, y, z = [int(a[2:]) for a in line[1:-1].split(", ")]
            initial_x.append((x, 0))
            initial_y.append((y, 0))
            initial_z.append((z, 0))

    x_steps = cycle_steps(initial_x)
    y_steps = cycle_steps(initial_y)
    z_steps = cycle_steps(initial_z)

    print(lcm(x_steps, lcm(y_steps, z_steps)))

    print(f"Elapsed time: {time.perf_counter()-start:.3f} seconds")
