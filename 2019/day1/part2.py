if __name__ == "__main__":
    with open("input.txt") as f:
        modules = [int(x) for x in f.readlines()]
    sum = 0
    for x in modules:
        fuel = x//3-2
        while fuel > 0:
            sum += fuel
            fuel = fuel//3-2
    print(sum)
