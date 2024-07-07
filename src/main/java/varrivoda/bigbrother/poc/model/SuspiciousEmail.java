package varrivoda.bigbrother.poc.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SuspiciousEmail {
    private String text;
}
