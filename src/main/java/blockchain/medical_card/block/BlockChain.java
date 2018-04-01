package blockchain.medical_card.block;

import blockchain.medical_card.dto.exceptions.AddBlockException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockChain {

	private static final int DIFFICULTY = 5;
	private static final String HASH_TARGET = new String(new char[DIFFICULTY]).replace('\0', '0');
	private static final Double MINING_REWARD = 100d;

	private LinkedList<Block> blockChain = new LinkedList<>();
	private List<Transaction> pendingTransactions = new ArrayList<>();

	public Block getLastBlock() {
		return CollectionUtils.isNotEmpty(blockChain) ? blockChain.getLast() : null;
	}

	public boolean addBlock(Block block) {
		return isBlockValidInternal(block) ? blockChain.add(block) : false;
	}

	public static Double getMiningReward() {
		return MINING_REWARD;
	}

	public void minePendingTransactions(String miningRewardAddress) throws AddBlockException {
		Block latestBlock = getLastBlock();
		Block block = new Block(Instant.now().getEpochSecond(), pendingTransactions, latestBlock != null ? latestBlock.getHash() : "");
		block.setTimestamp(Instant.now().getEpochSecond());
		block.setTransactions(pendingTransactions);
		block.mineBlock(getDifficulty());
		if (!addBlock(block))
			throw new AddBlockException(String.format("Can not add new block with hash %s into blockchain", block.getHash()));

		pendingTransactions = Arrays.asList(new Transaction(null, miningRewardAddress, getMiningReward()));
	}

	public void createTransactions(List<Transaction> transactions) {
		pendingTransactions.addAll(transactions);
	}

	public Double getBalanceOfAddress(String address) {
		double balance = 0d;

		if (CollectionUtils.isEmpty(blockChain))
			return balance;

		for (Block block : blockChain) {
			if (CollectionUtils.isNotEmpty(block.getTransactions()))
				for (Transaction transaction : block.getTransactions()) {
					if (StringUtils.equals(transaction.getFromAddress(), address))
						balance -= transaction.getAmount();

					if (StringUtils.equals(transaction.getToAddress(), address))
						balance += transaction.getAmount();
				}
		}
		return balance;
	}

	public String getHashTarget() {
		return HASH_TARGET;
	}

	public int getDifficulty() {
		return DIFFICULTY;
	}

	public boolean isChainValid() {
		String hashTarget = getHashTarget();
		for (int i = 1; i < blockChain.size(); i++) {
			Block currentBlock = blockChain.get(i);
			Block previousBlock = blockChain.get(i - 1);
			if (!StringUtils.equals(currentBlock.getPreviousHash(), previousBlock.getHash()))
				return false;

			if (!StringUtils.equals(currentBlock.getHash(), currentBlock.calculateHash()))
				return false;

			if (!StringUtils.equals(StringUtils.substring(currentBlock.getHash(), 0, getDifficulty()), hashTarget))
				return false;
		}
		return true;
	}

	private boolean isBlockValidInternal(Block block) {
		String hashTarget = getHashTarget();
		Block latestBlock = getLastBlock();

		if (!StringUtils.equals(block.getHash(), block.calculateHash()))
			return false;

		if (!StringUtils.equals(StringUtils.substring(block.getHash(), 0, getDifficulty()), hashTarget))
			return false;

		if (Objects.nonNull(latestBlock) && !StringUtils.equals(latestBlock.getHash(), block.getPreviousHash()))
			return false;

		return true;
	}
}
