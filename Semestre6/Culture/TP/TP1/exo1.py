def mirror(mot):
    new = ""
    for i in range(len(mot)):
        new = new + mot[len(mot) - i - 1]
    return new

print(mirror("Bonjour"))
    