package com.rajeevn.common.util;

import java.io.File;
import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;

import static com.rajeevn.common.util.FileIOUtil.getFileExt;
import static com.rajeevn.common.util.FileIOUtil.processInputFile;
import static com.rajeevn.common.util.FileIOUtil.processOutputFile;

public final class PropertiesUtil
{
    private PropertiesUtil()
    {
    }

    public static Properties load(Properties prop, File file) throws Exception
    {
        if (prop == null)
            prop = new Properties();
        final Properties props = prop;
        processInputFile(file, in ->
        {
            switch (getFileExt(file))
            {
                case "xml":
                    props.loadFromXML(in);
                    break;
                default:
                    props.load(in);
            }
        });
        return props;
    }

    public static void store(Properties prop, File file) throws Exception
    {
        processOutputFile(file, out -> prop.store(out, null));
    }

    public static Properties load(Properties prop, String filePath) throws Exception
    {
        return load(prop, new File(filePath));
    }

    public static void store(Properties prop, String filePath) throws Exception
    {
        store(prop, new File(filePath));
    }

    public static boolean addOrUpdate(String filePath, Map<String, String> keyValMap)
    {
        return addOrUpdate(new File(filePath), keyValMap);
    }

    public static boolean addOrUpdate(File file, Map<String, String> keyValMap)
    {
        return addOrUpdate(file, props -> props.putAll(keyValMap));
    }

    public static boolean addOrUpdate(String filePath, Consumer<Properties> addProps)
    {
        return addOrUpdate(new File(filePath), addProps);
    }

    public static boolean addOrUpdate(File file, Consumer<Properties> addProps)
    {
        try
        {
            Properties props = new Properties();
            load(props, file);
            addProps.accept(props);
            store(props, file);
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }
}
