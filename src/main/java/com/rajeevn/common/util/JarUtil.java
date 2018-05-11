package com.rajeevn.common.util;

import com.rajeevn.common.interfaces.ThrowableFunction;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;

import static com.rajeevn.common.util.CachedPattern.getMatcher;

/**
 * Utility class for operations on jar.
 *
 * @author Rajeev Naik
 * @since 2018/05/11
 */
public final class JarUtil
{
    private JarUtil()
    {
    }

    public static String zipEntryToPackage(ZipEntry entry)
    {
        return getMatcher("[\\\\/]", entry.getName()).replaceAll(".");
    }

    public static ClassLoader getClassLoader(final String path)
    {
        URL[] jars;
        File root = new File(path);
        Function<File, URL> fileToUrl = ((ThrowableFunction<File, URL>) (file -> file.toURI().toURL())).doThrow();
        if (root.isDirectory())
        {
            jars = Stream.of(root.listFiles()).map(fileToUrl).toArray(URL[]::new);
        } else
        {
            jars = new URL[]{fileToUrl.apply(root)};
        }
        return new URLClassLoader(jars);
    }
}
