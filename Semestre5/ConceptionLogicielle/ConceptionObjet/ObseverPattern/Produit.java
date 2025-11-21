public class Produit implements Sujet
{
    private String  nom;
    private int     nb_stock;
    private Observer[];

    public Produit (String nom, int stock)
    {
        this.nom = nom;
        this.nb_stock = stock;
    }

    public void add_observer(Observer observer)
    {
        
    }
}