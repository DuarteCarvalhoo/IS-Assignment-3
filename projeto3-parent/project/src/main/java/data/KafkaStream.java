package data;

import java.security.Key;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.connect.source.SourceTask;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;

public class KafkaStream {
    private final static String BOOTSTRAP_SERVERS = "localhost:9092";

    public static void main(String[] args) {

        final Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "Stream");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String > salesStream = builder.stream("Sales");
        KStream<String, String> purchaseStream = builder.stream("Purchases");


        KTable<String, String> revenues = salesStream.mapValues((k, v) -> splitData(v)).groupByKey().reduce((v1, v2) -> {

            Double totalItemRevenue = Double.parseDouble(v1) + Double.parseDouble(v2);

            return totalItemRevenue.toString();
        });

        revenues.mapValues((k, v) -> "Revenue " + k + ": " + v).toStream().to("Results");


        KTable<String, String> expenses = purchaseStream.mapValues((k, v) -> splitData(v)).groupByKey().reduce((v1, v2) -> {

            Double totalItemExpenses = Double.parseDouble(v1) + Double.parseDouble(v2);

            return totalItemExpenses.toString();
        });

        expenses.mapValues((k, v) -> "Expenses " + k + ": " + v).toStream().to("Results");


        KTable<String, String> profit = revenues.join(expenses, (revenue,expense) -> calculateProfit(revenue,expense));

        profit.mapValues((k, v) -> "Profit " + k + ": " + v).toStream().to("Results");

        KTable<Windowed<String>, String> revenuesFinal = revenues.toStream().groupBy((k,v) -> { return "Total Revenue";}).windowedBy(TimeWindows.of(TimeUnit.MINUTES.toMillis(1))).reduce((v1,v2) -> {
            
            Double totalRevenue = Double.parseDouble(v1) + Double.parseDouble(v2);
            
            return totalRevenue.toString();
        });


        revenuesFinal.mapValues((k,v) -> "Total Revenues: " + v).toStream().to("Results");
        
        KTable<Windowed<String>, String> expensesFinal = expenses.toStream().groupBy((k,v) -> { return "Total Expense";}).windowedBy(TimeWindows.of(TimeUnit.MINUTES.toMillis(1))).reduce((v1,v2) -> {
            
            Double totalExpense = Double.parseDouble(v1) + Double.parseDouble(v2);
            
            return totalExpense.toString();
        });

        expensesFinal.mapValues((k,v) -> "Total Expenses: " + v).toStream().to("Results");

        KTable<String, String> profitFinal = profit.toStream().groupBy((k,v) -> { return "Total Expense";}).reduce((v1,v2) -> {
            
            Double totalProfit = Double.parseDouble(v1) + Double.parseDouble(v2);
            
            return totalProfit.toString();
        });

        profitFinal.mapValues((k,v) -> "Total Expenses: " + v).toStream().to("Results");
        

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }


    private static String calculateProfit(String revenue, String expense) {
        Double profit = Double.parseDouble(revenue) - Double.parseDouble(expense);
        return profit.toString();
    }

    private static String splitData(String v1) {
        String[] salesParts = v1.split(",");

        int quantidade = Integer.parseInt(salesParts[0]);
        Double preço = Double.parseDouble(salesParts[1]);

        Double money =  preço*quantidade;;

        return money.toString();
    }
}