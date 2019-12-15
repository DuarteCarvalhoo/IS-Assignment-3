package data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
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
import org.apache.kafka.connect.source.SourceTask;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Purchases {
    private final static String topic = "Purchases";
    private final static String BOOTSTRAP_SERVERS = "localhost:9092";
    private static ArrayList<String> purchares = new ArrayList<>();

    public static void main(String[] args) {

        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "Purchases");
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
                if(!recordInfo[0].equals("")){
                    int id = Integer.parseInt(recordInfo[0]);
                    int quantidade = Integer.parseInt(recordInfo[1]);
                    double preço = Double.parseDouble(recordInfo[2]);
                }
            }
        }
    }

    public static String[] splitValues(ConsumerRecord<String,String> record){
        //System.out.println(record.value());

        String[] pursharesSplit = record.value().split(" ");
        /*System.out.println("primeiro:"+pursharesSplit[0]);
        System.out.println("segundo:"+pursharesSplit[1]);
        System.out.println("terceiro:"+pursharesSplit[2]);*/
        
        String[] purshareSplit = pursharesSplit[2].split(",");

        
        String[] info = new String[3];
        info[0] = purshareSplit[0]; //id
        info[1] = purshareSplit[1]; //quantidade
        info[2] = purshareSplit[2]; //type
        /*System.out.println(info[0]);
        System.out.println(info[1]);
        System.out.println(info[2]);*/

        return info;
    }

}