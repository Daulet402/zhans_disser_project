package blockchain.medical_card.dto.gui;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableItemDTO implements Serializable {
	private String id;
	private String visitTime;
	private String doctorFio;
	private String hospitalName;
	private String inspectionType;
	private String complaint;
}
