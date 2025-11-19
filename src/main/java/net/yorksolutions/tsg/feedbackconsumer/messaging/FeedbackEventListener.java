package net.yorksolutions.tsg.feedbackconsumer.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class FeedbackEventListener {

    private static final Logger log =
            LoggerFactory.getLogger(FeedbackEventListener.class);

    private final ObjectMapper objectMapper;

    public FeedbackEventListener() {
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())     // <-- FIX FOR OffsetDateTime
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .enable(SerializationFeature.INDENT_OUTPUT); // pretty-print JSON
    }

    @KafkaListener(
            topics = "feedback-submitted",
            groupId = "feedback-analytics-consumer",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleFeedbackSubmitted(FeedbackEvent event) {

        // Log when event is null and skip
        if (event == null) {
            log.warn("Received null feedback event — skipping");
            return;
        }

        try {
            String json = objectMapper.writeValueAsString(event);

            log.info(
                    "\n====== RECEIVED FEEDBACK EVENT ======\n{}\n=====================================\n",
                    json
            );

        } catch (Exception e) {
            log.error("Failed to convert FeedbackEvent to JSON", e);
        }
    }
}
