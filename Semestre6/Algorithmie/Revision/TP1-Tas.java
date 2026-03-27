import java.util.ArrayList;

public class Tas {
    private ArrayList<Integer> t;
    private int n;

    public Tas(){
        this.t = new ArrayList<>();
        this.n = 0;
    }

    public static int getFilsGauche(int i){
        return 2 * i + 1;
    }

    public static int getFilsDroit(int i){
        return 2 * i + 2;
    }

    public static int getParent(int i){
        if (i == 0)
            return 0;
        
        return (i-1)/2;
    }

    public Tas(ArrayList<Integer> tab){
        this.t = tab;
        this.n = tab.size();
    }

    public boolean estVide(){
        return this.n == 0;
    }

    @Override
    public String toString(){
        if(estVide())
            return "";
        return toString(0);
    }

    //  [15[12[10[7]][3]][9[8][5]]]

    private String toString(int index){
        if(index >= this.n)
            return "";
        if(index < this.n - 1)
            return "[" + this.t.get(index) + toString(getFilsGauche(index)) + 
                    toString(getFilsDroit(index)) + "]";
        return "[" + this.t.get(index) + "]";
    }

    public boolean testTas(){
        return testTasAux(0);
    }

    public boolean testTasAux(int index){
        if(getFilsGauche(index) >= this.n || getFilsDroit(index) >= this.n)
            return true;
        if(this.t.get(getFilsGauche(index)) > this.t.get(index)){
            return false;
        }
        if(this.t.get(getFilsDroit(index)) > this.t.get(index)){
            return false;
        }
        return true && testTasAux(getFilsGauche(index)) && testTasAux(getFilsDroit(index));
    }

    public void entasser(int index){
        int left = getFilsGauche(index);
        int right = getFilsDroit(index);
        int max;

        if((left < this.n) && (this.t.get(left) > this.t.get(index)))
            max = left;
        else
            max = index;

        if((right < this.n) && (this.t.get(right) > this.t.get(max)))
            max = right;

        if(max != index){
            int tmp = this.t.get(index);
            this.t.set(index, this.t.get(max));
            this.t.set(max, tmp);
            entasser(max);
        }
    }

    public void inser(int value){
        this.n += 1;
        this.t.add(this.n - 1, value);
        int i = this.n - 1;
        while((i >= 1) && (this.t.get(getParent(i)) < this.t.get(i))){
            int tmp = this.t.get(i);
            this.t.set(i, this.t.get(getParent(i)));
            this.t.set(getParent(i), tmp);
            i = getParent(i);
        }
    }

    public int supprMax(){
        if(estVide()){
            throw new IllegalStateException();
        }
        int racine = this.t.get(0);
        this.t.set(0, this.t.get(this.n - 1));
        this.n -= 1;
        entasser(0);
        return racine;
    }

    public static void tri(ArrayList<Integer> tab){
        Tas tas = new Tas(tab);
        int n = tab.size();

        // 1. Construire le tas (heapify)
        for(int i = n/2 - 1; i >= 0; i--){
            tas.entasser(i);
        }

        // 2. Trier
        for(int i = n - 1; i > 0; i--){
            // échanger racine (max) avec dernier élément
            int tmp = tab.get(0);
            tab.set(0, tab.get(i));
            tab.set(i, tmp);

            // réduire taille du tas
            tas.n--;

            // rétablir le tas
            tas.entasser(0);
        }
    }
}
