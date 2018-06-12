package blockchain.medical_card.dto;


import blockchain.medical_card.utils.JsonUtils;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Data
public class RecordBlockChain {
    private LinkedList<IllnessRecordBlock> blocks = new LinkedList<>();
    private int DIFFICULTY = 4;

    public RecordBlockChain(int DIFFICULTY) {
        this.DIFFICULTY = DIFFICULTY;
        IllnessRecordBlock genesisBlock = createGenesisBlock();
        mineBlock(genesisBlock);
        addBlock(genesisBlock);
    }

    public RecordBlockChain() {
        IllnessRecordBlock genesisBlock = createGenesisBlock();
        mineBlock(genesisBlock);
        addBlock(genesisBlock);
    }

    public void mineBlock(IllnessRecordBlock block) {
        block.mineBlock(getTargetHashPrefix());
    }

    public String getTargetHashPrefix() {
        return new String(new char[DIFFICULTY]).replace('\0', '0');
    }

    public String getPreviousHash() {
        return getLatestBlock() != null ? getLatestBlock().getHash() : "";
    }
    public boolean addBlock(IllnessRecordBlock block) {
        block.setPreviousHash(getLatestBlock() != null ? getLatestBlock().getHash() : "");
        //block.setHash(block.calculateHash());
        return blocks.add(block);
    }

    public IllnessRecordBlock getLatestBlock() {
        return CollectionUtils.isNotEmpty(blocks) ? blocks.getLast() : null;
    }

    public IllnessRecordBlock createGenesisBlock() {
        IllnessRecordBlock genesisBlock = new IllnessRecordBlock(Instant.MIN.getEpochSecond(), null, "0");
        genesisBlock.setIsGenesisBlock(Boolean.TRUE);
        return genesisBlock;
    }

    public boolean isChainValid() {
        for (int i = 1; i < blocks.size(); i++) {
            IllnessRecordBlock currentBlock = blocks.get(i);
            IllnessRecordBlock previousBlock = blocks.get(i - 1);

            if (!StringUtils.equals(currentBlock.getHash(), currentBlock.calculateHash()))
                return false;

            if (!StringUtils.equals(currentBlock.getPreviousHash(), previousBlock.getHash()))
                return false;
        }
        return true;
    }
}
