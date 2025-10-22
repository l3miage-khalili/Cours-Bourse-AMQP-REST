package objects;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class ClientCourtier {

    // Déclaration du nom de la queue (constante)
    private final static String QUEUE_NAME = "titres_boursiers_queue";

    public static void main(String[] argv) throws Exception {

        // Création d'une fabrique de connexion RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();

        /* tentative de récupération de l'URL de RabbitMQ */
        // On vérifie si une variable d'environnement RABBIT_URL existe
        if (System.getenv("RABBIT_URL") != null){
            // Si oui, on utilise l'URL fournie pour se connecter
            factory.setUri(System.getenv("RABBIT_URL"));
        }
        else {
            // sinon on tente en local
            factory.setHost("localhost");
        }

        // On établit une connexion avec RabbitMQ
        Connection connection = factory.newConnection();

        // On crée un canal de communication sur cette connexion
        Channel channel = connection.createChannel();

        // On déclare la queue si elle n'existe pas (durée=false, exclusive=false, autodelete=false)
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // On affiche un message d'attente des messages
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        /* Définition d'un callback (fonction) qui sera exécutée quand un message arrive */
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            // On convertit le corps du message en chaîne de caractères UTF-8
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

            // On affiche le message reçu
            System.out.println(" [x] Received '" + message + "'");

            // On crée une instance de Gson pour convertir JSON en objet
            Gson gson = new Gson();

            // On désérialise le message JSON en objet MaClasse
            TitreBoursier titreBoursier = gson.fromJson(message, TitreBoursier.class);

            // On affiche l'objet désérialisé
            System.out.println(titreBoursier);
        };

        // On s'abonne à la queue et traite les messages avec le callback défini plus haut
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTage -> { });
    }
}
