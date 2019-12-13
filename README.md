# IS-Assignment-3

Run Zookeeper:
bin/zookeeper-server-start.sh config/zookeeper.properties

Run Kafka:
bin/kafka-server-start.sh config/server.properties

Create topic:
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic test

Show topics list:
bin/kafka-topics.sh --list --bootstrap-server localhost:9092

Create producer:
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test

Create consumer:
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning

Start Source Connector:
bin/connect-standalone.sh config/connect-standalone.properties config/connect-jdbc-source-is3-items.properties
bin/connect-standalone.sh config/connect-standalone.properties config/connect-jdbc-source-is3-countries.properties
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic DBInfo --from-beginning

Start Sink Connector:





Run java class with maven:
mvn clean install
mvn exec:java -Dexec.mainClass=data.SimpleProducer -Dexec.args="DBInfo"