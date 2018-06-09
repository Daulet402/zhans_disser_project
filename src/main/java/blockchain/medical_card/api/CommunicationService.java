package blockchain.medical_card.api;

import blockchain.medical_card.dto.CheckResultDTO;

import java.util.List;

public interface CommunicationService {

    void notifyAllNodes(Block block);

    List<CheckResultDTO> notifyAllNodesToCheck(Block block);

    String getCurrent();
}
