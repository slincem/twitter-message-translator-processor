package processor.utils;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class KafkaConsumerUtil {

    private static final String TOPIC = "twitter-topic";

    private KafkaConsumerUtil(){}

    public static Consumer<String, String> createConsumer() {
        Map<String, Object> consumerConfigProperties = new HashMap<String, Object>();
        consumerConfigProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerConfigProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "twitter-group1");
        consumerConfigProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerConfigProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        final Consumer<String, String> consumer = new KafkaConsumer<String, String>(consumerConfigProperties);
        consumer.subscribe(Collections.singletonList(TOPIC));
        return consumer;
    }
}
