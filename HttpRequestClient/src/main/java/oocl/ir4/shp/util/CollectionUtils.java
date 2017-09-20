package oocl.ir4.shp.util;

import java.util.Collection;

/**
 * Created by CHENHY on 9/15/2017.
 */
public class CollectionUtils {

	public static boolean isNotEmpty(Collection collection) {
		return !isEmpty(collection);
	}
	public static boolean isEmpty(Collection collection) {
		return collection == null || collection.isEmpty();
	}

}
