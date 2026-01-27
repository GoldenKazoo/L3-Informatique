class Iter:
    def __init__(self, mirror):
        self.mirror = mirror
        self.index = len(mirror) - 1
    
    def __next__(self):
        if (self.index < 0):
            raise StopIteration()
        else:
            tmp = self.mirror[self.index]
            self.index = self.index - 1
            return tmp


class Miroir:


    def __init__(self, mot):
        self.mot = mot
    
    def __repr__(self):
        return ("A l'envers ca donne " + self.mot)
    
    def __iter__(self):
        return Iter(self.mot)
    

m = Miroir ("bar")
for c in m:
    print(c)
