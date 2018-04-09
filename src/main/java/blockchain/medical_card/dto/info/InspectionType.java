package blockchain.medical_card.dto.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@AllArgsConstructor
@NoArgsConstructor
public enum InspectionType {
	THERAPIST("Терапевт"),
	OPHTHALMOLOGIST("Окулист"),
	SURGEON("Хирург"),
	LOR("Лор");

	@Getter
	private String name;

	public static InspectionType getByName(String name) {
		for (InspectionType inspectionType : InspectionType.values()) {
			if (StringUtils.equalsIgnoreCase(inspectionType.getName(), name))
				return inspectionType;
		}
		return null;
	}
}
