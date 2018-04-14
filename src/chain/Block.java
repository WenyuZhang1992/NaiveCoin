package chain;

import chain.utilities.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

/**
 * Created by JOHNNY on 4/12/18.
 *
 * Define the basic structure of Block
 *
 * Only the most essential properties are included in block:
 * index : The height of the block in the blockchain
 * data: Any data that is included in the block.
 * timestamp: A timestamp
 * hash: A sha256 hash taken from the content of the block
 * previousHash: A reference to the hash of the previous block. This value explicitly defines the previous block.
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

        // Calculate the Hash Value of the chain.Block
        this.hash = calculateHash(this);
    }

    public long getIndex() { return this.index; }

    public long getTimestamp() { return this.timestamp; }

    public String getHash() { return this.hash; }

    public String getPreviousHash() { return this.previousHash; }

    public String getData() { return this.data; }

    public static String calculateHash(Block block){
        final long index = block.getIndex();
        final long timestamp = block.getTimestamp();
        final String previousHash = block.getPreviousHash();
        final String data = block.getData();

        String contents = String.format("%d%d%s%s", index, timestamp, previousHash, data);
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] hash = sha256.digest(contents.getBytes(StandardCharsets.UTF_8));
            return DatatypeConverter.printHexBinary(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}