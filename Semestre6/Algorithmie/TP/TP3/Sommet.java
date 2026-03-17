import java.util.ArrayList;

enum Color {
        Blanc,
        Gris,
        Noir
}

class Sommet
{
    ArrayList<Integer> voisins;
    Color couleur;
    int debut,fin,pi,id;

    Sommet()
    {
        couleur = Color.Blanc;
        debut = Integer.MAX_VALUE;
        voisins = new ArrayList<>();
    }

}