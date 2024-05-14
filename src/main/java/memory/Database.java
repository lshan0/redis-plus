package memory;

public interface Database {
    void set(String key, String value);
    String get(String key);
}
