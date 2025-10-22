package objects;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class ServiceBourse {

    // Déclaration du nom de la queue (constante)
    private final static String QUEUE_NAME = "titres_boursiers_queue";

    public static void main(String[] argv) throws Exception {

        // Création d'une fabrique de connexion RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();

        // On vérifie si une variable d'environnement RABBIT_URL existe
        if (System.getenv("RABBIT_URL") != null) {
            // Si oui, on utilise l'URL fournie pour se connecter
            factory.setUri(System.getenv("RABBIT_URL"));
        }
        else {
            // sinon, on tente en local
            factory.setHost("localhost");
        }

        // Création d'une connexion et d'un canal (try-with-resources pour fermer automatiquement)
        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()
                ) {
            // Déclaration de la queue si elle n'existe pas (durée=false, exclusive=false, autodelete=false)
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // Instanciation de l'objet TitreBoursier
            TitreBoursier titreBoursier = new TitreBoursier("AAPL", "IAK Consulting", 100.0, 2, "secteur 1");

            // instance Gson pour la conversion JSON
            Gson gson = new Gson();

            // Sérialisation de l'objet en chaîne JSON
            String message = gson.toJson(titreBoursier);

            // Envoi du message dans la queue "hello" (QUEUE_NAME)
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));

            // Affichage du message envoyé
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}