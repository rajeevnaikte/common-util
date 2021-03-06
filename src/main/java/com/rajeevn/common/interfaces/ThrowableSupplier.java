package com.rajeevn.common.interfaces;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * It is {@link Supplier} with ability to throw specified exception.
 *
 * @param <T>
 * @author Rajeev Naik
 * @since 2018/03/04
 */
@FunctionalInterface
public interface ThrowableSupplier<T>
{
    T get() throws Exception;

    default Supplier<T> doThrow()
    {
        return (() ->
        {
            try
            {
                return get();
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
    default Supplier<T> onThrow(Consumer<Exception> onThrow)
    {
        return (() ->
        {
            try
            {
                return get();
            } catch (Exception e)
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
    default ThrowableSupplier<T> onThrowThrowable(ThrowableConsumer<Exception> onThrow)
    {
        return (() ->
        {
            try
            {
                return get();
            } catch (Exception e)
            {
                onThrow.accept(e);
            }
            return null;
        });
    }
}
