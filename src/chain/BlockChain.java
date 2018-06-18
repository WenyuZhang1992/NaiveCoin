package chain;

import chain.utilities.NCLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by JOHNNY on 4/13/18.
 *
 * Define the basic structure of BlockChain
 */
public class BlockChain {

    private HashMap<String, Block> blockMap;
    private ArrayList<Block> blockArray;
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
        blockArray = new ArrayList<Block>();
        initialHashValue = "816534932c2b7154836da6afc367695e6337db8a921823784c14378abed4f7d7";
        genesisHashValue = generateGenesisBlock();
        lastBlockHash = genesisHashValue;

        LOGGER.info("Successfully generated a new BlockChain");
    }

    private String generateGenesisBlock() {
        Block genesis = new Block((long)0, System.currentTimeMillis(), initialHashValue, "Genesis Block");
        blockMap.put(genesis.getHash(), genesis);
        blockArray.add(genesis);

        LOGGER.info("Successfully generated genesis block");
        return genesis.getHash();
    }

    public Block generateNextBlock(String data) {
        Block previousBlock = blockMap.get(lastBlockHash);
        Block newBlock = new Block(previousBlock.getIndex() + 1, System.currentTimeMillis(), lastBlockHash, data);
        return newBlock;
    }

    public void addBlockToChain(Block potentialBlock) {
        if (isValidNextBlock(potentialBlock)) {
            blockMap.put(lastBlockHash, potentialBlock);
            blockArray.add(potentialBlock);
            lastBlockHash = potentialBlock.getHash();
            LOGGER.severe(String.format("Successfully added a new block with index %d", potentialBlock.getIndex()));
        }
    }

    private boolean isValidNextBlock(Block potentialBlock) {
        Block lastBlock = blockMap.get(lastBlockHash);

        if (potentialBlock.getIndex() != lastBlock.getIndex() + 1) {
            LOGGER.severe(String.format("Invalid Next Block: Invalid Index"));
            return false;
        } else if (!lastBlockHash.equals(potentialBlock.getPreviousHash())) {
            LOGGER.severe(String.format("Invalid Next Block: Invalid Previous Hash Value"));
            return false;
        } else if (!potentialBlock.getHash().equals(Block.calculateHash(potentialBlock))) {
            LOGGER.severe(String.format("Invalid Next Block: Invalid Hash Value"));
            return false;
        }

        return true;
    }

    private boolean isValidBlock(Block prev, Block curr) {
        if (curr.getIndex() != prev.getIndex() + 1) {
            LOGGER.severe(String.format("Invalid Block: Invalid Index %d", curr.getIndex()));
            return false;
        } else if (!curr.getPreviousHash().equals(prev.getHash())) {
            LOGGER.severe(String.format("Invalid Block: Invalid Previous Hash Value %s", curr.getPreviousHash()));
            return false;
        } else if (!curr.getHash().equals(Block.calculateHash(curr))) {
            LOGGER.severe(String.format("Invalid Block: Invalid Hash Value %s", curr.getHash()));
            return false;
        }

        return true;
    }

    private boolean isValidGenesisBlock(Block genesis) {
        if (!genesis.getPreviousHash().equals(initialHashValue)) {
            LOGGER.severe(String.format("Invalid Genesis Block: Invalid Previous Hash Value %s", genesis.getPreviousHash()));
            return false;
        } else if (genesis.getIndex() != 0) {
            LOGGER.severe(String.format("Invalid Genesis Block: Invalid Index %d", genesis.getIndex()));
            return false;
        } else if (genesisHashValue != genesis.getHash() || genesisHashValue != Block.calculateHash(genesis)) {
            LOGGER.severe(String.format("Invalid Genesis Block: Invalid Hash Value %s", genesis.getHash()));
            return false;
        }

        return true;
    }

    private boolean isValidChain(ArrayList<Block> chain) {
        if (!isValidGenesisBlock(chain.get(0))) {
            return false;
        }

        for (int i = 1; i < chain.size(); ++i) {
            if (!isValidBlock(chain.get(i - 1), chain.get(i))) {
                return false;
            }
        }

        return true;
    }

    public void replaceChain(ArrayList<Block> newChain) {
        if (isValidChain(newChain) && newChain.size() > blockArray.size()) {
            blockArray = newChain;
            for (Block block : newChain) {
                blockMap.put(block.getHash(), block);
            }
            LOGGER.severe(String.format("Replace with New Chain."));
        } else {
            LOGGER.severe(String.format("Invalid Chain."));
        }
    }

    // For test only
    public static void main(String[] argv) {
        BlockChain testChain = new BlockChain();

        Block testBlock = testChain.generateNextBlock("Test Block");
        testChain.isValidNextBlock(testBlock);
    }
}
