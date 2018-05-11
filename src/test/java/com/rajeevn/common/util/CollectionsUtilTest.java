package com.rajeevn.common.util;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.rajeevn.common.util.CollectionsUtil.forEach;
import static com.rajeevn.common.util.CollectionsUtil.map;
import static com.rajeevn.common.util.CollectionsUtil.stringToMap;
import static org.junit.Assert.assertEquals;

public class CollectionsUtilTest
{
    @Test
    public void testForEach()
    {
        try
        {
            forEach(Stream.of(new String[]{"1", "2"}), (i) ->
                    {
                        throw new Exception(i);
                    }
            );
        } catch (Exception e)
        {
            assertEquals("java.lang.Exception: 1", e.getMessage());
        }
    }

    @Test
    public void testForEachIOException()
    {
        try
        {
            forEach(Stream.of(new String[]{"1", "2"}), (i) ->
                    {
                        throw new IOException(i);
                    }
            );
        } catch (Exception e)
        {
            assertEquals("java.io.IOException: 1", e.getMessage());
        }
    }

    @Test
    public void testMapThrow()
    {
        try
        {
            map(Stream.of(new String[]{"1", "2", "a"}), Integer::parseInt).collect(Collectors.toList());
        } catch (Exception e)
        {
            assertEquals("java.lang.NumberFormatException: For input string: \"a\"", e.getMessage());
        }
    }

    @Test
    public void testMap() throws Exception
    {
        List<Integer> list = map(Stream.of(new String[]{"1", "2"}), Integer::parseInt).collect(Collectors.toList());
        assertEquals(1, list.get(0).intValue());
    }

    @Test
    public void testMapNoThrow() throws Exception
    {
        List<String> list = map(Stream.of(new String[]{"1", "2"}), (s) -> s).collect(Collectors.toList());
        assertEquals("1", list.get(0));
    }

    @Test
    public void testStringToMap()
    {
        Map<String, String> map = stringToMap("1:2;3:4;", HashMap::new);
        System.out.println(map);
        assertEquals("2", map.get("1"));
    }
}
