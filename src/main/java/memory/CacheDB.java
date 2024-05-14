package memory;
import java.util.HashMap;
import java.util.Map;

public class CacheDB implements Database {
    Map<String, CacheValue> cache = new HashMap<>();

    @Override
    public void set(String key, CacheValue value) {
        cache.put(key, value);
    }

    @Override
    public String get(String key) {
        CacheValue value = cache.getOrDefault(key, null);
        if (value != null && value.isExpired()) {
            cache.remove(key);
            return null;
        }
        return value == null ? null : value.getValue();
    }
}