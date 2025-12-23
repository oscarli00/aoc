from itertools import permutations


class Computer:
    def __init__(self, program: list[int]):
        self.program = program

    def run(self, inputs):
        i = 0
        while i < len(self.program):
            instruction = str(self.program[i]).rjust(5, "0")
            opcode = int(instruction[-2:])
            if opcode == 99:
                break

            m3, m2, m1 = [int(x) for x in instruction[:-2]]

            p1 = self.program[i+1]
            p2 = self.program[i+2] if i+2 < len(self.program) else 0
            p3 = self.program[i+3] if i+3 < len(self.program) else 0

            if opcode == 1:
                v1 = p1 if m1 else self.program[p1]
                v2 = p2 if m2 else self.program[p2]
                self.program[p3] = v1+v2
                i += 4
            elif opcode == 2:
                v1 = p1 if m1 else self.program[p1]
                v2 = p2 if m2 else self.program[p2]
                self.program[p3] = v1*v2
                i += 4
            elif opcode == 3:
                n = inputs.pop(0)
                self.program[p1] = n
                i += 2
            elif opcode == 4:
                v1 = p1 if m1 else self.program[p1]
                return v1
                # print(v1)
                # i += 2
            elif opcode == 5:
                v1 = p1 if m1 else self.program[p1]
                v2 = p2 if m2 else self.program[p2]
                i = v2 if v1 != 0 else i+3
            elif opcode == 6:
                v1 = p1 if m1 else self.program[p1]
                v2 = p2 if m2 else self.program[p2]
                i = v2 if v1 == 0 else i+3
            elif opcode == 7:
                v1 = p1 if m1 else self.program[p1]
                v2 = p2 if m2 else self.program[p2]
                self.program[p3] = 1 if v1 < v2 else 0
                i += 4
            elif opcode == 8:
                v1 = p1 if m1 else self.program[p1]
                v2 = p2 if m2 else self.program[p2]
                self.program[p3] = 1 if v1 == v2 else 0
                i += 4
            else:
                raise Exception("invalid opcode: ", opcode)


if __name__ == "__main__":
    with open("input.txt") as f:
        program = [int(x) for x in f.readline().split(",")]

    ans = 0
    for p in permutations(range(5)):
        res = 0
        for phase in p:
            cpu = Computer(program.copy())
            res = cpu.run([phase, res])
        ans = max(ans, res)
    print(ans)
