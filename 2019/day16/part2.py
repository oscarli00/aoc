import time

if __name__ == "__main__":
    start = time.perf_counter()

    with open("input.txt") as f:
        input = f.read()*10000

    offset_start = int(input[:7])
    assert offset_start >= len(input)/2
    # the trick is that the offset is always nicely in the second half of the array
    # which allows the simplification below

    # To calculate the next values for the second half of the
    # curr array is just the suffix sum of the array
    # i.e. next value for digit i, where i>len(curr)/2, is equal to sum(curr[i:])%10
    curr = [int(x) for x in input[offset_start:]]
    for _ in range(100):
        sum = 0
        for i, n in reversed(list(enumerate(curr))):
            sum += n
            curr[i] = sum % 10

    print("".join(str(i) for i in curr[:8]))

    print(f"Elapsed time: {time.perf_counter()-start:.3f} seconds")
