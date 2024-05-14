package utils;

import java.io.BufferedReader;
import java.io.IOException;

public class ArgUtils {
    public static String toRedisProtocolMessage(String message) {
        if (message == null) {
            return "$-1\r\n";
        }
        return "$" + message.length() + "\r\n" + message + "\r\n";
    }

    public static String readArg(BufferedReader reader) throws IOException {
        String numberBytesLine = reader.readLine();
        if (numberBytesLine.charAt(0) != '$') {
            throw new IllegalStateException();
        }
        return reader.readLine();
    }
}
