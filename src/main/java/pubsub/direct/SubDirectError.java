package pubsub.direct;

import com.rabbitmq.client.Channel;             // Import du canal de communication RabbitMQ
import com.rabbitmq.client.Connection;          // Import de la connexion RabbitMQ
import com.rabbitmq.client.ConnectionFactory;   // Import de la fabrique pour créer des connexions
import com.rabbitmq.client.DeliverCallback;     // Import du callback qui traite les messages reçus

public class SubDirectError {

    // On déclare le nom de l'exchange de type "direct"
    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] argv) throws Exception {
        // On crée une fabrique de connexion RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();

        // On définit l'hôte local
        factory.setHost("localhost");

        // On établit une connexion avec RabbitMQ
        Connection connection = factory.newConnection();

        // On crée un canal de communication sur cette connexion
        Channel channel = connection.createChannel();

        // On déclare l'exchange de type "direct"
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        // On crée une queue temporaire (anonyme) et récupère son nom généré
        String queueName = channel.queueDeclare().getQueue();

        // On lie la queue à l'exchange avec la routing key "error" (reçoit les messages d'erreur)
        channel.queueBind(queueName, EXCHANGE_NAME, "error");
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // On définit un callback (fonction) qui sera exécuté quand un message arrive
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            // On convertit le corps du message en chaîne UTF-8
            String message = new String(delivery.getBody(), "UTF-8");

            // On affiche le message reçu avec sa routing key
            System.out.println(" [x] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        // On s'abonne à la queue et traite les messages avec le callback défini
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
