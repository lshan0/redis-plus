package common;

import java.util.Locale;

public enum Info {
    ROLE,
    MASTER_REPLID,
    MASTER_REPL_OFFSET;

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
