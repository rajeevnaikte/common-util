package com.rajeevn.common.util;

import com.rajeevn.common.interfaces.ThrowableConsumer;
import com.rajeevn.common.interfaces.ThrowableSupplier;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Utility class for operations on {@link InputStream} and {@link OutputStream}
 *
 * @author Rajeev Naik
 * @since 2018/05/11
 */
public final class IOUtil
{
    private IOUtil()
    {
    }

    public static <R extends AutoCloseable> void processResource(ThrowableSupplier<R> resourceSupplier, ThrowableConsumer<R> operation) throws Exception
    {
        try (R resource = resourceSupplier.get())
        {
            operation.accept(resource);
        }
    }

    public static void readLine(InputStream in, ThrowableConsumer<String> processLine) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = br.readLine()) != null)
            processLine.accept(line);
    }
}
