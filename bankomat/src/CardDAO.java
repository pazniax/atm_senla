import java.io.*;
import java.util.ArrayList;
import java.util.List;

class CardDAO {
    private String dataFile;

    public CardDAO(String dataFile) {
        this.dataFile = dataFile;
    }

    public List<Card> loadCards() {
        List<Card> cards = new ArrayList<>();
        System.out.println("Zagruzhayu karty iz: " + dataFile);
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                Card card = new Card(parts[0], parts[1], Double.parseDouble(parts[2]), Integer.parseInt(parts[3]), Long.parseLong(parts[4]));
                cards.add(card);
            }
        } catch (IOException e) {
            System.err.println("Oshibka zagruzki kart: " + e.getMessage());
            System.exit(1);
        }
        return cards;
    }

    public void saveCards(List<Card> cards) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
            for (Card card : cards) {
                writer.write(card.getCardNumber() + " " + card.getPassword() + " " + card.getBalance() + " " + card.getCardStatus() + " " + card.getBlockUntil() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Oshibka sohraneniya kart: " + e.getMessage());
        }
    }
}