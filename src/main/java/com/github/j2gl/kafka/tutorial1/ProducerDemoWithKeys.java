package com.github.j2gl.kafka.tutorial1;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerDemoWithKeys {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        final Logger logger = LoggerFactory.getLogger(ProducerDemoWithKeys.class);

        final String bootstrapServers = "127.0.0.1:9092";

        // create producer properties
        final Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create the producer
        final KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        final String topic = "first_topic";

        for (int i = 0; i < 10; i++) {

            final String key = "id_" + i;
            final String value = "Hello world " + i;

            ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);

            logger.info("Key      : " + key);
            producer.send(record, (recordMetadata, exception) -> {
                if (exception == null) {
                    logger.info("Received new metadata. \n" +
                            "Topic    : " + recordMetadata.topic() + "\n" +
                            "Partition: " + recordMetadata.partition() + "\n" +
                            "Offset   : " + recordMetadata.offset() + "\n" +
                            "Timestamp: " + recordMetadata.timestamp() + "\n");
                } else {
                    logger.error("Error while producing", exception);
                }
            }).get(); // Block send to make it synchronous, not for prod
        }

        producer.flush();
        producer.close();
    }

}
