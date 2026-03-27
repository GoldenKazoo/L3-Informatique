import java.util.ArrayList;

enum Color {
    Blanc,
    Gris,
    Noir
}

class Arrete{
    double poids;
    int cible;
    Arrete(double poids, int cible){
        this.poids = poids;
        this.cible = cible;
    }
}

class Sommet{

    ArrayList<Arrete> voisins;
    double distance;
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

    public void ajoutArete(int i, int j, double poids){
        if(i < 0 || i >= sommets.size() || j < 0 || j >= sommets.size()){
            throw new IllegalArgumentException();
        }
        if(!existeArete(i, j)){
            this.sommets.get(i).voisins.add(new Arrete(poids, j));
        }
    }

    public boolean existeArete(int i, int j) {
        if((i < 0 || i >= this.sommets.size()) || (j < 0 || j >= this.sommets.size())){
            throw new IllegalArgumentException();
        }
        return this.sommets.get(i).voisins.contains(j);
    }

    public double getNbSommet(){
        return this.sommets.size();
    }

    public int getNbAretes(){
        int nb = 0;
        for (Sommet sommet : this.sommets) {
            nb += sommet.voisins.size();
        }
        return nb;
    }

    public void parcoursProf() {
        int date = 0;
        for (Sommet sommet : sommets)
            if (sommet.couleur == Color.Blanc)
                date = visiterPP(sommet, date);
    }

    public int visiterPP(Sommet u, int datep){
        datep += 1;
        u.debut = datep;
        u.couleur = Color.Gris;
        for (Arrete v : u.voisins) {
            if(sommets.get(v.cible).couleur == Color.Blanc){
                sommets.get(v.cible).pi = u.id; // ?
                datep = visiterPP(sommets.get(v.cible), datep);
            }
        }
        u.couleur = Color.Noir;
        datep += 1;
        u.fin = datep;
        return datep;
    }

    public String toStringDates(){
        StringBuilder str = new StringBuilder();
        for (Sommet sommet : this.sommets) {
            str.append("sommet " + sommet.id + "[" + sommet.debut + ";" + sommet.fin + "]\n");
        }
        return str.toString();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("digraph{\n");
        for (Sommet sommet : this.sommets)
            str.append(sommet.id + ";\n");
        for (Sommet sommet : this.sommets)
            for (Arrete a : sommet.voisins)
                str.append(sommet.id + "->" + a.cible + " [label=\"" + a.poids + "\"];\n");
        str.append("}");
        return str.toString();
    }

    public double getDistance(int i){
        return this.sommets.get(i).distance;
    }

    public int getParent(int i){
        return this.sommets.get(i).pi;
    }

    public double poidsTotal(){
        double p = 0;
        for (Sommet sommet : sommets) {
            for (Arrete a : sommet.voisins) {
                p += a.poids;
            }
        }
        return p;
    }

    public void initSourceUnique(int s){
        for (Sommet sommet : sommets) {
            sommet.distance = Double.MAX_VALUE;
            sommet.pi = -1;
        }
        sommets.get(s).distance = 0;
    }

    public void relacherVoisins(int u) {
    for (Arrete a : sommets.get(u).voisins) {
        Sommet v = sommets.get(a.cible);
        Sommet su = sommets.get(u);
        if (su.distance != Double.MAX_VALUE && v.distance > su.distance + a.poids) {
            v.distance = su.distance + a.poids;
            v.pi = u;
        }
    }
}

    public boolean bellmanFord(int s) {
        initSourceUnique(s);
        for (int i = 0; i < sommets.size() - 1; i++) {
            for (Sommet u : sommets) { 
                relacherVoisins(u.id);
            }
        }
        for (Sommet u : sommets) {
            for (Arrete a : u.voisins) {
                Sommet v = sommets.get(a.cible);
                if (v.distance > u.distance + a.poids)
                    return false; 
            }
        }
        return true;  
    }
}