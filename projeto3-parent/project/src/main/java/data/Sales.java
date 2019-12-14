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
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;

public class Sales {
    private final static String topic = "Sales";
    private final static String BOOTSTRAP_SERVERS = "localhost:9092";
    private static ArrayList<String> sales = new ArrayList<>();

    public static void main(String[] args) {

        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "Sales");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        final Consumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : records) {
                String[] recordInfo  = splitValues(record);

                if(!recordInfo[0].equals("")){
                    int id = Integer.parseInt(recordInfo[0]);
                    int quantidade = Integer.parseInt(recordInfo[1]);
                    double preço = Double.parseDouble(recordInfo[2]);
                    String country = recordInfo[3];

                    double revenue = preço*quantidade;
                } 
            }
        }
    }

    public static KStream<String,Long> makeKStream(){
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, Long> lines = builder.stream(topic);



        return lines;
    }

    public static String[] splitValues(ConsumerRecord<String,String> record){
        try{
            //System.out.println(record.value());

        String[] salesSplit = record.value().split(" ");
        //System.out.println(salesSplit[2]);

        String[] saleSplit = salesSplit[2].split(",");
        /*System.out.println(saleSplit[0]);
        System.out.println(saleSplit[1]);
        System.out.println(saleSplit[2]);
        System.out.println(saleSplit[3]);*/

        String[] nameSplit = saleSplit[0].split(":");
        //System.out.println(nameSplit[1]);

        String[] countrySplit = saleSplit[3].split(":");
        //System.out.println(countrySplit[1]);


        String[] info = new String[4];
        info[0] = nameSplit[1]; //id
        info[1] = saleSplit[1]; //quantidade
        info[2] = saleSplit[2]; //preço
        info[3] = countrySplit[1]; //country

        return info;
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("here");
        }

        String[] info = new String[4];
        info[0] = ""; //id
        info[1] = ""; //quantidade
        info[2] = ""; //preço
        info[3] = ""; //country
        return info;
    }
}