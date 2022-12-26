package ca.sait.backup.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {
        private String email;
        private String title;
        private String message;
        private String username;

}
