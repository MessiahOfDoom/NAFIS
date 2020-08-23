package de.schneider_oliver.nafis.utils;

import net.minecraft.util.Pair;

public class PairUtils {
	
	public static <K, V> Pair<K, V> pair(K key, V value){
		return new Pair<>(key, value);
	}

}
