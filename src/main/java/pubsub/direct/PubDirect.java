package pubsub.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class PubDirect {

    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            String severity1 = "info";
            String severity2 = "warning";
            String severity3 = "error";
            String message = "Hello world!";

            channel.basicPublish(EXCHANGE_NAME, severity1, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + severity1 + "':'" + message + "'");
            channel.basicPublish(EXCHANGE_NAME, severity2, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + severity2 + "':'" + message + "'");
            channel.basicPublish(EXCHANGE_NAME, severity3, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + severity3 + "':'" + message + "'");
        }
    }
}
