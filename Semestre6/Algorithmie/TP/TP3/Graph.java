import java.util.ArrayList;

public class Graph {
    private
        ArrayList<Sommet> sommets;

    public Graph()
    {
        sommets = new ArrayList<>();
    }

    public void ajoutSommet()
    {
        Sommet s = new Sommet();
        s.id = this.sommets.size();
        this.sommets.add(s);
    }

    public void ajoutArete(int i, int j )
    {
        boolean check = 0;
        if (i < this.sommets.size() && j < this.sommets.size())
        {
            for (Sommets s: this.sommets.get(i).voisins)
            {
                if(s.getId() == j)
                    check = 1;
            }
            if (check == 0)
                this.sommets.get(i).voisins.add(this.sommets.get(j).id);  
        }

    @Override
    public String toString()
    {
        String dot = "digraph{\n";
        for (Sommet s: this.sommets)
        {
            System.out.println("Ajout de " + s.id);
            dot += s.id + ";" + "\n";
        }
        for (Sommet s: this.sommets)
        {
          for (int sv: s.voisins)
          {
            dot += s.id + "->" + sv + ";" +"\n";
          }  
        }
        dot += '}';
        return (dot);
    }
}
}