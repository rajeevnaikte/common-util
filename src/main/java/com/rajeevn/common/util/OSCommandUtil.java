package com.rajeevn.common.util;

import com.rajeevn.common.interfaces.ThrowableRunnable;

import java.io.InputStream;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.rajeevn.common.util.IOUtil.processResource;
import static com.rajeevn.common.util.IOUtil.readLine;
import static java.lang.Runtime.getRuntime;

/**
 * Utility class for executing OS commands.
 *
 * @author Rajeev Naik
 * @since 2018/05/11
 */
public final class OSCommandUtil
{
    private OSCommandUtil()
    {
    }

    public static void execCommand(String command, Consumer<String> logger) throws Exception
    {
        Process process = getRuntime().exec(command);
        Function<InputStream, Runnable> console = (InputStream in) ->
                ((ThrowableRunnable) () -> processResource(() -> in, (is) -> readLine(is, logger::accept)))
                        .onThrow((e) -> logger.accept(e.getMessage()));
        new Thread(console.apply(process.getErrorStream())).start();
        new Thread(console.apply(process.getInputStream())).start();
        process.waitFor();
    }
}
