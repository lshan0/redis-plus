package handlers;

public class HandlerFactory {
    public static final String PING = "PING";
    public static final String ECHO = "ECHO";
    public static final String SET = "SET";
    public static final String GET = "GET";

    public static HandlerFactory getInstance() {
        return new HandlerFactory();
    }

    private HandlerFactory() {

    }

    public IHandler createHandler(String instruction) {
        if (PING.equalsIgnoreCase(instruction)) {
            return new PINGHandler();
        } else if (ECHO.equalsIgnoreCase(instruction)) {
            return new ECHOHandler();
        } else if (GET.equalsIgnoreCase(instruction)) {
            return new GETHandler();
        } else if (SET.equalsIgnoreCase(instruction)) {
            return new SETHandler();
        }
        throw new IllegalArgumentException();
    }
}
