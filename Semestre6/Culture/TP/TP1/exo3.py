import time

class FileVide(Exception):
    pass

class File:

    def __init__(self):
        self.file = []

    # def __repr__(self):
    #     return self.file

    def ajouter(self,nb):
        self.file.append(nb)

    def retirer(self):
        if (len(self.file) <= 0):
            raise FileVide("File vide")
        nb = self.file[len(self.file)-1]
        del(self.file[len(self.file)-1])
        print(nb)

def vider(file):
    try:
        while (True):
            file.retirer()
    except FileVide:
        print("")


l = File()
l.ajouter(5)
l.ajouter(5)
l.ajouter(5)
l.ajouter(5)
l.ajouter(5)
l.ajouter(5)
l.ajouter(5)
print(l.file)
vider(l)


    