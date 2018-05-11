package com.rajeevn.common.interfaces;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * It is {@link Function} with ability to throw specified exception.
 * @author Rajeev Naik
 * @since 2018/03/04
 * @param <T>
 * @param <R>
 */
@FunctionalInterface
public interface ThrowableFunction<T, R>
{
    R apply(T t) throws Exception;

    default Function<T, R> doThrow()
    {
        return ((T t) ->
        {
            try
            {
                return apply(t);
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
    default Function<T, R> onThrow(Consumer<Exception> onThrow)
    {
        return ((T t) ->
        {
            try
            {
                return apply(t);
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
    default ThrowableFunction<T, R> onThrowThrowable(ThrowableConsumer<Exception> onThrow)
    {
        return ((T t) ->
        {
            try
            {
                return apply(t);
            } catch (Exception e)
            {
                onThrow.accept(e);
            }
            return null;
        });
    }
}
