def entrelacement(l1, l2):
    l1_size = len(l1)
    l2_size = len(l2)
    max = 0

    if l1_size < l2_size:
        min = l1_size
    else:
        min = l2_size
    l3 = []

    for i in range(min):
        if (l1[i] != ''):
            l3.append(l1[i])
        if (l1[i] != ''):
            l3.append(l2[i])
    
    if (l1_size < l2_size):
        l3 = l3 + l2[i+1:l2_size]
    else:
        l3 = l3+ l1[i+1:l1_size]

    print(l3)


entrelacement([1,2,3],[4,5,6,7,8])    