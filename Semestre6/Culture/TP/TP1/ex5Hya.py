class Entrelacement:
    def __init__(self, i1, i2):
        self.i1 = i1
        self.i2 = i2
    
    def __iter__(self):
        return EntrelacementIter(self.i1, self.i2)


class EntrelacementIter:
    def __init__(self, i1, i2):
        self.iter1 = iter(i1)  # on crée des itérateurs à partir des itérables
        self.iter2 = iter(i2)
        self.tour = 1  # 1 = tour de i1, 2 = tour de i2
        self.fini1 = False  # i1 est épuisé ?
        self.fini2 = False  # i2 est épuisé ?
    
    def __next__(self):
        # Si les deux sont épuisés, on arrête
        if self.fini1 and self.fini2:
            raise StopIteration
        
        # Tour de i1
        if self.tour == 1:
            if not self.fini1:
                try:
                    elt = next(self.iter1)
                    self.tour = 2  # prochain tour = i2
                    return elt
                except StopIteration:
                    self.fini1 = True
            # Si i1 est fini, on passe à i2
            self.tour = 2
            return self.__next__()
        
        # Tour de i2
        else:
            if not self.fini2:
                try:
                    elt = next(self.iter2)
                    self.tour = 1  # prochain tour = i1
                    return elt
                except StopIteration:
                    self.fini2 = True
            # Si i2 est fini, on passe à i1
            self.tour = 1
            return self.__next__()


# Test
e = Entrelacement("Python", [1, 2, 3])
for x in e:
    print(x)
# Affiche : P 1 y 2 t 3 h o n