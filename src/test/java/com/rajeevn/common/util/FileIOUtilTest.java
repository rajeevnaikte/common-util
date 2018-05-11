package com.rajeevn.common.util;

import org.junit.Test;

import java.io.File;

import static com.rajeevn.common.util.FileIOUtil.getFileExt;
import static com.rajeevn.common.util.FileIOUtil.stripExt;
import static org.junit.Assert.assertEquals;

public class FileIOUtilTest
{
    @Test
    public void testStripExt()
    {
        assertEquals("hello", stripExt("hello"));
        assertEquals("hello", stripExt("hello.txt"));
    }

    @Test
    public void testGetExt()
    {
        assertEquals("", getFileExt(new File("hello")));
        assertEquals("txt", getFileExt(new File("hello.txt")));
    }
}
