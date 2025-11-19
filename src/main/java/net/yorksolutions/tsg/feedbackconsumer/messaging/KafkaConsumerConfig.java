package net.yorksolutions.tsg.feedbackconsumer.messaging;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    private static final String DEFAULT_GROUP_ID = "feedback-analytics-consumer";

    @Bean
    public ConsumerFactory<String, GenericRecord> consumerFactory(KafkaProperties kafkaProperties) {
        // Start from Spring Boot's kafka.consumer.* properties
        Map<String, Object> props = kafkaProperties.buildConsumerProperties();

        // Make sure the important ones are set explicitly
        props.put(ConsumerConfig.GROUP_ID_CONFIG, DEFAULT_GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, FeedbackEvent.class);


        // We want GenericRecord, not specific Avro classes
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, false);

        // schema.registry.url is coming from SPRING_KAFKA_PROPERTIES_SCHEMA_REGISTRY_URL
        // via Spring Boot, so we don't need to hard-code it here.

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, GenericRecord> kafkaListenerContainerFactory(
            ConsumerFactory<String, GenericRecord> consumerFactory
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, GenericRecord>();
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.BATCH);
        return factory;
    }
}
