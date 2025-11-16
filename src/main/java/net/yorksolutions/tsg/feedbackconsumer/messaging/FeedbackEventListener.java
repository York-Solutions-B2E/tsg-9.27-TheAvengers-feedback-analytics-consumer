package net.yorksolutions.tsg.feedbackconsumer.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FeedbackEventListener {

    private static final Logger log =
            LoggerFactory.getLogger(FeedbackEventListener.class);

    @KafkaListener(
            topics = "feedback-submitted",
            groupId = "feedback-analytics-consumer",
            containerFactory = "kafkaListenerContainerFactory"
    )

//    method Spring calls every time a new event arrives on the Kafka topic
    public void handleFeedbackSubmitted(FeedbackEvent event) {

//        log to show when an event is null. allows consumer to continue on bad input
        if (event == null) {
            log.warn("Received null feedback event — skipping");
            return;
        }

        // below code can be used to manually fail a test
//        if (event == null) {
//            throw new RuntimeException("Boom!");
//        }
        log.info(
                "Received feedback event id={} memberId={} provider={} rating={} submittedAt={}",
                event.id(),
                event.memberId(),
                event.providerName(),
                event.rating(),
                event.submittedAt()
        );
    }
}