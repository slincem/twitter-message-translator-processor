package processor.utils;

import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.*;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import processor.model.Message;

public class MongoDBManager {

    private static MongoDBManager instance = null;
    private MongoDatabase mongoDatabase = null;

    private MongoDBManager(){
        initMongoClient();
    }

    public static MongoDBManager getInstance() {
        if(instance == null) {
            instance = new MongoDBManager();
        }

        return instance;
    }

    private void initMongoClient(){
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClients.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry)
                .build();

        // To directly connect to the default server localhost on port 27017
        MongoClient mongoClient = MongoClients.create(settings);
        this.mongoDatabase = mongoClient.getDatabase("twitter-translator-db");
    }

    public void save(Message message) {

        MongoCollection<Message> collection = this.mongoDatabase.getCollection("message", Message.class);

        collection.insertOne(message).subscribe(new Subscriber<Success>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(10);
            }

            @Override
            public void onNext(Success success) {
                System.out.println("Mensaje guardado");
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("DONE!");
            }
        });

        // mongoClient.close();

    }

}
