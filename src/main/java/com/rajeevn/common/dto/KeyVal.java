package com.rajeevn.common.dto;

import java.util.Map;

/**
 * Data transfer object to hold key-value pair.
 *
 * @author Rajeev Naik
 * @since 2018/05/11
 */
public class KeyVal<K, V> implements Map.Entry<K, V>
{
    private K key;
    private V value;

    public KeyVal()
    {
    }

    public KeyVal(K key, V value)
    {
        this.key = key;
        this.value = value;
    }

    public K getKey()
    {
        return key;
    }

    public void setKey(K key)
    {
        this.key = key;
    }

    public V getValue()
    {
        return value;
    }

    public V setValue(V value)
    {
        V old = this.value;
        this.value = value;
        return old;
    }
}
