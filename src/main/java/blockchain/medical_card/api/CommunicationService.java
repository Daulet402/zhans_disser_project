package blockchain.medical_card.api;

import blockchain.medical_card.dto.ResultDTO;

import java.util.List;

public interface CommunicationService {

    List<ResultDTO> notifyAllNodesToAdd(Block block);

    List<ResultDTO> notifyAllNodesToCheck(Block block);

    String getCurrent();
}
