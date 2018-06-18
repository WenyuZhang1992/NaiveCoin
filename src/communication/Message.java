package communication;

/**
 * Created by JOHNNY on 4/17/18.
 *
 * Define the Message used for P2P communication.
 */
enum MessageType {
    QUERY_LATEST,
    QUERY_ALL,
    RESPONSE_BLOCKCHAIN
}

public class Message<T> {

    private MessageType type;
    private T data;

    public Message(MessageType type, T data) {
        this.type = type;
        this.data = data;
    }
}
