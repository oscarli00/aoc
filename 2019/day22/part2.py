import time

if __name__ == "__main__":
    start = time.perf_counter()

    with open("input.txt") as f:
        lines = f.read().splitlines()

    SIZE = 119315717514047

    def shuffle(pos):
        for line in lines:
            if line == "deal into new stack":
                pos = SIZE-pos-1
            elif line.startswith("cut"):
                n = int(line[4:])
                pos = (pos-n) % SIZE
            elif line.startswith("deal with increment"):
                inc = int(line[line.index("t ")+2:])
                pos = pos*inc % SIZE
            else:
                raise Exception()
        return pos

    # A shuffle can be represented by applying a*starting_pos+b (mod SIZE)
    # a and b is what we want to calculate

    # pos = a*0 + b
    b = shuffle(0)
    pos = shuffle(1)
    # pos = a*1 + b (mod SIZE)
    a = (pos-b) % SIZE

    # 1 shuffle is equivalent to a*pos+b (mod SIZE)
    # 2 shuffles is equivalent to a*(a*pos+b)+b (mod SIZE)
    # n shuffles is equivalent to a**n * pos + (a**0)*b + (a**1)*b + (a**2)*b + ... + (a**n-1)*b
    # a_n = a**n mod SIZE
    # b_n = b*(a**n-1)/(a-1) mod SIZE  (sum of geometric series formula)
    # modular inverse of (a-1) = (a-1)**(SIZE-2) mod SIZE (fermat’s little theorem)
    n = 101741582076661
    exp = pow(a, n, SIZE)
    mod_inv = pow(a-1, SIZE-2, SIZE)
    a_n = exp
    b_n = b*(exp-1)*mod_inv % SIZE

    # 2020 = a_n * ans + b_n mod SIZE
    # ans = (2020-b_n)*a_n^-1 mod SIZE
    a_n_inv = pow(a_n, SIZE-2, SIZE)
    ans = (2020-b_n)*a_n_inv % SIZE
    assert (a_n*ans + b_n) % SIZE == 2020
    print(ans)

    print(f"Elapsed time: {time.perf_counter()-start:.3f} seconds")
