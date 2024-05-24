package common;

import java.util.Locale;

public enum Role {
    MASTER,
    SLAVE;

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
