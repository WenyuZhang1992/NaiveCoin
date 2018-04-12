import java.util.Objects;

/**
 * Created by JOHNNY on 4/12/18.
 *
 *
 *
 */
public class Block {

    private long index;
    private long timestamp;
    private String hash;
    private String previousHash;
    private String data;

    public Block(long index, long timestamp, String previousHash, String data) {
        this.index = index;
        this.timestamp = timestamp;
        this.previousHash = previousHash;
        this.data = data;

        // Calculate the Hash Value of the Block
        this.hash = String.valueOf(this.hashCode());
    }

    public long getIndex() { return this.index; }

    public long getTimestamp() { return this.timestamp; }

    public String getHash() { return this.hash; }

    public String getData() { return this.data; }

}
