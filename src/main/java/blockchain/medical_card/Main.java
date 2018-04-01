package blockchain.medical_card;

import blockchain.medical_card.api.FileService;
import blockchain.medical_card.block.Block;
import blockchain.medical_card.block.BlockChain;
import blockchain.medical_card.block.Transaction;
import blockchain.medical_card.services.FileServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.Arrays;
import java.util.LinkedList;

public class Main {
	public static final int DIFFICULTY = 2;
	public static final String FILE_NAME = "C:\\blockchain\\blocks\\blocks.json";

	public static void main(String[] args) throws Exception {
		Gson gson = new Gson();
		BlockChain blockChain = new BlockChain();

		Block block1 = new Block(System.currentTimeMillis(), null, "0");
		//block1.mineBlock(blockChain.getDifficulty());

		Block block2 = new Block(System.currentTimeMillis(), null, block1.getHash());
		//block2.mineBlock(blockChain.getDifficulty());

		Block block3 = new Block(System.currentTimeMillis(), null, block2.getHash());
		//block3.mineBlock(blockChain.getDifficulty());

		blockChain.addBlock(block1);
		blockChain.addBlock(block2);
		blockChain.addBlock(block3);

		blockChain.createTransactions(Arrays.asList(new Transaction("address1", "address2", 100d)));
		blockChain.createTransactions(Arrays.asList(new Transaction("address2", "address1", 50d)));
		blockChain.createTransactions(Arrays.asList(new Transaction("address2", "address3", 20d)));
		blockChain.createTransactions(Arrays.asList(new Transaction("address1", "address1", 10d)));
		blockChain.minePendingTransactions("miners-address");


		System.out.println("address1 has balance " + blockChain.getBalanceOfAddress("address1"));
		System.out.println("address2 has balance " + blockChain.getBalanceOfAddress("address2"));
		System.out.println("address3 has balance " + blockChain.getBalanceOfAddress("address3"));
		System.out.println("miners-address has balance " + blockChain.getBalanceOfAddress("miners-address"));
		blockChain.minePendingTransactions("miners-address");
		System.out.println("miners-address has balance " + blockChain.getBalanceOfAddress("miners-address"));
		System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(blockChain.getBlockChain()));
		System.out.println();
		System.out.println(blockChain.isChainValid());

		FileService fileService = new FileServiceImpl();

		fileService.writeToFile(FILE_NAME, gson.toJson(blockChain.getBlockChain()));

		String blockChainJson = fileService.readFromFile(FILE_NAME);
		LinkedList<Block> blockChain2 = gson.fromJson(blockChainJson, new TypeToken<LinkedList<Block>>() {
		}.getType());

		System.out.println();
		//gson.fromJson(blockChainJson, LinkedList<Block>.getClass())
		//System.out.println(isChainValid(Arrays.asList(block1, block2, block3)));
	}
}
