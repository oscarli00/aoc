if __name__ == "__main__":
    with open("input.txt") as f:
        modules = [int(x) for x in f.readlines()]
    sum = 0
    for x in modules:
        sum += x//3-2
    print(sum)
