from collections import defaultdict, deque
from enum import Enum
from typing import Union


class ExitCode(Enum):
    HALT = 1
    WAITING = 2


class Computer:
    def __init__(self, program: list[int], inputs: list[int]):
        self.program = defaultdict(int)
        for i, n in enumerate(program):
            self.program[i] = n
        self.i = 0
        self.inputs = inputs
        self.relative_base = 0

    def run(self) -> Union[ExitCode, int]:
        while self.i in self.program:
            instruction = str(self.program[self.i]).rjust(5, "0")
            opcode = int(instruction[-2:])
            if opcode == 99:
                break

            m3, m2, m1 = [int(x) for x in instruction[:-2]]
            if opcode == 1:
                v1 = self.read_value(self.program[self.i+1], m1)
                v2 = self.read_value(self.program[self.i+2], m2)
                p3 = self.read_write_param(self.program[self.i+3], m3)
                self.program[p3] = v1+v2
                self.i += 4
            elif opcode == 2:
                v1 = self.read_value(self.program[self.i+1], m1)
                v2 = self.read_value(self.program[self.i+2], m2)
                p3 = self.read_write_param(self.program[self.i+3], m3)
                self.program[p3] = v1*v2
                self.i += 4
            elif opcode == 3:
                if len(self.inputs) == 0:
                    return ExitCode.WAITING
                n = self.inputs.pop(0)
                p1 = self.read_write_param(self.program[self.i+1], m1)
                self.program[p1] = n
                self.i += 2
            elif opcode == 4:
                v1 = self.read_value(self.program[self.i+1], m1)
                self.i += 2
                return v1
            elif opcode == 5:
                v1 = self.read_value(self.program[self.i+1], m1)
                v2 = self.read_value(self.program[self.i+2], m2)
                self.i = v2 if v1 != 0 else self.i+3
            elif opcode == 6:
                v1 = self.read_value(self.program[self.i+1], m1)
                v2 = self.read_value(self.program[self.i+2], m2)
                self.i = v2 if v1 == 0 else self.i+3
            elif opcode == 7:
                v1 = self.read_value(self.program[self.i+1], m1)
                v2 = self.read_value(self.program[self.i+2], m2)
                p3 = self.read_write_param(self.program[self.i+3], m3)
                self.program[p3] = 1 if v1 < v2 else 0
                self.i += 4
            elif opcode == 8:
                v1 = self.read_value(self.program[self.i+1], m1)
                v2 = self.read_value(self.program[self.i+2], m2)
                p3 = self.read_write_param(self.program[self.i+3], m3)
                self.program[p3] = 1 if v1 == v2 else 0
                self.i += 4
            elif opcode == 9:
                v1 = self.read_value(self.program[self.i+1], m1)
                self.relative_base += v1
                self.i += 2
            else:
                raise Exception("invalid opcode: ", opcode)
        return ExitCode.HALT

    def read_value(self, param: int, mode: int) -> int:
        if mode == 0:  # position
            return self.program[param]
        if mode == 1:  # immediate
            return param
        if mode == 2:  # relative
            return self.program[param+self.relative_base]

    def read_write_param(self, param: int, mode: int) -> int:
        assert mode != 1
        if mode == 0:
            return param
        if mode == 2:
            return param + self.relative_base

    def input(self, inputs: list[int]) -> None:
        self.inputs += inputs


DIRS = [(-1, 0), (1, 0), (0, -1), (0, 1)]

if __name__ == "__main__":
    with open("input.txt") as f:
        program = [int(x) for x in f.readline().split(",")]

    cpu = Computer(program.copy(), [])
    tiles = defaultdict(int)
    visited = set()

    def backtrack(i, j):
        for idx, d in enumerate(DIRS, 1):
            di, dj = i+d[0], j+d[1]
            if (di, dj) in visited:
                continue
            visited.add((di, dj))
            cpu.input([idx])
            output = cpu.run()
            tiles[(di, dj)] = output
            if output > 0:
                backtrack(di, dj)
                cpu.input([idx+1 if idx % 2 == 1 else idx-1])
                cpu.run()

    tiles[(0, 0)] = 0
    visited.add((0, 0))
    backtrack(0, 0)

    queue = deque()
    visited.clear()
    queue.append((0, 0))
    visited.add((0, 0))
    dist = 0
    while len(queue) > 0:
        dist += 1
        size = len(queue)
        for _ in range(size):
            i, j = queue.popleft()
            found = False
            for d in DIRS:
                di, dj = i+d[0], j+d[1]
                if (di, dj) in visited:
                    continue
                visited.add((di, dj))
                if tiles[(di, dj)] == 2:
                    print("dist: ", dist)
                    print("pos: ", (di, dj))
                    found = True
                    break
                elif tiles[(di, dj)] == 1:
                    queue.append((di, dj))
            if found:
                break
        if found:
            break
