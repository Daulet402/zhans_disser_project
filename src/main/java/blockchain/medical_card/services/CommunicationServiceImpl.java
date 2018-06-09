package blockchain.medical_card.services;

import blockchain.medical_card.api.Block;
import blockchain.medical_card.api.CommunicationService;
import blockchain.medical_card.api.dao.CommonDaoService;
import blockchain.medical_card.dto.CheckResultDTO;
import blockchain.medical_card.dto.HostDTO;
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
    public void notifyAllNodes(Block block) {
        List<HostDTO> otherHosts = commonDaoService.getOtherHosts();
        String port = System.getProperties().getProperty("server.port");
        System.out.println("running in port " + port);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("from-host", Arrays.asList(port));

        HttpEntity requestEntity = new HttpEntity(block, httpHeaders);
        // restTemplate.postForEntity(String.format("http://%s:%s/blocks/new", "localhost", 8080), requestEntity, String.class);

        for (HostDTO host : otherHosts) {
           /* if (String.valueOf(host.getPort()).equals(port))
                continue;*/
            try {
                restTemplate.postForEntity(String.format("http://%s:%s/blocks/new", host.getAddress(), host.getPort()), requestEntity, String.class);
                System.out.println("success from " + host.getPort());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<CheckResultDTO> notifyAllNodesToCheck(Block block) {
        List<CheckResultDTO> results = new ArrayList<>();
        List<HostDTO> otherHosts = commonDaoService.getOtherHosts();
        String current = getCurrent();
        System.out.println("notify to check on " + current);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put(Constants.FROM_HEADER_NAME, Arrays.asList(current));
        HttpEntity requestEntity = new HttpEntity(block, httpHeaders);

        for (HostDTO host : otherHosts) {
            if (StringUtils.equals(String.valueOf(host.getPort()), current))
                continue;
            try {
                ResponseEntity<CheckResultDTO> responseEntity = restTemplate.postForEntity(String.format("http://%s:%s%s", host.getAddress(), host.getPort(), Constants.CHECK_BLOCK_URI), requestEntity, CheckResultDTO.class);
                results.add(responseEntity.getBody());
            } catch (Exception e) {
                results.add(new CheckResultDTO(CheckResultDTO.Result.ERROR, String.valueOf(host.getPort()), e.getMessage()));
                e.printStackTrace();
            }
        }
        return results;
    }
    

    @Override
    public String getCurrent() {
        return System.getProperties().getProperty("server.port");
    }
}
