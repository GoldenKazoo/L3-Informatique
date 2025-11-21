public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        Livre miserables = new Livre("Les Miserables", "Victor Hugo", 1862, 400); 
        miserables.afficherDetails();
    }
}
