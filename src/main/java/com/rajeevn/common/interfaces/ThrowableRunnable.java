package com.rajeevn.common.interfaces;

import java.util.function.Consumer;

/**
 * It is {@link Runnable} with ability to throw specified exception.
 *
 * @author Rajeev Naik
 * @since 2018/03/04
 */
@FunctionalInterface
public interface ThrowableRunnable
{
    void run() throws Exception;

    default Runnable doThrow()
    {
        return (() ->
        {
            try
            {
                run();
            } catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Handler to perform when the operation represented by this interface throws exception.
     *
     * @param onThrow
     * @return
     */
    default Runnable onThrow(Consumer<Exception> onThrow)
    {
        return (() ->
        {
            try
            {
                run();
            } catch (Exception e)
            {
                onThrow.accept(e);
            }
        });
    }

    /**
     * Handler to perform when the operation represented by this interface throws exception.
     *
     * @param onThrow
     * @return
     */
    default ThrowableRunnable onThrowThrowable(ThrowableConsumer<Exception> onThrow)
    {
        return (() ->
        {
            try
            {
                run();
            } catch (Exception e)
            {
                onThrow.accept(e);
            }
        });
    }
}
