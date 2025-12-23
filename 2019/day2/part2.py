if __name__ == "__main__":
    with open("input.txt") as f:
        input = [int(x) for x in f.readline().split(",")]
    for noun in range(100):
        for verb in range(100):
            ints = input.copy()
            ints[1] = noun
            ints[2] = verb
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
            if ints[0] == 19690720:
                print(100*noun + verb)
