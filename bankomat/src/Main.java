public class Main {
    public static void main(String[] args) {
        CardDAO cardDAO = new CardDAO("data.txt");
        Bankomat bankomat = new Bankomat(cardDAO);
        bankomat.run();
    }
}