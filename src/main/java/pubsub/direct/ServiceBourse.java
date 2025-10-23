package pubsub.direct;

import com.google.gson.Gson;                    // Import de la librairie Gson pour sérialiser/désérialiser JSON
import com.rabbitmq.client.Channel;             // Import du canal de communication RabbitMQ
import com.rabbitmq.client.Connection;          // Import de la connexion RabbitMQ
import com.rabbitmq.client.ConnectionFactory;   // Import de la fabrique pour créer des connexions
import objects.TitreBoursier;

public class ServiceBourse {

    // Déclaration du nom de la queue (constante)
    private static final String EXCHANGE_NAME = "titres_boursiers_exchange";

    public static void main(String[] argv) throws Exception {
        // Création d'une fabrique de connexion RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();

        // On définit l'hôte local
        factory.setHost("localhost");

        // Création d'une connexion et d'un canal (try-with-resources pour fermer automatiquement)
        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()) {

            // On déclare un exchange de type "direct" qui routera les messages en fonction de la clé de routage
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            // Instanciation d'un TitreBoursier Apple
            String codeTitreBoursierApple = "AAPL";
            TitreBoursier titreBoursierApple = new TitreBoursier(codeTitreBoursierApple, "titreApple", 100.0, 2, "secteur Apple");

            // Instanciation d'un TitreBoursier Google
            String codeTitreBoursierGoogle = "GOOG";
            TitreBoursier titreBoursierGoogle = new TitreBoursier(codeTitreBoursierGoogle, "titreGoogle", 250, 1, "secteur Google");

            // Instanciation d'un TitreBoursier Microsoft
            String codeTitreBoursierMicrosoft = "MSFT";
            TitreBoursier titreBoursierMicrosoft = new TitreBoursier(codeTitreBoursierMicrosoft, "titreMicrosoft", 520, 3, "secteur Microsoft");

            // instance Gson pour la conversion JSON
            Gson gson = new Gson();

            // Sérialisation de l'objet titreBoursierApple en chaîne JSON
            String messageApple = gson.toJson(titreBoursierApple);

            // On publie le message avec la routing key AAPL
            channel.basicPublish(EXCHANGE_NAME, codeTitreBoursierApple, null, messageApple.getBytes());

            // On affichage le message envoyé pour AAPL
            System.out.println(" [x] Sent '" + codeTitreBoursierApple + "':'" + messageApple + "'");

            // Sérialisation de l'objet titreBoursierGoogle en chaîne JSON
            String messageGoogle = gson.toJson(titreBoursierGoogle);

            // On publie le message avec la routing key GOOG
            channel.basicPublish(EXCHANGE_NAME, codeTitreBoursierGoogle, null, messageGoogle.getBytes());

            // On affichage le message envoyé pour GOOG
            System.out.println(" [x] Sent '" + codeTitreBoursierGoogle + "':'" + messageGoogle + "'");

            // Sérialisation de l'objet titreBoursierMicrosoft en chaîne JSON
            String messageMicrosoft = gson.toJson(titreBoursierMicrosoft);

            // On publie le message avec la routing key MSFT
            channel.basicPublish(EXCHANGE_NAME, codeTitreBoursierMicrosoft, null, messageMicrosoft.getBytes());

            // On affichage le message envoyé pour MSFT
            System.out.println(" [x] Sent '" + codeTitreBoursierMicrosoft + "':'" + messageMicrosoft + "'");
        }
    }
}