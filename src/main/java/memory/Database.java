package memory;

public interface Database {
    void set(String key, CacheValue value);
    String get(String key);
}
