if __name__ == "__main__":
    with open("input.txt") as f:
        ints = [int(x) for x in f.readline().split(",")]
    ints[1] = 12
    ints[2] = 2
    i = 0
    while i < len(ints):
        opcode = ints[i]
        if opcode == 99:
            break
        i1, i2, o = ints[i+1], ints[i+2], ints[i+3]
        if opcode == 1:
            ints[o] = ints[i1]+ints[i2]
        elif opcode == 2:
            ints[o] = ints[i1]*ints[i2]
        i += 4
    print(ints[0])
