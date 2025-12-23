from functools import cache
import time

if __name__ == "__main__":
    start = time.perf_counter()

    with open("input.txt") as f:
        edges = [(line.strip().split(")")) for line in f.readlines()]

    adj = {}
    for i, j in edges:
        if i not in adj:
            adj[i] = []
        if j not in adj:
            adj[j] = []
        adj[i].append(j)

    @cache
    def dfs(o: str) -> int:
        res = 0
        for neighbour in adj[o]:
            res += 1+dfs(neighbour)
        return res

    ans = 0
    for o in adj:
        ans += dfs(o)
    print(ans)

    print(f"Elapsed time: {time.perf_counter()-start:.3f} seconds")
