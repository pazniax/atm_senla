import java.util.List;
import java.util.Objects;
import java.util.Scanner;

class Bankomat {
    private CardDAO cardDAO;
    private List<Card> cards;

    public Bankomat(CardDAO cardDAO) {
        this.cardDAO = cardDAO;
        this.cards = cardDAO.loadCards();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Vvedite nomer karty:");
            String cardNumber = scanner.nextLine();
            if (Objects.equals(cardNumber, "")) {
                System.out.println("Zavershenie raboty...");
                cardDAO.saveCards(cards);
                break;
            }
            Card card = findCard(cardNumber);
            if (card == null) {
                System.out.println("Karta ne naidena");
                continue;
            }
            card.checkStatus();
            System.out.println("Vvedite pin-kod:");
            String pin = scanner.nextLine();
            if (!card.authenticate(pin)) {
                System.out.println("Nepravilny pin-kod");
                continue;
            }
            while (true) {
                System.out.println("1. Posmotret' balans");
                System.out.println("2. Vyvesti den'gi");
                System.out.println("3. Popolnit' balans");
                System.out.println("4. Vyiti");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        System.out.println("Balans: " + card.getBalance());
                        break;
                    case 2:
                        System.out.println("Vvedite summy dlya vyvoda:");
                        double amount = scanner.nextDouble();
                        if (amount > card.getBalance() || amount > 1000000) {
                            System.out.println("Nel'zya provesti operaciyu (chislo bol'she balansa ili 1 milliona)");
                        } else {
                            card.setBalance(card.getBalance() - amount);
                            System.out.println("Summa uspeshno snyata");
                        }
                        break;
                    case 3:
                        System.out.println("Vvedite summy dlya popolneniya:");
                        amount = scanner.nextDouble();
                        if (amount > 1000000) {
                            System.out.println("Nel'zya popolnit' bol'she chem na million za 1 raz");
                        } else {
                            card.setBalance(card.getBalance() + amount);
                            System.out.println("Balans uspeshno popolnen");
                        }
                        break;
                    case 4:
                        cardDAO.saveCards(cards);
                        return;
                }
            }
        }
    }

    private Card findCard(String cardNumber) {
        for (Card card : cards) {
            if (card.getCardNumber().equals(cardNumber)) {
                return card;
            }
        }
        return null;
    }
}