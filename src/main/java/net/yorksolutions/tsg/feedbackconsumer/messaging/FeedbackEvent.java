package net.yorksolutions.tsg.feedbackconsumer.messaging;

import java.time.OffsetDateTime;
import java.util.UUID;

public class FeedbackEvent {

    private UUID id;
    private String memberId;
    private String providerName;
    private Integer rating;
    private String comment;
    private OffsetDateTime submittedAt;

    public FeedbackEvent() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public OffsetDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(OffsetDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    @Override
    public String toString() {
        return "FeedbackEvent{" +
                "id=" + id +
                ", memberId='" + memberId + '\'' +
                ", providerName='" + providerName + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", submittedAt=" + submittedAt +
                '}';
    }

}
