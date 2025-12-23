if __name__ == "__main__":
    ans = 0
    for x in range(372037, 905158):
        xString = str(x)
        adj = False
        increasing = True
        for i, c in enumerate(xString):
            if i > 0 and c == xString[i-1]:
                adj = True
            if i > 0 and c < xString[i-1]:
                increasing = False
        if adj and increasing:
            ans += 1
    print(ans)
