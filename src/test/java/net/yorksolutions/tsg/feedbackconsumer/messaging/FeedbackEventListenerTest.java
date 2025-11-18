//package net.yorksolutions.tsg.feedbackconsumer.messaging;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.time.OffsetDateTime;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class FeedbackEventListenerTest {
//
//    private FeedbackEventListener listener;
//
//    @BeforeEach
//    void setUp() {
//        listener = new FeedbackEventListener();
//    }
//
//    @Test
//    void handleFeedbackSubmitted_whenValidEvent_doesNotThrow() {
//        // Arrange
//        FeedbackEvent event = new FeedbackEvent(
//                UUID.randomUUID(),
//                "member-123",
//                "Dr. Strange",
//                5,
//                "Magical service!",
//                OffsetDateTime.now()
//        );
//
//        // Act + Assert
//        assertDoesNotThrow(() ->
//                listener.handleFeedbackSubmitted(event)
//        );
//    }
//
//    @Test
//    void handleFeedbackSubmitted_whenNullEvent_doesNotThrow() {
//        assertDoesNotThrow(() ->
//                listener.handleFeedbackSubmitted(null)
//        );
//    }
//
//}
package net.yorksolutions.tsg.feedbackconsumer.messaging;

import org.apache.avro.generic.GenericRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class FeedbackEventListenerTest {

    private FeedbackEventListener listener;

    @BeforeEach
    void setUp() {
        listener = new FeedbackEventListener();
    }

    @Test
    void handleFeedbackSubmitted_whenValidEvent_doesNotThrow() {
        // Mock a GenericRecord
        GenericRecord record = Mockito.mock(GenericRecord.class);

        Mockito.when(record.get("id")).thenReturn("123");
        Mockito.when(record.get("memberId")).thenReturn("member-1");
        Mockito.when(record.get("providerName")).thenReturn("Dr. Strange");
        Mockito.when(record.get("rating")).thenReturn(5);
        Mockito.when(record.get("comment")).thenReturn("Great!");
        Mockito.when(record.get("submittedAt")).thenReturn("2025-11-18T00:00:00Z");

        assertDoesNotThrow(() ->
                listener.handleFeedbackSubmitted(record)
        );
    }

    @Test
    void handleFeedbackSubmitted_whenNullEvent_doesNotThrow() {
        assertDoesNotThrow(() ->
                listener.handleFeedbackSubmitted(null)
        );
    }
}
