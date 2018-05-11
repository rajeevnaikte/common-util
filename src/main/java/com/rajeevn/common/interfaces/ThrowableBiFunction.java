package com.rajeevn.common.interfaces;

import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * It is {@link BiFunction} with ability to throw specified exception.
 * @author Rajeev Naik
 * @since 2018/03/04
 * @param <T>
 * @param <U>
 * @param <R>
 */
@FunctionalInterface
public interface ThrowableBiFunction<T, U, R>
{
    R apply(T t, U u) throws Exception;

    default BiFunction<T, U, R> doThrow()
    {
        return ((T t, U u) ->
        {
            try
            {
                return apply(t, u);
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
    default BiFunction<T, U, R> onThrow(Consumer<Exception> onThrow)
    {
        return ((T t, U u) ->
        {
            try
            {
                return apply(t, u);
            }
            catch (Exception e)
            {
                onThrow.accept(e);
            }
            return null;
        });
    }

    /**
     * Handler to perform when the operation represented by this interface throws exception.
     *
     * @param onThrow
     * @return
     */
    default ThrowableBiFunction<T, U, R> onThrowThrowable(ThrowableConsumer<Exception> onThrow)
    {
        return ((T t, U u) ->
        {
            try
            {
                return apply(t, u);
            } catch (Exception e)
            {
                onThrow.accept(e);
            }
            return null;
        });
    }
}
