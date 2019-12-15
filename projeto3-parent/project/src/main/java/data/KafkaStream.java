package data;

import java.security.Key;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.connect.source.SourceTask;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

public class KafkaStream {
    private final static String BOOTSTRAP_SERVERS = "localhost:9092";

    public static void main(String[] args) {

        final Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "done");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> salesStream = builder.stream("Sales");

        //KTable<String, Long> outlines = lines.groupByKey().reduce((oldval, newval) -> oldval + newval);
        //outlines.mapValues((k, v) -> k + " => " + v).toStream().to(outtopicname, Produced.with(Serdes.String(), Serdes.String()));
        
        KTable<String, String> outlinesS = salesStream.mapValues((v1, v2) -> v1 + v2).groupByKey().reduce((v1,v2) -> {
            String[] salesParts1 = v1.split(",");
            String[] salesParts = v2.split(",");
            Float itemprofit = (float) 0.0;

            if(salesParts1.length > 1) {
                System.out.print("aqui mau");
                int quantidade = Integer.parseInt(salesParts1[0]);
                Float preço = Float.parseFloat(salesParts1[1]);
                int countryId = Integer.parseInt(salesParts1[2]);

                Float revenue = preço*quantidade;
                
                return revenue.toString();
            }
            else{
                int quantidade = Integer.parseInt(salesParts[0]);
                float preço = Float.parseFloat(salesParts[1]);
                int countryId = Integer.parseInt(salesParts[2]);

                Float revenue = preço*quantidade;

                Float lastRevenue = Float.parseFloat(v1);
                
                itemprofit = revenue + lastRevenue;
                System.out.println(itemprofit.toString());
                return itemprofit.toString();
            }
        });


        
        outlinesS.toStream().to("Results");


        /*KStream<String, String> purchasesStream = builder.stream("Purchases");
        KTable<String, Long> outlinesP = purchasesStream.groupByKey().count();
        outlinesP.toStream().to("Results");*/


        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }
}