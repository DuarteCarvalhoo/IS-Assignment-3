package data;

import java.text.DecimalFormat;
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
    private static ArrayList<Integer> items = new ArrayList<>();
    private static ArrayList<Integer> countries = new ArrayList<>();

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
                String[] recordInfo = splitValues(record);
                if(recordInfo[2].equals("\"country\"")){
                    countries.add(Integer.parseInt(recordInfo[0])); 
                }
                else{
                    items.add(Integer.parseInt(recordInfo[0])); 
                }
                /*
                 * JSONObject json; HashMap<String, Object> yourHashMap = new
                 * Gson().fromJson(json.toString(), HashMap.class); try { json = (JSONObject)
                 * parser.parse(record.value()); }catch (ParseException e) { // TODO
                 * Auto-generated catch block e.printStackTrace(); }
                 */
            }

            makeSale();
            makePurchase();
        }
    }

    public static void makeSale() {
        String topicName = "Sales";

        Producer<String, String> saleProducer = makeProducer();

        String message = "";

        if(countries.size()>0 && items.size()>0){
            message = createSale();
        }

        saleProducer.send(new ProducerRecord<String, String>(topicName, null, message));
        saleProducer.close();
    }

    public static String createSale() {
        Random rand = new Random();
        int country = rand.nextInt(countries.size());
        int item = rand.nextInt(items.size());
        int quantidade = rand.nextInt(50);
        double preço = rand.nextDouble();
        
        double p = Math.round(preço*100);
        preço = p / 100.0;

        String message = "New sale: " + items.get(item) + "," + quantidade + "," + preço + "," + countries.get(country);
        return message;
    }

    public static void makePurchase() {
        String topicName = "Purchases";

        Producer<String, String> purchaseProducer = makeProducer();

        String message = "";
        if(countries.size()>0 && items.size()>0){
            message = createPurchase();
        }

        purchaseProducer.send(new ProducerRecord<String, String>(topicName, null, message));
        purchaseProducer.close();
    }

    private static String createPurchase() {
        Random rand = new Random();
        int item = rand.nextInt(items.size());
        int quantidade = rand.nextInt(100);
        double preço = rand.nextDouble();

        double p = Math.round(preço*100);
        preço = p / 100.0;

        String message = "New purchase: " + items.get(item) + "," + quantidade + "," + preço;
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

    public static String[] splitValues(ConsumerRecord<String,String> record){
        System.out.println(record.value());

        String[] recordSplit = record.value().split("\"payload\":\\{");
        //System.out.println(recordSplit[1]);
        
        String[] recordSplit2 = recordSplit[1].split(",");
        /*System.out.println(recordSplit2[0]);
        System.out.println(recordSplit2[1]);
        System.out.println(recordSplit2[2]);*/
        

        String[] recordSplit3 = recordSplit2[2].split("\\}");
        //System.out.println("3.0" + recordSplit3[0]);

        String[] recordSplit5 = recordSplit2[1].split("\\}");
        //System.out.println("5.1" + recordSplit5[0]);

        String[] nameSplit = recordSplit2[1].split("\\}");
        //System.out.println("5.1" + nameSplit[0]);

        String[] typeSplit = recordSplit3[0].split(":");
        //System.out.println("type " + typeSplit[1]);

        String[] idSplit = recordSplit2[0].split(":");
        //System.out.println("id " + idSplit[1]);

        String[] info = new String[3];
        info[0] = idSplit[1]; //id
        info[1] = nameSplit[0]; //name
        info[2] = typeSplit[1]; //type


        return info;
    }

}