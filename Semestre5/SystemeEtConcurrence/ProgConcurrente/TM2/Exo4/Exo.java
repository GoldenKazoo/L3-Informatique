class Philosophe implements Runnable {
    private Lock gauche, droite;
    private String nom;
    public Philosophe(String nom, Lock gauche, Lock droite) {
        this.nom = nom; this.gauche = gauche; this.droite = droite;
    }
    public void run() {
        try {
            while (true) {
                gauche.lock();
                System.out.println(nom + " prend la gauche");
                Thread.sleep(50);
                droite.lock();
                System.out.println(nom + " prend la droite et mange");
                Thread.sleep(50);
                droite.unlock();
                gauche.unlock();
            }
        } catch (InterruptedException e) {}
    }
}
