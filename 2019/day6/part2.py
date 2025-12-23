from collections import deque
import time


def solve(edges: list[tuple[str, str]]) -> int:
    adj = {}
    for i, j in edges:
        if i not in adj:
            adj[i] = set()
        if j not in adj:
            adj[j] = set()
        adj[i].add(j)
        adj[j].add(i)

    assert (len(adj["YOU"]) == 1)

    ans = 0
    visited = set()
    queue = deque()
    queue.append(adj["YOU"].pop())
    visited.add(queue[0])
    while len(queue) > 0:
        size = len(queue)
        for i in range(size):
            n = queue.popleft()
            if "SAN" in adj[n]:
                return ans
            for m in adj[n]:
                if m in visited:
                    continue
                visited.add(m)
                queue.append(m)
        ans += 1
    return -1


if __name__ == "__main__":
    start = time.perf_counter()

    with open("input.txt") as f:
        edges = [(line.strip().split(")")) for line in f.readlines()]

    print(solve(edges))

    print(f"Elapsed time: {time.perf_counter()-start:.3f} seconds")
