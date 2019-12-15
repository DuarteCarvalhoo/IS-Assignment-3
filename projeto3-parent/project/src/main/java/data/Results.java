package data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.Random;


//Producer
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Results {
    private final static String topic = "Results";
    private final static String BOOTSTRAP_SERVERS = "localhost:9092";
    private static ArrayList<Integer> items = new ArrayList<>();
    private static ArrayList<Integer> countries = new ArrayList<>();

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        String aux;
        int i=0;
        while(true){
            i++;
            aux = "{'schema':{'type':'struct','fields':[{'type':'int32','optional':false,'field':'id'},{'type':'int32','optional':false,'field':'revenue'},'payload':{'id':"+i+", 'revenue':731430.0,'profit':257070.0}}}";
            producer.send(new ProducerRecord<String, String>("Results", Integer.toString(i), Integer.toString(i)));
        }
        producer.close();

    }

}
