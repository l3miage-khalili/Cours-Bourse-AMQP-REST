package pubsub.direct;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import objects.TitreBoursier;

public class ClientCourtierGoogle {

    // Déclaration du nom de la queue (constante)
    private static final String EXCHANGE_NAME = "titres_boursiers_exchange";

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

        // On lie la queue à l'exchange avec la routing key "GOOG" (reçoit les messages GOOG)
        channel.queueBind(queueName, EXCHANGE_NAME, "GOOG");
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // On définit un callback (fonction) qui sera exécuté quand un message arrive
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            // On convertit le corps du message en chaîne UTF-8
            String message = new String(delivery.getBody(), "UTF-8");

            // On affiche le message reçu avec sa routing key
            System.out.println(" [x] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");

            // On crée une instance de Gson pour convertir JSON en objet
            Gson gson = new Gson();

            // On désérialise le message JSON en objet TitreBoursier
            TitreBoursier titreBoursier = gson.fromJson(message, TitreBoursier.class);

            // On affiche l'objet désérialisé
            System.out.println(titreBoursier);
        };

        // On s'abonne à la queue et traite les messages avec le callback défini
        channel.basicConsume(queueName, true, deliverCallback, consumerTage -> { });
    }
}
