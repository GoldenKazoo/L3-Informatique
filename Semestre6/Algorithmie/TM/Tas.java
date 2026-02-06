import java.util.ArrayList;

public class Tas
{
    ArrayList<Integer>  t;
    int                 n;

    public Tas()
    {
        this.t = new ArrayList<>();
    }

    private void cp_array(ArrayList<Integer> t, ArrayList<Integer> array)
    {
        for (int i = 0; i < array.size(); i++)
        {
            t.add(array.get(i));
        }
    }

    public Tas(ArrayList<Integer> tab)
    {
        t = new ArrayList<>(tab);
    }

    public static int getFilsGauche(int i)
    {
        return ((2 * i) + 1);
    }

    public static int getFilsDroit(int i)
    {
        return ((2 * i) + 2);
    }

    public static int getParent(int i)
    {
        if (i == 0)
            return 0;
        return ((i - 1) / 2);
    }

    public boolean estVide()
    {
        return this.t.isEmpty();
    }

    @Override
    public String toString()
    {
        if (estVide())
            throw new IllegalStateException("exception");
        return toStringAux(0);
    }

    private String toStringAux(int i)
    {
        if (i >= t.size())
            return ("");
        String s = "[" + t.get(i);
        String gauche = toStringAux(2 * i + 1);
        if (!gauche.equals(""))
            s += gauche;

        String droite = toStringAux(2 * i + 2);
        if (!droite.equals(""))
            s += droite;
        s += "]";
        return s;
    }

    public boolean testTas()
    {
        if (estVide())
            return true;

        int n = t.size();
        for (int i = 0; i < n; i++)
        {
            int gauche = 2 * i + 1;
            int droit = 2 * i + 2;

            if (gauche < n && t.get(i) < t.get(gauche))
                return false;

            if (droit < n && t.get(i) < t.get(droit))
                return false;
        }

        return true;
    }

    public void entasser(int index)
    {
        int n = t.size();
        int gauche = 2 * index + 1;
        int droit = 2 * index + 2;
        int max = index;

        if (gauche < n && t.get(gauche) > t.get(max))
            max = gauche;

        if (droit < n && t.get(droit) > t.get(max))
            max = droit;

        if (max != index)
        {
            int tmp = t.get(index);
            t.set(index, t.get(max));
            t.set(max, tmp);
            entasser(max);
        }
    }

    public void inser(int value)
    {
        t.add(value);
        int i = t.size() - 1;
        while (i > 0)
        {
            int parent = (i - 1) / 2;
            if (t.get(parent) >= t.get(i))
                break;
            int tmp = t.get(i);
            t.set(i, t.get(parent));
            t.set(parent, tmp);
            i = parent;
        }
    }
    /*
     * Dans le pire cas, on remonte jusqu’a la racine → O(log n)
     * Insertion + ajout à la fin → O(1) (negligeable)
     * Donc complexite totale O(log n)
     */

    public int supprMax()
    {

        int max = t.get(0);
        int n = t.size();

        if (n == 1)
        {
            t.remove(0);
            return max;
        }
        t.set(0, t.get(n - 1));
        t.remove(n - 1);
        entasser(0);
        return max;
    }

    public static void tri(ArrayList<Integer> tab)
    {
        Tas tas = new Tas(tab);
        int n = tab.size();

        for (int i = n - 1; i >= 0; i--)
        {
            int max = tas.supprMax();
            tab.set(i, max);
        }
    }

    /*
     * La boucle fait n extractions → O(n log n)
     * Construction du tAs : O(n) si optimisee
     * Donc complexite totale O(n log n)
     */
}
