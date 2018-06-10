package blockchain.medical_card.services.dao;

import blockchain.medical_card.api.FileService;
import blockchain.medical_card.api.dao.RecordDao;
import blockchain.medical_card.configuration.PropertiesConfig;
import blockchain.medical_card.dto.IllnessRecordDTO;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;
import blockchain.medical_card.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class RecordDaoImpl implements RecordDao {

    @Autowired
    private FileService fileService;

    @Autowired
    private PropertiesConfig propertiesConfig;

    private File tempFile;

    @PostConstruct
    public void init() throws IOException {
        tempFile = File.createTempFile("temp_records", ".json");
    }

    @PreDestroy
    public void destroy() {
        if (!tempFile.delete()) {
            tempFile.deleteOnExit();
        }
    }

    @Override
    public List<IllnessRecordDTO> getTempRecords() throws BlockChainAppException {
        return JsonUtils.fromJsonToType(
                fileService.readFromFile(getTempRecordsFileName()),
                new TypeToken<List<IllnessRecordDTO>>() {
                }.getType());
    }

    @Override
    public void addTempRecord(IllnessRecordDTO record) throws BlockChainAppException {
        List<IllnessRecordDTO> records = getTempRecords();
        if (CollectionUtils.isEmpty(records))
            records = new ArrayList<>();
        records.add(record);
        fileService.writeToFile(getTempRecordsFileName(), JsonUtils.toJson(records));
    }

    @Override
    public void clearTempRecordList() throws BlockChainAppException {
        fileService.writeToFile(getTempRecordsFileName(), JsonUtils.toJson(Collections.emptyList()));
    }

    private String getTempRecordsFileName() {
        //tempFile.getAbsolutePath();
        return propertiesConfig
                .getFilesLocation()
                .concat(File.separator)
                .concat(propertiesConfig.getTempRecordsFileName());
    }
}
