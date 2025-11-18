package net.yorksolutions.tsg.feedbackconsumer.messaging;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, FeedbackEvent> consumerFactory(KafkaProperties kafkaProperties) {

        // loads Kafka consumer properties from application.yml
        Map<String, Object> consumerProps = kafkaProperties.buildConsumerProperties();

        //  Custom ObjectMapper with JavaTime support and strict schema validation
        // todo requires strict schema validation. check with Mike about this
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

        // json deserializer using the custom mapper
        JsonDeserializer<FeedbackEvent> valueDeserializer =
                new JsonDeserializer<>(FeedbackEvent.class, mapper);

        // keep the trusted packages restricted instead of using *
        valueDeserializer.addTrustedPackages("net.yorksolutions.tsg.feedbackconsumer.messaging");

        return new DefaultKafkaConsumerFactory<>(
                consumerProps,
                new StringDeserializer(),
                valueDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FeedbackEvent> kafkaListenerContainerFactory (ConsumerFactory<String, FeedbackEvent> consumerFactory)
    { var factory = new ConcurrentKafkaListenerContainerFactory<String, FeedbackEvent>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

}
