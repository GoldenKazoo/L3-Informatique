def mirror(mot):
    new = ""
    for i in range(len(mot)):
        new = new + mot[len(mot) - i - 1]
    return new

def mirror_neg(mot):
    new = ""
    for i in range(len(mot) - 1, -1, -1):
        new = new + mot[i]
    return new

def entrelacement(l1,l2):
    resultat = []
    min_len = min(len(l1), len(l2))

    for i in range (min_len):
        resultat.append(l1[i])
        resultat.append(l2[i])
    
    resultat = resultat + (l1[min_len:])
    resultat = resultat + (l2[min_len:])
    
    return resultat

# print(mirror("Bonjour"))
# print(mirror_neg("Bonjour"))
# print(entrelacement([1,2,3],[4,5,6,7,8]))