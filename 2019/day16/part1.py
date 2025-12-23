PATTERN = [0, 1, 0, -1]

if __name__ == "__main__":
    with open("input.txt") as f:
        curr = [int(x) for x in f.read()]

    for _ in range(100):
        next = []
        for i, n in enumerate(curr):
            sum = 0
            for j, m in enumerate(curr):
                sum += m*PATTERN[(j+1)//(i+1) % 4]
            next.append(abs(sum) % 10)
        curr = next

    print("".join(str(i) for i in curr[:8]))
