package com.rajeevn.common.interfaces;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * It is {@link BiConsumer} with ability to throw specified exception.
 * @author Rajeev Naik
 * @since 2018/03/04
 * @param <T>
 * @param <U>
 */
@FunctionalInterface
public interface ThrowableBiConsumer<T, U>
{
    void accept(T t, U u) throws Exception;

    default BiConsumer<T, U> doThrow()
    {
        return ((T t, U u) ->
        {
            try
            {
                accept(t, u);
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
    default BiConsumer<T, U> onThrow(Consumer<Exception> onThrow)
    {
        return ((T t, U u) ->
        {
            try
            {
                accept(t, u);
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
    default ThrowableBiConsumer<T, U> onThrowThrowable(ThrowableConsumer<Exception> onThrow)
    {
        return ((T t, U u) ->
        {
            try
            {
                accept(t, u);
            } catch (Exception e)
            {
                onThrow.accept(e);
            }
        });
    }
}
