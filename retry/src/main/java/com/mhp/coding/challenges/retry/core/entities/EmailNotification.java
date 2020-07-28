package com.mhp.coding.challenges.retry.core.entities;

import lombok.*;
import org.springframework.stereotype.Component;
import javax.validation.constraints.NotBlank;

@Data
@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmailNotification {

    @NotBlank
    public String recipient;

    @NotBlank
    public String subject;

    @NotBlank
    public String text;

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
