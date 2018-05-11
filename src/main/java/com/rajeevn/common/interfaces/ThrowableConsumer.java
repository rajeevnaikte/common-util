package com.rajeevn.common.interfaces;

import java.util.function.Consumer;

/**
 * It is {@link Consumer} with ability to throw specified exception.
 * @author Rajeev Naik
 * @since 2018/03/04
 * @param <T>
 */
@FunctionalInterface
public interface ThrowableConsumer<T>
{
    void accept(T t) throws Exception;

    default Consumer<T> doThrow()
    {
        return ((T t) ->
        {
            try
            {
                accept(t);
            } catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Handler to perform when the operation represented by this interface throws exception.
     * @param onThrow
     * @return
     */
    default Consumer<T> onThrow(Consumer<Exception> onThrow)
    {
        return ((T t) ->
        {
            try
            {
                accept(t);
            }
            catch (Exception e)
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
    default ThrowableConsumer<T> onThrowThrowable(ThrowableConsumer<Exception> onThrow)
    {
        return ((T t) ->
        {
            try
            {
                accept(t);
            } catch (Exception e)
            {
                onThrow.accept(e);
            }
        });
    }
}
