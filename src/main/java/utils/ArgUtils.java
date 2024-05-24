package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ArgUtils {
    public static String toRedisProtocolMessage(String message) {
        return toRedisProtocolMessage(message, true);
    }

    public static String toRedisProtocolMessage(String message, boolean hasEndLine) {
        if (message == null) return "$-1\r\n";

        return "$" + message.length() + "\r\n" + message + (hasEndLine ? "\r\n" : "");
    }

    public static String readArg(BufferedReader reader) throws IOException {
        String numberBytesLine = reader.readLine();
        if (numberBytesLine.charAt(0) != '$') {
            throw new IllegalStateException();
        }
        return reader.readLine();
    }

    public static String toPackage(List<String> commands) {
        return "*" + commands.size() + "\r\n" +
                commands.stream()
                        .map(command -> toRedisProtocolMessage(command, false))
                        .collect(Collectors.joining("\r\n")) +
                "\r\n";
    }
}
