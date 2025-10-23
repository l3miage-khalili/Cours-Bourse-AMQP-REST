package pubsub.direct;

import com.rabbitmq.client.Channel;             // Import du canal de communication RabbitMQ
import com.rabbitmq.client.Connection;          // Import de la connexion RabbitMQ
import com.rabbitmq.client.ConnectionFactory;   // Import de la fabrique pour créer des connexions

public class PubDirect {

    // On déclare le nom de l'exchange de type "direct"
    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] argv) throws Exception {
        // On crée une fabrique de connexion RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();

        // On définit l'hôte local
        factory.setHost("localhost");

        // On crée une connexion et un canal (try-with-resources pour fermer automatiquement)
        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()) {

            // On déclare un exchange de type "direct" qui routera les messages en fonction de la clé de routage
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            // On définit trois niveaux de sévérité (routing keys)
            String severity1 = "info";
            String severity2 = "warning";
            String severity3 = "error";

            // On définit le message à envoyer
            String message = "Hello world!";

            // On publie le message avec la routing key "info"
            channel.basicPublish(EXCHANGE_NAME, severity1, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + severity1 + "':'" + message + "'");

            // On publie le message avec la routing key "warning"
            channel.basicPublish(EXCHANGE_NAME, severity2, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + severity2 + "':'" + message + "'");

            // On publie le message avec la routing key "error"
            channel.basicPublish(EXCHANGE_NAME, severity3, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + severity3 + "':'" + message + "'");
        }
    }
}
