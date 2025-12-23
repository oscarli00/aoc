from collections import defaultdict, deque
import math


if __name__ == "__main__":
    adj = defaultdict(lambda: defaultdict(list))
    in_degree = defaultdict(int)
    with open("input.txt") as f:
        for line in f.read().splitlines():
            l, r = line.split(" => ")
            output_q, output_id = r.split(" ")
            inputs = [input.split(" ") for input in l.split(", ")]
            for q, id in inputs:
                adj[output_id][int(output_q)].append((id, int(q)))
                in_degree[id] += 1

    quantity_required = defaultdict(int)
    quantity_required["FUEL"] = 1
    queue = deque()
    queue.append("FUEL")
    while len(queue) > 0:
        id = queue.popleft()
        if id == "ORE":
            break
        q_to_inputs = adj[id]
        assert len(q_to_inputs) == 1
        # sub_q = quantity of id produced given the inputs quantities
        sub_q, inputs = list(q_to_inputs.items())[0]
        multiplier = math.ceil(quantity_required[id]/sub_q)
        for input_id, input_q in inputs:
            quantity_required[input_id] += input_q*multiplier
            in_degree[input_id] -= 1
            if in_degree[input_id] == 0:
                queue.append(input_id)

    print(quantity_required["ORE"])
