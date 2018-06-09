package blockchain.medical_card.services;

import blockchain.medical_card.api.Block;
import blockchain.medical_card.api.CommunicationService;
import blockchain.medical_card.api.dao.CommonDaoService;
import blockchain.medical_card.dto.HostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
            if (String.valueOf(host.getPort()).equals(port))
                continue;

            restTemplate.postForEntity(String.format("http://%s:%s/blocks/new", host.getAddress(), host.getPort()), requestEntity, String.class);
            System.out.println("sent to " + host.getPort());
        }
    }
}
