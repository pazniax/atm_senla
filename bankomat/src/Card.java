public class Card {
    private String cardNumber;
    private String password;
    private double balance;
    private int cardStatus; // 0 - aktivna, 1 - zablokirovana
    private long blockUntil; // vremya, kogda karta budet razblokirovana
    private int pinAttempts;

    public Card(String cardNumber, String password, double balance, int cardStatus, long blockUntil) {
        if (!isValidCardNumber(cardNumber)) {
            throw new IllegalArgumentException("Neverniy format karty v faile");
        }
        this.cardNumber = cardNumber;
        this.password = password;
        this.balance = balance;
        this.cardStatus = cardStatus;
        this.blockUntil = blockUntil;
        this.pinAttempts = 0;
    }

    private boolean isValidCardNumber(String cardNumber) {
        String pattern = "^\\d{4}-\\d{4}-\\d{4}-\\d{4}$"; //regex dlya xxxx-xxxx-xxxx-xxxx
        return cardNumber.matches(pattern);
    }

    public boolean authenticate(String password) {
        if (this.password.equals(password)) {
            pinAttempts = 0;
            return true;
        } else {
            pinAttempts++;
            if (pinAttempts < 3) {
                System.out.println("Nepravilny pin-kod. Kolichestvo ostavshihsya popytok - " + (3 - pinAttempts));
            } else {
                cardStatus = 1;
                blockUntil = System.currentTimeMillis() + 86400000;
                System.out.println("Karta zablokirovana. Razblokiryetsa cherez " + getRemainingTime() + " secund.");
            }
            return false;
        }
    }

    public void checkStatus() {
        if (cardStatus == 1) {
            if (System.currentTimeMillis() < blockUntil) {
                System.out.println("Karta zablokirovana. Razblokiryetsa cherez " + getRemainingTime() + " secund.");
            } else {
                cardStatus = 0;
                blockUntil = 0;
                System.out.println("Karta razblokirovana.");
            }
        }
    }

    private long getRemainingTime() {
        return (blockUntil - System.currentTimeMillis()) / 1000;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public double getBalance() {
        return balance;
    }

    public int getCardStatus() {
        return cardStatus;
    }

    public String getPassword() {
        return password;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public long getBlockUntil() {
        return blockUntil;
    }

}