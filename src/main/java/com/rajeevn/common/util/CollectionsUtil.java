package com.rajeevn.common.util;

import com.rajeevn.common.dto.KeyVal;
import com.rajeevn.common.interfaces.ThrowableConsumer;
import com.rajeevn.common.interfaces.ThrowableFunction;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * Utility methods for operations on collections.
 *
 * @author Rajeev Naik
 * @since 2018/03/04
 */
public final class CollectionsUtil
{
    private CollectionsUtil()
    {
    }

    /**
     * Convert list to string by joining them with given delimiter.
     *
     * @param list
     * @param delim
     * @return
     */
    public static String listToString(List<String> list, String delim)
    {
        return ofNullable(list)
                .map(l -> l.stream().collect(joining(delim)))
                .orElse("");
    }

    /**
     * Convert string to stream by splitting with delimiter provided.
     *
     * @param s
     * @param delim
     * @return
     */
    public static Stream<String> stringToStream(String s, String delim)
    {
        return Stream.of(ofNullable(s).filter(s1 -> !s1.isEmpty()).map(s1 -> s1.split(delim)).orElse(new String[]{}));
    }

    /**
     * Convert string to list by splitting with delimiter provided.
     *
     * @param s
     * @param delim
     * @return
     */
    public static List<String> stringToList(String s, String delim)
    {
        return stringToStream(s, delim).collect(toList());
    }

    /**
     * Convert string to map. Uses #{@link #stringToMap(String, Supplier, Function, Function)}
     *
     * @param text
     * @param mapProvider
     * @return
     */
    public static Map<String, String> stringToMap(String text, Supplier<? extends Map<String, String>> mapProvider)
    {
        return stringToMap(text, mapProvider, null, null);
    }

    /**
     * Convert string to map. Uses #{@link #stringToEntryStream(String)} to get the stream,
     * and then collects into map provided.
     *
     * @param s
     * @param mapProvider provide map object in which items need to be filled
     * @param parseKey    optionally translate the string key
     * @param parseValue  optionally translate the string value
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> stringToMap(String s, Supplier<? extends Map<K, V>> mapProvider, Function<String, K> parseKey, Function<String, V> parseValue)
    {
        Function<Entry<String, String>, K> keyMapper = (parseKey == null ? kv -> (K) kv.getKey() : kv -> parseKey.apply(kv.getKey()));
        Function<Entry<String, String>, V> valueMapper = (parseValue == null ? kv -> (V) kv.getValue() : kv -> parseValue.apply(kv.getValue()));
        return stringToEntryStream(s)
                .collect(toMap(keyMapper, valueMapper, (k, v) -> v, mapProvider));
    }

    /**
     * Convert string to stream of {@link Entry}.
     * Accepted format for the string is - items are separated by ';'
     * and key-value are separated by ':'. E.g. 1:2;3:4;
     *
     * @param s
     * @return
     */
    public static Stream<Entry<String, String>> stringToEntryStream(String s)
    {
        if (s == null || s.isEmpty())
            return Stream.empty();
        return Stream.of(s.split(";"))
                .map(entry -> entry.split(":", 2))
                .map(kv -> new KeyVal<>(kv[0], (kv.length > 1 ? kv[1] : null)));
    }

    /**
     * Merge two maps by taking care of nested maps.
     * i.e. if value is a map, then it will be merged recursively.
     *
     * @param map1 will be updated with merged values
     * @param map2
     * @param <K>
     * @param <V>
     */
    public static <K, V> void mergeMaps(final Map<K, V> map1, final Map<K, V> map2)
    {
        map2.forEach((k, v) ->
        {
            V val = map1.get(k);
            if (val instanceof Map)
            {
                mergeMaps((Map) val, (Map) v);
            } else
            {
                map1.put(k, v);
            }
        });
    }

    /**
     * return empty list if given list is null.
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> toEmptyIfNull(List<T> list)
    {
        return ofNullable(list).orElse(emptyList());
    }

    /**
     * Return unmodifiable list if the given list is not null, or else return empty list
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> toUnmodifiableOrEmptyIfNull(List<T> list)
    {
        return ofNullable(list).map(Collections::unmodifiableList).orElse(emptyList());
    }

    /**
     * Check it given object is present in any of given lists.
     *
     * @param obj
     * @param lists
     * @param <T>
     * @return
     */
    public static <T> boolean presentInAny(T obj, List<T>... lists)
    {
        for (List<T> list : lists)
        {
            if (list.contains(obj))
                return true;
        }
        return false;
    }

    /**
     * Flatten given collection recursively (i.e. if it is nested collections) and return flattened stream.
     *
     * @param coll
     * @param <E>
     * @return
     */
    public static <T, NT, E> Stream<E> flatten(Collection<T> coll)
    {
        return coll.stream().flatMap(o ->
        {
            if (o instanceof Collection)
                return flatten((Collection<NT>) o);
            return Stream.of((E) o);
        });
    }

    /**
     * Find if there is any string in the array that starts with given string
     *
     * @param startsWithStr
     * @param arr
     * @return
     */
    public static boolean isAnyStartsWith(String startsWithStr, String[] arr)
    {
        for (String item : arr)
        {
            if (item.startsWith(startsWithStr))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Get first item which starts with given string
     *
     * @param startsWithStr
     * @param arr
     * @return
     */
    public static Optional<String> getFirstStartsWith(String startsWithStr, String[] arr)
    {
        for (String item : arr)
        {
            if (item.startsWith(startsWithStr))
            {
                return of(item);
            }
        }
        return empty();
    }

    /**
     * Find if any of the string in array is starting part of given text
     *
     * @param arr
     * @param text
     * @return
     */
    public static boolean isStartsWithAny(String[] arr, String text)
    {
        for (String item : arr)
        {
            if (!item.isEmpty() && text.startsWith(item))
            {
                return true;
            }
        }
        return false;
    }

    public static <T> void forEach(Enumeration<T> e, Consumer<T> operation)
    {
        while (e.hasMoreElements())
            operation.accept(e.nextElement());
    }

    public static <T> void forEach(Enumeration<T> e, ThrowableConsumer<T> operation) throws Exception
    {
        while (e.hasMoreElements())
            operation.accept(e.nextElement());
    }

    public static <T> Optional<T> findMatching(Enumeration<T> e, Function<T, Boolean> f)
    {
        while (e.hasMoreElements())
        {
            T el = e.nextElement();
            if (f.apply(el))
                return Optional.of(el);
        }
        return Optional.empty();
    }

    /**
     * This method will reduce need to write try catch in your code. When your lambda body will
     * throw exception, then RuntimeException will be thrown back.
     *
     * @param stream
     * @param action
     * @param <T>
     */
    public static <T> void forEach(Stream<T> stream, ThrowableConsumer<T> action)
    {
        stream.forEach(action.doThrow());
    }

    /**
     * This method will reduce need to write try catch in your code. When your lambda body will
     * throw exception, then RuntimeException will be thrown back.
     *
     * @param stream
     * @param mapper
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> Stream<R> map(Stream<T> stream, ThrowableFunction<T, R> mapper) throws Exception
    {
        return stream.map(mapper.doThrow());
    }
}
