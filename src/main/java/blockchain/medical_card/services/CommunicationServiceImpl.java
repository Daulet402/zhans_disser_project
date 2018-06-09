package blockchain.medical_card.services;

import blockchain.medical_card.api.Block;
import blockchain.medical_card.api.CommunicationService;
import blockchain.medical_card.api.dao.CommonDaoService;
import blockchain.medical_card.dto.HostDTO;
import blockchain.medical_card.dto.ResultDTO;
import blockchain.medical_card.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CommunicationServiceImpl implements CommunicationService {

    @Autowired
    private CommonDaoService commonDaoService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<ResultDTO> notifyAllNodesToAdd(Block block) {
        return notifyInternal(Constants.ADD_BLOCK_URI, block);
    }

    @Override
    public List<ResultDTO> notifyAllNodesToCheck(Block block) {
        return notifyInternal(Constants.CHECK_BLOCK_URI, block);
    }

    @Override
    public String getCurrent() {
        return System.getProperties().getProperty("server.port");
    }

    private List<ResultDTO> notifyInternal(String uri, Block block) {
        String current = getCurrent();
        HttpHeaders httpHeaders = new HttpHeaders();
        List<ResultDTO> results = new ArrayList<>();
        List<HostDTO> hosts = commonDaoService.getOtherHosts();
        httpHeaders.put(Constants.FROM_HEADER_NAME, Arrays.asList(current));
        HttpEntity requestEntity = new HttpEntity(block, httpHeaders);

        for (HostDTO host : hosts) {
            if (StringUtils.equals(String.valueOf(host.getPort()), current))
                continue;
            try {
                ResponseEntity<ResultDTO> responseEntity = restTemplate.postForEntity(
                        String.format("http://%s:%s%s", host.getAddress(), host.getPort(), uri),
                        requestEntity,
                        ResultDTO.class
                );
                results.add(responseEntity.getBody());
            } catch (Exception e) {
                results.add(new ResultDTO(ResultDTO.ResultCode.ERROR, String.valueOf(host.getPort()), e.getMessage()));
                System.err.println(e.getMessage());
            }
        }
        return results;
    }
}
