class Noeud {
  int v;
  Noeud fg;
  Noeud fd;
  int hauteur;

  Noeud(Noeud fg,int v,Noeud fd){
    this.fg=fg;
    this.v=v;
    this.fd=fd;

    int hg = (fg == null) ? 0 : fg.hauteur;
    int hd = (fd == null) ? 0 : fd.hauteur;

    this.hauteur = 1 + Math.max(hg, hd);
  }

  public String toString(){
    String sb = "[";
    if (fg!=null)sb += fg.toString();
    sb += v;
    if (fd!=null)sb += fd.toString();
    return sb + "]";
  }

  public boolean testABR(int min, int max){
    if(this.v < min || this.v > max)
        return false;
    if((this.fg != null) && !(this.fg.testABR(min, v - 1)))
        return false;
    if((this.fd != null) && !(this.fd.testABR(v+1, max)))
        return false;
    return true;
  }

    public boolean estEquilibre(){
        int hg = (fg == null) ? 0 : fg.hauteur();
        int hd = (fd == null) ? 0 : fd.hauteur();

        if (Math.abs(hg - hd) > 1)
            return false;

        boolean gaucheOK = (fg == null) || fg.estEquilibre();
        boolean droiteOK = (fd == null) || fd.estEquilibre();

        return gaucheOK && droiteOK;
    }

    public int hauteur() {
        int hg = (fg == null) ? 0 : fg.hauteur();
        int hd = (fd == null) ? 0 : fd.hauteur();
        return 1 + Math.max(hg, hd);
    }
}

public class Arbre {
    Noeud racine;

    public Arbre(){ 
        racine=null;
    }

    public Arbre(Arbre ag, int v, Arbre ad) {
        racine = new Noeud (ag.racine, v, ad.racine);
    }

    public String toString() {
        if (racine == null) { return ""; }
        else { return racine.toString(); }
    }

    public boolean testABR(){
        if(this.racine == null)
            return true;
        return this.racine.testABR(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public void inser(int value){

        Noeud new_node = new Noeud(null, value, null);
        if(this.racine == null){
            this.racine = new_node;
            return;
        }

        Noeud current = this.racine;

        while(true){
            if(value <= current.v){
                if(current.fg == null){
                    current.fg = new_node;
                    return;
                }
                current = current.fg;
            }else{
                if(current.fd == null){
                    current.fd = new_node;
                    return;
                }
                current = current.fd;
            }
        }
    }

    public boolean membre(int value){
        
        Noeud current = this.racine;

        while(current != null){
            if(value == current.v)
                return true;
            if(value < current.v)
                current = current.fg;
            else
                current = current.fd;
        }
        return false;
    }

    public boolean testAVL(){
        if(this.racine == null)
            return true;
        return testABR() && this.racine.estEquilibre();
    }

}