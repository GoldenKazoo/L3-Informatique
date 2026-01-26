from bs4 import BeautifulSoup
import requests

kl = open("King's_Landing.html", "r", encoding="utf-8")
klsoup = BeautifulSoup(kl, "html.parser")


def titre(soup):
    if soup.title is not None:
        return soup.title.string
    return None

def afficher_h2(soup):
    for h2 in soup.find_all("h2"):
        print(h2.get_text())

def afficher_h1(soup):
    for h1 in soup.find_all("h1"):
        print(h1.get_text())

def nb_par_avec_lien(soup):
    count = 0
    for p in soup.find_all("p"):
        if p.find("a") is not None:
            count += 1
    return count


# print(afficher_h2(klsoup))
print(nb_par_avec_lien(klsoup))


# Sur le wiki Fandom (Petyr Baelish), le contenu principal est dans une balise :
# <div class="mw-parser-output">

# Donc on ciblera :
# contenu = soup.find("div", class_="mw-parser-output")

def liens(page):
    html = requests.get(page).text
    soup = BeautifulSoup(html, "html.parser")

    contenu = soup.find("div", class_="mw-parser-output")
    resultat = set()

    if contenu is None:
        return resultat

    for a in contenu.find_all("a", href=True):
        href = a["href"]
        if href.startswith("/wiki/"):
            url = "https://iceandfire.fandom.com" + href
            resultat.add(url)

    return resultat




def liens_distance(page, d):
    visites = set([page])
    courant = set([page])

    for _ in range(d):
        suivant = set()
        for p in courant:
            try:
                voisins = liens(p)
                for v in voisins:
                    if v not in visites:
                        visites.add(v)
                        suivant.add(v)
            except:
                pass
        courant = suivant

    visites.remove(page)
    return visites


page = "https://iceandfire.fandom.com/wiki/Petyr_Baelish"
L = liens(page)
print(len(L))
print(list(L)[:10])


res = liens_distance("https://iceandfire.fandom.com/wiki/Petyr_Baelish", 1)
print(len(res))
