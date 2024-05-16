package memory;

import java.time.Instant;

public class CacheValue {
    private final String value;
    private final Instant addedTime;
    private final Integer expirationTime;

    public CacheValue(String value, Integer expirationTime) {
        this.addedTime = Instant.now();
        this.value = value;
        this.expirationTime = expirationTime;
    }

    public String getValue() {
        return value;
    }

    public boolean isExpired() {
        if (expirationTime == -1) {
            return false;
        }
        return Instant.now().isAfter(addedTime.plusMillis(expirationTime));
    }
}
