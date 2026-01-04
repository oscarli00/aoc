import time

if __name__ == "__main__":
    start = time.perf_counter()

    with open("input.txt") as f:
        lines = f.read().splitlines()

    deck = [i for i in range(10007)]
    for line in lines:
        if line == "deal into new stack":
            deck.reverse()
        elif line.startswith("cut"):
            n = int(line[4:])
            deck = deck[n:] + deck[:n]
        elif line.startswith("deal with increment"):
            inc = int(line[line.index("t ")+2:])
            pos = 0
            next = deck.copy()
            for card in deck:
                next[pos] = card
                pos = (pos+inc) % len(deck)
            deck = next

    print(deck.index(2019))

    print(f"Elapsed time: {time.perf_counter()-start:.3f} seconds")
