if __name__ == "__main__":
    moons = []
    with open("input.txt") as f:
        lines = f.read().splitlines()
        for line in lines:
            x, y, z = [int(a[2:]) for a in line[1:-1].split(", ")]
            moons.append((x, y, z, 0, 0, 0))

    for _ in range(1000):
        next = []
        for i, (x, y, z, dx, dy, dz) in enumerate(moons):
            for j, (x1, y1, z1, _, _, _) in enumerate(moons):
                if i == j:
                    continue
                dx += max(-1, min(1, x1-x))
                dy += max(-1, min(1, y1-y))
                dz += max(-1, min(1, z1-z))
            x += dx
            y += dy
            z += dz
            next.append((x, y, z, dx, dy, dz))
        moons = next

    energy = 0
    for (x, y, z, dx, dy, dz) in moons:
        pot = abs(x)+abs(y)+abs(z)
        kin = abs(dx)+abs(dy)+abs(dz)
        energy += pot*kin

    print(energy)
