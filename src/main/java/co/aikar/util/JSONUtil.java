package co.aikar.util;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides Utility methods that assist with generating JSON Objects
 */
@SuppressWarnings({"rawtypes", "SuppressionAnnotation"})
public final class JSONUtil {
    private JSONUtil() {}

    /**
     * Creates a key/value "JSONPair" object
     * @param key The key.
     * @param obj The value object.
     * @return The resultant JSON key/value map.
     */
    public static JSONPair pair(String key, Object obj) {
        return new JSONPair(key, obj);
    }

    public static JSONPair pair(long key, Object obj) {
        return new JSONPair(String.valueOf(key), obj);
    }

    /**
     * Creates a new JSON object from multiple JsonPair key/value pairs
     * @param data The key-value map in JSON.
     * @return The Java equivalent for this map.
     */
    public static Map createObject(JSONPair... data) {
        return appendObjectData(new LinkedHashMap(), data);
    }

    /**
     * This appends multiple key/value Obj pairs into a JSON Object
     * @param parent The original map in Java.
     * @param data The key/value map in JSON to add to the original map.
     * @return The resultant map.
     */
    public static Map appendObjectData(Map parent, JSONPair... data) {
        for (JSONPair JSONPair : data) {
            parent.put(JSONPair.key, JSONPair.val);
        }
        return parent;
    }

    /**
     * This builds a JSON array from a set of data
     * @param data The Java objects to collect into the list.
     * @return The resultant list.
     */
    public static List toArray(Object... data) {
        return Lists.newArrayList(data);
    }

    /**
     * These help build a single JSON array using a mapper function
     * @param collection An array of objects of type E.
     * @param mapper The function to use to map the array of objects.
     * @param <E> The type of the objects.
     * @return The resultant list.
     */
    public static <E> List toArrayMapper(E[] collection, Function<E, Object> mapper) {
        return toArrayMapper(Lists.newArrayList(collection), mapper);
    }

    public static <E> List toArrayMapper(Iterable<E> collection, Function<E, Object> mapper) {
        List array = Lists.newArrayList();
        for (E e : collection) {
            Object object = mapper.apply(e);
            if (object != null) {
                array.add(object);
            }
        }
        return array;
    }

    /**
     * These help build a single JSON Object from a collection, using a mapper function
     * @param collection An array of objects of type E.
     * @param mapper The function to use to map the array of objects.
     * @param <E> The type of the objects.
     * @return The resultant list.
     */
    public static <E> Map toObjectMapper(E[] collection, Function<E, JSONPair> mapper) {
        return toObjectMapper(Lists.newArrayList(collection), mapper);
    }

    public static <E> Map toObjectMapper(Iterable<E> collection, Function<E, JSONPair> mapper) {
        Map object = Maps.newLinkedHashMap();
        for (E e : collection) {
            JSONPair JSONPair = mapper.apply(e);
            if (JSONPair != null) {
                object.put(JSONPair.key, JSONPair.val);
            }
        }
        return object;
    }

    /**
     * Simply stores a key and a value, used internally by many methods below.
     */
    @SuppressWarnings("PublicInnerClass")
    public static class JSONPair {
        final String key;
        final Object val;

        JSONPair(String key, Object val) {
            this.key = key;
            this.val = val;
        }
    }
}
