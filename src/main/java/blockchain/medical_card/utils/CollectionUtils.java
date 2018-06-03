package blockchain.medical_card.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class CollectionUtils {

	public static <T> ObservableList<T> convertToObservableList(List<T> list) {
		return org.apache.commons.collections4.CollectionUtils.isNotEmpty(list) ? FXCollections.observableList(list) : FXCollections.emptyObservableList();
	}

	public static <T> List<T> defaultIfEmpty(List<T> list, List<T> defaultList) {
		return org.apache.commons.collections4.CollectionUtils.isNotEmpty(list) ? list : defaultList;
	}
}
