package pubsub.headers;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;

public class Prod1 {

    private static final String EXCHANGE_NAME = "logs_headers";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "headers");

            // First message
            String message = "Hello from Prod1!";
            AMQP.BasicProperties props = new AMQP.BasicProperties();
            HashMap map = new HashMap<String,Object>();
            map.put("Third","C");
            props = props.builder().headers(map).build();
            channel.basicPublish(EXCHANGE_NAME, "", props, message.getBytes());
            System.out.println(" Message Sent '" + message + "'");
        }
    }
}
