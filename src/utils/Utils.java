package utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Utils {

    public static  <K, V> K getRandomKey(Map<K, V> map) {
        List<K> keyList = List.copyOf(map.keySet());
        Random random = new Random();
        int randomIndex = random.nextInt(keyList.size());
        return keyList.get(randomIndex);
    }

}
