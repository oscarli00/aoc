if __name__ == "__main__":
    with open("input.txt") as f:
        wire1 = f.readline().split(",")
        wire2 = f.readline().split(",")

    horiz = []
    vert = []

    x = 0
    y = 0
    steps = 0
    for path in wire1:
        dir = path[0]
        dist = int(path[1:])
        if dir == "U":
            vert.append((x, y, x, y+dist, steps, "U"))
            y += dist
        elif dir == "R":
            horiz.append((x, y, x+dist, y, steps, "R"))
            x += dist
        elif dir == "D":
            vert.append((x, y-dist, x, y, steps, "D"))
            y -= dist
        elif dir == "L":
            horiz.append((x-dist, y, x, y, steps, "L"))
            x -= dist
        steps += dist

    x = 0
    y = 0
    steps = 0
    ans = float("inf")
    for path in wire2:
        dir = path[0]
        dist = int(path[1:])
        if dir == "U":
            for h in horiz:
                if h[0] < x and x < h[2] and y < h[1] and h[1] < y+dist:
                    steps2 = steps + abs(h[1]-y)
                    steps1 = h[4] + (abs(x-h[0]) if h[5] ==
                                     "R" else abs(h[2]-x))
                    ans = min(ans, steps1+steps2)
            y += dist
        elif dir == "R":
            for v in vert:
                if v[1] < y and y < v[3] and x < v[0] and v[0] < x+dist:
                    steps2 = steps + abs(v[0]-x)
                    steps1 = v[4] + (abs(y-v[1]) if v[5] ==
                                     "U" else abs(v[3]-y))
                    ans = min(ans, steps1+steps2)
            x += dist
        elif dir == "D":
            for h in horiz:
                if h[0] < x and x < h[2] and y-dist < h[1] and h[1] < y:
                    steps2 = steps + abs(y-h[1])
                    steps1 = h[4] + (abs(x-h[0]) if h[5] ==
                                     "R" else abs(h[2]-x))
                    ans = min(ans, steps1+steps2)
            y -= dist
        elif dir == "L":
            for v in vert:
                if v[1] < y and y < v[3] and x-dist < v[0] and v[0] < x:
                    steps2 = steps + abs(x-v[0])
                    steps1 = v[4] + (abs(v[1]-y) if v[5] ==
                                     "U" else abs(v[3]-y))
                    ans = min(ans, steps1+steps2)
            x -= dist
        steps += dist
    print(ans)
