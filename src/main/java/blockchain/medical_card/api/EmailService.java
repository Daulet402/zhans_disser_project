package blockchain.medical_card.api;

public interface EmailService {

    void sendMail(String recipient, String sender, String title, String text);
}
