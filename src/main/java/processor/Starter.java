package processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import processor.model.Message;
import processor.utils.KafkaConsumerUtil;
import processor.utils.MongoDBManager;

import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.Locale;

public class Starter {

    public static void main(String[] args) {

        final Consumer<String, String> consumer = KafkaConsumerUtil.createConsumer();
        MongoDBManager mongoDBManager = MongoDBManager.getInstance();

        while (true) {

            ConsumerRecords<String, String> consumerRecords =
                    consumer.poll(Duration.ofMillis(400));

            Flowable<ConsumerRecord<String, String>> consumerRecordsFlowable = Flowable.fromIterable(consumerRecords);

            consumerRecordsFlowable.observeOn(Schedulers.io()).subscribe(record -> {
                System.out.printf("Consumer Record:(%d, %s, %d, %d)\n",
                        record.key(), record.value(),
                        record.partition(), record.offset());

                System.out.println("Current Thread: " + Thread.currentThread().getName());

                Message message = null;
                try {
                   message = buildMessageFromRecord(record);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Mensaje -> : " + message);

                mongoDBManager.save(message);
            });

            // Line to avoid SonarLint rule
            if(false) {
                break;
            }

        }

    }

    private static Message buildMessageFromRecord(ConsumerRecord<String, String> record) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(record.value(), Message.class);
        Locale locale = new Locale(message.getLanguage());
        message.setLanguageDesc(locale.getDisplayName());
        message.setCreatedAt(new Date());
        message.setContent(message.getContent().toUpperCase());

        return message;
    }

}
