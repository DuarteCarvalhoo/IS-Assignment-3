package data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
//Producer
import java.util.Properties;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.*;
import com.google.gson.Gson;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DBInfo {
    private final static String topic = "DBInfo";
    private final static String BOOTSTRAP_SERVERS = "localhost:9092";
    private static ArrayList<String> items = new ArrayList<>();
    private static ArrayList<String> countries = new ArrayList<>();

    public static void main(String[] args) {

        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "Info");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        final Consumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));

        JSONParser parser = new JSONParser();
        ObjectMapper mapper = new ObjectMapper();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : records) {
                splitValues(record);
                /*
                 * JSONObject json; HashMap<String, Object> yourHashMap = new
                 * Gson().fromJson(json.toString(), HashMap.class); try { json = (JSONObject)
                 * parser.parse(record.value()); }catch (ParseException e) { // TODO
                 * Auto-generated catch block e.printStackTrace(); }
                 */
            }

            //makeSale();
            //makePurchase();
        }
    }

    public static void makeSale() {
        String topicName = "Sales";

        Producer<String, String> saleProducer = makeProducer();
        String message = createSale();

        saleProducer.send(new ProducerRecord<String, String>(topicName, null, message));
        saleProducer.close();
    }

    public static String createSale() {
        Random rand = new Random();
        int country = rand.nextInt(countries.size() - 1);
        int item = rand.nextInt(items.size() - 1);
        int quantidade = rand.nextInt(50);
        double preço = rand.nextDouble();

        String message = "New sale: " + item + "," + quantidade + "," + preço + "," + country;
        return message;
    }

    public static void makePurchase() {
        String topicName = "Purchases";

        Producer<String, String> purchaseProducer = makeProducer();
        String message = createPuchase();

        purchaseProducer.send(new ProducerRecord<String, String>(topicName, null, message));
        purchaseProducer.close();
    }

    private static String createPuchase() {
        Random rand = new Random();
        int item = rand.nextInt(items.size() - 1);
        int quantidade = rand.nextInt(100);
        double preço = rand.nextDouble();

        String message = "New purchase: " + item + "," + quantidade + "," + preço;
        return message;
    }

    public static Producer<String, String> makeProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        return producer;
    }

    public static String splitValues(ConsumerRecord<String,String> record){
        System.out.println(record.value());

        String[] recordSplit = record.value().split("\"payload\":\\{");
        System.out.println(recordSplit[1]);
        
        String[] recordSplit2 = recordSplit[1].split(",");
        System.out.println(recordSplit2[0]);
        System.out.println(recordSplit2[1]);

        String[] recordSplit3 = recordSplit2[1].split("\\}");
        System.out.println(recordSplit3[0]);



        String[] idSplit = recordSplit2[0].split(":");
        int id = Integer.parseInt(idSplit[1]);

        return "";
    }

}