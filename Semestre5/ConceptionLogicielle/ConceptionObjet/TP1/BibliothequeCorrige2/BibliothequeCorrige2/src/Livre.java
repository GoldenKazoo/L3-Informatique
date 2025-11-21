class Livre extends Document {
    private int nombreDePages;

    public Livre(String titre, String auteur, int annee, int nombreDePages) {
        super(titre, auteur, annee);
        this.nombreDePages = nombreDePages;
    }

    @Override
    public void afficherDetails() {
        super.afficherDetails(); 
        System.out.println("nombre de pages: "+nombreDePages + " pages.");
    }

    @Override
    public String getType() {
        return "Livre";
    }



}
