package data;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;


public class DBInfo {
    private final static String topic = "DBInfo";
    private final static String BOOTSTRAP_SERVERS = "localhost:9092";

    public static void main(String[] args){

        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "Purchases");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        
        final Consumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : records) {
                String a = splitValues(record);
            }
        }
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

        return "";
    }

}