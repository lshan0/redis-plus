package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public final class ProtocolUtils {

    public static void writesBytes(OutputStream out, byte[] message, boolean hasEndl, boolean flush) throws IOException {
        if (message == null) {
            out.write("$-1\r\n".getBytes());
            out.flush();
            return;
        }
        out.write(("$" + message.length + "\r\n").getBytes());
        out.write(message);
        if (hasEndl) {
            out.write("\r\n".getBytes());
        }
        if (flush) {
            out.flush();
        }
    }

    public static void writesBytes(OutputStream out, byte[] message, boolean hasEndl) throws IOException {
        writesBytes(out, message, hasEndl, true);
    }

    public static void writesMessage(OutputStream out, String message, boolean hasEndl) throws IOException {
        writesBytes(out, message == null ? null : message.getBytes(), hasEndl, true);
    }

    public static void writesMessage(OutputStream out, String message)
            throws IOException {
        writesMessage(out, message, true);
    }

    public static byte[] readBytesMessage(InputStream in) throws IOException {
        String lengthLine = readStringLine(in);
        if (lengthLine.charAt(0) != '$') {
            throw new IllegalStateException();
        }
        int nrBytes = Integer.parseInt(lengthLine.substring(1));
        byte[] result = new byte[nrBytes];

        for (int i = 0; i < nrBytes; i++) {
            result[i] = (byte) in.read();
        }

        if (in.available() > 0) {
            in.mark(Integer.MAX_VALUE);
            byte nextByte = (byte)in.read();
            if (nextByte != '\n' && nextByte != '\r') {
                in.reset();
            } else {
                if (nextByte == '\r') {
                    if (((byte)in.read()) != '\n') {
                        in.reset();
                    }
                }
            }
        }
        return result;
    }

    public static String readStringLine(InputStream in) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        while (true) {
            Character current = (char)in.read();
            if (current == '\n') {
                break;
            }
            if (current == '\r' && in.available() > 0) {
                Character next = (char)in.read();
                if (next == '\n') {
                    break;
                }
                stringBuilder.append(current);
                stringBuilder.append(next);
            } else {
                stringBuilder.append(current);
            }
        }
        return stringBuilder.toString();
    }

    public static String readString(InputStream in) throws IOException {
        return new String(readBytesMessage(in), StandardCharsets.UTF_8);
    }

    public static void writePackage(OutputStream out, List<byte[]> messages, boolean finishWithEndline) throws IOException {
        out.write(("*" + messages.size() + "\r\n").getBytes());
        for (int i = 0; i < messages.size(); ++i) {
            writesBytes(out, messages.get(i),
                    i < messages.size() - 1 || finishWithEndline, false);
        }
        out.flush();
    }

    public static void writeStringPackage(OutputStream out, List<String> messages, boolean finishWithEndline) throws IOException {
        writePackage(out, messages.stream().map(String::getBytes).toList(),
                finishWithEndline);
    }
}
