package chain;

import chain.utilities.NCLogger;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by JOHNNY on 4/13/18.
 *
 * Define the basic structure of BlockChain
 */

// TODO: Add logging message
public class BlockChain {

    private HashMap<String, Block> blockMap;
    private String genesisHashValue;
    private String lastBlockHash;
    private String initialHashValue;

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public BlockChain() {

        // Setup the logger
        try {
            NCLogger.setup();
            LOGGER.setLevel(Level.INFO);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blockMap = new HashMap();
        initialHashValue = "816534932c2b7154836da6afc367695e6337db8a921823784c14378abed4f7d7";
        genesisHashValue = generateGenesisBlock();
        lastBlockHash = genesisHashValue;

        LOGGER.info("Successfully generated a new BlockChain");
    }

    private String generateGenesisBlock() {
        Block genesis = new Block((long)0, System.currentTimeMillis(), initialHashValue, "Genesis Block");
        blockMap.put(genesis.getHash(), genesis);

        LOGGER.info("Successfully generated genesis block");
        return genesis.getHash();
    }

    public Block generateNextBlock(String data) {
        Block previousBlock = blockMap.get(lastBlockHash);
        Block newBlock = new Block(previousBlock.getIndex() + 1, System.currentTimeMillis(), lastBlockHash, data);
        return newBlock;
    }

    public void addBlockToChain(Block potentialBlock) {
        if (isValidBlock(potentialBlock)) {
            blockMap.put(lastBlockHash, potentialBlock);
            lastBlockHash = potentialBlock.getHash();
            LOGGER.severe(String.format("Successfully added a new block with index %d", potentialBlock.getIndex()));
        }
    }

    private boolean isValidBlock(Block potentialBlock) {
        Block lastBlock = blockMap.get(lastBlockHash);

        // Check the index
        if (potentialBlock.getIndex() != lastBlock.getIndex() + 1) {
            LOGGER.severe(String.format("Failed to add new block: Invalid Index"));
            return false;
        } else if (!lastBlockHash.equals(potentialBlock.getPreviousHash())) {
            LOGGER.severe(String.format("Failed to add new block:: Invalid Previous Hash Value"));
            return false;
        } else if (!potentialBlock.getHash().equals(Block.calculateHash(potentialBlock))) {
            LOGGER.severe(String.format("Failed to add new block: Invalid Hash Value"));
            return false;
        }

        return true;
    }

    private boolean isValidChain() {
        return true;
    }

    // For test only
    public static void main(String[] argv) {
        BlockChain testChain = new BlockChain();

        Block testBlock = testChain.generateNextBlock("Test Block");
        testChain.isValidBlock(testBlock);
    }
}
