class Iter:

    def __init__(self, mot):
        self._mot = mot
        self._index = len(mot) -1 
    
    def __next__(self):
        if (self._index < 0):
            raise StopIteration
        else:
            tmp = self._mot[self._index]
            self._index = self._index - 1
        return tmp

class Mirroir:

    def __init__(self, mot):
        self._mot = mot
    
    def __repr__(self):
        return ("A l'envers ca donne :" + self._mot)
    
    def __iter__(self):
        return Iter(self._mot)

m = Mirroir("bar")
for c in m:
    print(c)