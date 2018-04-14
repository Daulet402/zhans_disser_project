package blockchain.medical_card.dto.gui;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeItemDTO<T> {
	private T object;
	private String name;
	private String parentName;

	@Override
	public String toString() {
		return getName();
	}
}
