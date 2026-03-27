import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

enum Color {
    Blanc,
    Gris,
    Noir
}

class Sommet{
    ArrayList<Integer> voisins;
    Color couleur;
    int debut,fin,pi,id;

    Sommet(){
        couleur = Color.Blanc;
        debut = Integer.MAX_VALUE;
        voisins = new ArrayList<>();
    }
}

public class Graph {
    private
        ArrayList<Sommet> sommets;

    public Graph(){
        sommets = new ArrayList<>();
    }

    public void ajoutSommet(){
        Sommet s = new Sommet();
        s.id = this.sommets.size();
        this.sommets.add(s);
    }

    public void ajoutArete(int i, int j){
        if(i >= this.sommets.size() || j >= this.sommets.size())
            throw new IllegalArgumentException();
        if(this.sommets.get(i) == null || this.sommets.get(j) == null)
            throw new IllegalArgumentException();
        if(!(this.sommets.get(i).voisins.contains(this.sommets.get(j).id)))
            this.sommets.get(i).voisins.add(this.sommets.get(j).id);
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("digraph{\n");
        for (Sommet sommet : sommets) {
            str.append(sommet.id + ";\n");
        }
        for (Sommet sommet : sommets) {
            for (Integer voisin : sommet.voisins) {
                str.append(sommet.id + "->" + voisin + ";\n");
            }
        }
        str.append("}");
        return str.toString();
    }

    public boolean existeArete(int i, int j){
        if(i >= this.sommets.size() || j >= this.sommets.size())
            throw new IllegalArgumentException();
        return this.sommets.get(i).voisins.contains(this.sommets.get(j).id);
    }

    public int getNbSommets(){
        return this.sommets.size();
    }

    public int getNbAretes(){
        int nbArretes = 0;
        for (Sommet sommet : sommets) {
            nbArretes += sommet.voisins.size();
        }
        return nbArretes;
    }

    public void parcoursProf(){
        for (Sommet u : sommets) {
            u.couleur = Color.Blanc;
            u.debut = Integer.MAX_VALUE;
            u.fin = Integer.MAX_VALUE;
            u.pi = 0;
        }
        int date = 0;
        for (Sommet u : sommets) {
            if(u.couleur == Color.Blanc)
                date = visiterPP(u, date);
        }
    }

    public int visiterPP(Sommet u, int date){
        date += 1;
        u.debut = date;
        u.couleur = Color.Gris;
        for (Integer v : u.voisins) {
            if(this.sommets.get(v).couleur == Color.Blanc){
                this.sommets.get(v).pi = u.pi;
                date = visiterPP(this.sommets.get(v), date);
            }
        }
        u.couleur = Color.Noir;
        date += 1;
        u.fin = date;
        return date;
    }

    public String toStringDates(){
        StringBuilder str = new StringBuilder();
        for (Sommet sommet : sommets) {
            str.append("sommet " + sommet.id + "[" + sommet.debut + ";" + sommet.fin + "]\n");
        }
        return str.toString();
    }

    // Étape 2 de Kosaraju : construit le graphe transposé (arêtes inversées)
    private Graph transposer() {
        Graph gt = new Graph();
        for (int i = 0; i < sommets.size(); i++) {
            gt.ajoutSommet();
        }
        for (Sommet u : sommets) {
            for (Integer v : u.voisins) {
                gt.ajoutArete(v, u.id);
            }
        }
        return gt;
    }

    // DFS restreint : collecte dans 'composante' tous les sommets
    // atteignables depuis u dans le graphe transposé
    private int visiterComposante(Sommet u, int date, List<Integer> composante) {
        date += 1;
        u.debut = date;
        u.couleur = Color.Gris;
        composante.add(u.id);
        for (Integer v : u.voisins) {
            if (this.sommets.get(v).couleur == Color.Blanc) {
                date = visiterComposante(this.sommets.get(v), date, composante);
            }
        }
        u.couleur = Color.Noir;
        date += 1;
        u.fin = date;
        return date;
    }

    public List<List<Integer>> ffconnexe() {
    // Étape 1 : DFS sur le graphe original pour calculer les temps de fin
    parcoursProf();

    // Trier les sommets par ordre décroissant de fin
    List<Sommet> ordreDecroissant = new ArrayList<>(sommets);
    Collections.sort(ordreDecroissant, (a, b) -> b.fin - a.fin);

    // Étape 2 : graphe transposé
    Graph gt = transposer();

    // Étape 3 : DFS sur gt dans l'ordre décroissant des fins
    for (Sommet u : gt.sommets) {
        u.couleur = Color.Blanc;
    }

    List<List<Integer>> composantes = new ArrayList<>();
    int date = 0;

    for (Sommet u : ordreDecroissant) {
        Sommet uGt = gt.sommets.get(u.id);
        if (uGt.couleur == Color.Blanc) {
            List<Integer> composante = new ArrayList<>();
            date = gt.visiterComposante(uGt, date, composante);
            // ❌ Collections.sort(composante); supprimé : l'ordre doit être celui du DFS
            composantes.add(composante);
        }
    }

    return composantes;
}
}