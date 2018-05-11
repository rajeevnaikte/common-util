package com.rajeevn.common.util;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class which keeps compiles patterns into cache to avoid repeated regex compilation.
 *
 * @author Rajeev Naik
 * @since 2018/05/11
 */
public final class CachedPattern
{
    private static final Map<String, Pattern> PATTERN_MAP = new ConcurrentHashMap<>();

    private CachedPattern()
    {
    }

    ;

    /**
     * Returns {@link Pattern} object from cache if previously compiled.
     *
     * @param regex
     * @return
     */
    public static Pattern getPattern(String regex)
    {
        Pattern pattern = PATTERN_MAP.get(regex);
        if (pattern == null)
        {
            pattern = Pattern.compile(regex);
            PATTERN_MAP.put(regex, pattern);
        }
        return pattern;
    }

    public static Matcher getMatcher(String pattern, String text)
    {
        return getPattern(pattern).matcher(text);
    }

    public static String replace(String pattern, String text, String replacement)
    {
        return getPattern(pattern).matcher(text).replaceFirst(replacement);
    }

    public static String replaceAll(String pattern, String text, String replacement)
    {
        return getPattern(pattern).matcher(text).replaceAll(replacement);
    }

    public static String[] split(String pattern, String text)
    {
        return getPattern(pattern).split(text);
    }

    public static boolean matches(String pattern, String text)
    {
        return getMatcher(pattern, text).matches();
    }

    public static <T extends Collection> T getAllMatched(String pattern, String text, Supplier<T> collSupplier)
    {
        T inst = collSupplier.get();
        Matcher matcher = getMatcher(pattern, text);
        while (matcher.find())
            inst.add(matcher.group(1));
        return inst;
    }
}
