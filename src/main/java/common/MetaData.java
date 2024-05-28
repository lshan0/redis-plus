package common;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MetaData {

    private Map<Info, Object> info = new HashMap<>();

    public void setInfo(Info info, Object value) {
        this.info.put(info, value);
    }

    public Object getInfo(Info info) {
        return this.info.get(info);
    }

    @Override
    public String toString() {
        return this.info.entrySet()
                .stream()
                .map(entry
                        -> entry.getKey().toString() + ":" +
                        entry.getValue().toString())
                .collect(Collectors.joining("\r\n"));
    }
}
