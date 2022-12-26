package ca.sait.backup.model.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Email {
    private String to;
    private String subject;
    private String message;
}
