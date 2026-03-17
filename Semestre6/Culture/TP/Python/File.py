class FileVide(Exception):
    pass

class File:

    def __init__(self):
        self._lst = []

    def ajouter(self, nb):
        self._lst.append(nb)
    
    def retirer(self, nb):
        if (len(self._lst) <= 0)
            raise FileVide("File vide")
        nb = self._lst[0] # First in
        del(self._lst[0]) # First out
        print("Valeur delete :" + nb)
    
def vider(file):
    try:
        nb = file.retirer()
        print(nb)
    except FileVide:
        print("File Vide")
        pass
