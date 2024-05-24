package common;

import java.util.Locale;

public enum Info {
    ROLE;

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
