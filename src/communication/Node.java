package communication;

import chain.utilities.NCLogger;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by JOHNNY on 4/17/18.
 *
 *  Define the structure of Node.
 *  Mainly for communication and local copy of BlockChain.
 */
public class Node {

    public static int PORT_REF = 2048;
    private String host = "localhost";
    private int port;
    private Socket socket;

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static Node NodeGenerator() {
        return new Node();
    }

    private Node() {
        try {
            port = PORT_REF++;
            socket = new Socket(host, port);
            NCLogger.setup();
            LOGGER.setLevel(Level.INFO);
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.info("Successfully generated a new Node.");
    }


}
