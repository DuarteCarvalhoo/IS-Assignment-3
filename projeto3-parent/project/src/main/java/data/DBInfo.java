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
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.value());
                try {
                    boolean itemExists = false;
                    boolean countryExists = false;
                    JSONObject json = (JSONObject) parser.parse(record.value());
                    String payload = json.get("payload").toString();
                    JSONObject fields = (JSONObject) parser.parse(payload);

                    String name = fields.get("name").toString();
                    String type = fields.get("data_type").toString();
                    //float revenue = Float.parseFloat(fields.get("revenue").toString());
                    //float expenses = Float.parseFloat(fields.get("expenses").toString());
                    //float profit = Float.parseFloat(fields.get("profit").toString());
                    Integer id = Integer.parseInt(fields.get("id").toString());

                    if (type.equals("country")) {
                        for (Integer country : countries) {
                            if (country == id) {
                                countryExists = true;
                            }
                        }
                        if (!countryExists) {
                            countries.add(id);
                        }
                    } else {
                        for (Integer item : items) {
                            if (item == id) {
                                itemExists = true;
                            }
                        }
                        if (!itemExists) {
                            items.add(id);
                        }
                    }

                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            makeSale();
            makePurchase();
            System.out.println("New sale");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void makeSale() {
        String topicName = "Sales";

        Producer<String, String> saleProducer = makeProducer();

        String message = "";

        if(countries.size()>0 && items.size()>0){
            String[] info = createSale();
            message = info[0];
            saleProducer.send(new ProducerRecord<String, String>(topicName, info[1], message));
            saleProducer.close();
        }
    }

    public static String[] createSale() {
        Random rand = new Random();
        int country = rand.nextInt(countries.size());
        Integer item = rand.nextInt(items.size());
        int quantidade = rand.nextInt(50);
        double preço = rand.nextDouble();
        
        double p = Math.round(preço*100);
        preço = p / 100.0;

        String message = quantidade + "," + preço + "," + countries.get(country);

        String[] info = new String[2];

        info[0] = message;
        info[1] = item.toString();
        return info;
    }

    public static void makePurchase() {
        String topicName = "Purchases";

        Producer<String, String> purchaseProducer = makeProducer();

        String message = "";
        String[] info;

        if(countries.size()>0 && items.size()>0){
            info = createPurchase();
            message = info[0];
            purchaseProducer.send(new ProducerRecord<String, String>(topicName, info[1], message));
            purchaseProducer.close();
        }
    }

    private static String[] createPurchase() {
        Random rand = new Random();
        Integer item = rand.nextInt(items.size());
        int quantidade = rand.nextInt(100);
        double preço = rand.nextDouble();

        double p = Math.round(preço*100);
        preço = p / 100.0;

        String message = quantidade + "," + preço;

        String[] info = new String[2];
        info[0] = message;
        info[1] = item.toString();
        return info;
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

}