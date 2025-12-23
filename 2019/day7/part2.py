from enum import Enum
from itertools import permutations


class ExitCode(Enum):
    HALT = 1
    WAITING = 2


class Computer:
    def __init__(self, program: list[int], inputs: list[int]):
        self.program = program
        self.i = 0
        self.inputs = inputs

    def run(self):
        while self.i < len(self.program):
            instruction = str(self.program[self.i]).rjust(5, "0")
            opcode = int(instruction[-2:])
            if opcode == 99:
                break

            m3, m2, m1 = [int(x) for x in instruction[:-2]]

            p1 = self.program[self.i+1]
            p2 = self.program[self.i+2] if self.i + \
                2 < len(self.program) else None
            p3 = self.program[self.i+3] if self.i + \
                3 < len(self.program) else None

            if opcode == 1:
                v1 = self.read_value(p1, m1)
                v2 = self.read_value(p2, m2)
                self.program[p3] = v1+v2
                self.i += 4
            elif opcode == 2:
                v1 = self.read_value(p1, m1)
                v2 = self.read_value(p2, m2)
                self.program[p3] = v1*v2
                self.i += 4
            elif opcode == 3:
                if len(self.inputs) == 0:
                    print("shouldn't happen")
                    return ExitCode.WAITING
                n = self.inputs.pop(0)
                self.program[p1] = n
                self.i += 2
            elif opcode == 4:
                v1 = self.read_value(p1, m1)
                self.i += 2
                return v1
            elif opcode == 5:
                v1 = self.read_value(p1, m1)
                v2 = self.read_value(p2, m2)
                self.i = v2 if v1 != 0 else self.i+3
            elif opcode == 6:
                v1 = self.read_value(p1, m1)
                v2 = self.read_value(p2, m2)
                self.i = v2 if v1 == 0 else self.i+3
            elif opcode == 7:
                v1 = self.read_value(p1, m1)
                v2 = self.read_value(p2, m2)
                self.program[p3] = 1 if v1 < v2 else 0
                self.i += 4
            elif opcode == 8:
                v1 = self.read_value(p1, m1)
                v2 = self.read_value(p2, m2)
                self.program[p3] = 1 if v1 == v2 else 0
                self.i += 4
            else:
                raise Exception("invalid opcode: ", opcode)
        return ExitCode.HALT

    def read_value(self, param: int, mode: int):
        return param if mode else self.program[param]

    def input(self, inputs: list[int]):
        self.inputs += inputs


if __name__ == "__main__":
    with open("input.txt") as f:
        program = [int(x) for x in f.readline().split(",")]

    ans = 0
    for permutation in permutations(range(5, 10)):
        computers = [Computer(program.copy(), [phase])
                     for phase in permutation]
        halted = False
        res = 0
        while not halted:
            for i, phase in enumerate(permutation):
                cpu = computers[i]
                cpu.input([res])
                res = cpu.run()
                if res == ExitCode.HALT:
                    halted = True
                    break
                if i == 4:
                    ans = max(ans, res)
    print(ans)
