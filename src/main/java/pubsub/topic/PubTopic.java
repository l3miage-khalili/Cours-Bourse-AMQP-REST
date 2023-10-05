package pubsub.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class PubTopic {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, "topic", true);

            String routingKey1 = "quick.brown.fox";
            String routingKey2 = "quick.red.fox";
            String routingKey3 = "lazy.brown.fox";
            String routingKey4 = "lazy.brown.rabbit";
            String message = "Hello world";

            channel.basicPublish(EXCHANGE_NAME, routingKey1, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + routingKey1 + "':'" + message + "'");
            channel.basicPublish(EXCHANGE_NAME, routingKey2, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + routingKey2 + "':'" + message + "'");
            channel.basicPublish(EXCHANGE_NAME, routingKey3, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + routingKey3 + "':'" + message + "'");
            channel.basicPublish(EXCHANGE_NAME, routingKey4, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + routingKey4 + "':'" + message + "'");
        }
    }
}
