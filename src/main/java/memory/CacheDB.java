package memory;
import java.util.HashMap;
import java.util.Map;

public class CacheDB implements Database {
    Map<String, String> cache = new HashMap<>();

    @Override
    public void set(String key, String value) {
        cache.put(key, value);
    }

    @Override
    public String get(String key) {
        return cache.getOrDefault(key, null);
    }
}