if __name__ == "__main__":
    with open("input.txt") as f:
        wire1 = f.readline().split(",")
        wire2 = f.readline().split(",")

    horiz = []
    vert = []

    x = 0
    y = 0
    for path in wire1:
        dir = path[0]
        dist = int(path[1:])
        if dir == "U":
            vert.append((x, y, x, y+dist))
            y += dist
        elif dir == "R":
            horiz.append((x, y, x+dist, y))
            x += dist
        elif dir == "D":
            vert.append((x, y-dist, x, y))
            y -= dist
        elif dir == "L":
            horiz.append((x-dist, y, x, y))
            x -= dist

    x = 0
    y = 0
    ans = float("inf")
    for path in wire2:
        dir = path[0]
        dist = int(path[1:])
        if dir == "U":
            for h in horiz:
                if h[0] < x and x < h[2] and y < h[1] and h[1] < y+dist:
                    ans = min(ans, max(1, abs(x)+abs(h[1])))
            y += dist
        elif dir == "R":
            for v in vert:
                if v[1] < y and y < v[3] and x < v[0] and v[0] < x+dist:
                    ans = min(ans, max(1, abs(v[0])+abs(y)))
            x += dist
        elif dir == "D":
            for h in horiz:
                if h[0] < x and x < h[2] and y-dist < h[1] and h[1] < y:
                    ans = min(ans, max(1, abs(x)+abs(h[1])))
            y -= dist
        elif dir == "L":
            for v in vert:
                if v[1] < y and y < v[3] and x-dist < v[0] and v[0] < x:
                    ans = min(ans, max(1, abs(v[0])+abs(y)))
            x -= dist
    print(ans)
