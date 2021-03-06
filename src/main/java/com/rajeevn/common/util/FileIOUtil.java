package com.rajeevn.common.util;

import com.rajeevn.common.interfaces.ThrowableConsumer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.rajeevn.common.util.IOUtil.processResource;

/**
 * Utility methods for file system operations.
 *
 * @author Rajeev naik
 * @since 2018/03/04
 */
public final class FileIOUtil
{
    private FileIOUtil()
    {
    }

    /**
     * This method will open the given file, then call the parameter, then will close the file.
     *
     * @param file
     * @param operation to do after file is opened and before it is closed.
     */
    public static void processInputFile(File file, ThrowableConsumer<FileInputStream> operation) throws Exception
    {
        processResource(() -> new FileInputStream(file), operation);
    }

    /**
     * This method will create outputstream, then call the parameter, then close the outputstream.
     *
     * @param file
     * @param operation to do after creating outputstream and before closing it
     */
    public static void processOutputFile(File file, ThrowableConsumer<FileOutputStream> operation) throws Exception
    {
        processResource(() -> new FileOutputStream(file), operation);
    }

    /**
     * @param filePath
     * @param operation
     * @see #processInputFile(File, ThrowableConsumer)
     */
    public static void processInputFile(String filePath, ThrowableConsumer<FileInputStream> operation) throws Exception
    {
        processInputFile(new File(filePath), operation);
    }

    /**
     * @param filePath
     * @param operation
     * @see #processOutputFile(File, ThrowableConsumer)
     */
    public static void processOutputFile(String filePath, ThrowableConsumer<FileOutputStream> operation) throws Exception
    {
        processOutputFile(new File(filePath), operation);
    }

    /**
     * Will not throw exception if move fails
     *
     * @param filePath
     * @param moveToFilePath
     * @see #move(String, String)
     */
    public static void moveQuietly(String filePath, String moveToFilePath)
    {
        try
        {
            move(filePath, moveToFilePath);
        } catch (IOException e)
        {
        }
    }

    /**
     * @param file
     * @param moveToFilePath
     * @see #moveQuietly(String, String)
     */
    public static void moveQuietly(File file, String moveToFilePath)
    {
        moveQuietly(file.getAbsolutePath(), moveToFilePath);
    }

    /**
     * Move file from 'filePath' to 'moveToFilePath'
     *
     * @param filePath
     * @param moveToFilePath
     * @throws IOException
     */
    public static void move(String filePath, String moveToFilePath) throws IOException
    {
        Files.move(Paths.get(filePath), Paths.get(moveToFilePath));
    }

    /**
     * @param file
     * @param moveToFilePath
     * @throws IOException
     * @see #move(String, String)
     */
    public static void move(File file, String moveToFilePath) throws IOException
    {
        move(file.getAbsolutePath(), moveToFilePath);
    }

    /**
     * Delete given file.
     *
     * @param filePath
     * @throws IOException
     */
    public static void delete(String filePath) throws IOException
    {
        Files.delete(Paths.get(filePath));
    }

    /**
     * @param file
     * @throws IOException
     * @see #delete(String)
     */
    public static void delete(File file) throws IOException
    {
        delete(file.getAbsolutePath());
    }

    /**
     * Will not throw exception if delete fails.
     *
     * @param filePath
     * @see #delete(String)
     */
    public static void deleteQuietly(Path filePath)
    {
        try
        {
            Files.delete(filePath);
        } catch (IOException e)
        {
        }
    }

    /**
     * Will not throw exception if delete fails.
     *
     * @param filePath
     * @see #delete(String)
     */
    public static void deleteQuietly(String filePath)
    {
        try
        {
            delete(filePath);
        } catch (IOException e)
        {
        }
    }

    /**
     * @param file
     * @see #deleteQuietly(String)
     */
    public static void deleteQuietly(File file)
    {
        deleteQuietly(file.getAbsolutePath());
    }

    /**
     * @param file
     * @return
     * @see #getFileExt(String)
     */
    public static String getFileExt(File file)
    {
        return getFileExt(file.getName());
    }

    /**
     * To get extension of file in lowercase
     *
     * @param fileName
     * @return
     */
    public static String getFileExt(String fileName)
    {
        int extIndex = fileName.lastIndexOf('.');
        if (extIndex == -1)
            return "";
        return fileName.substring(extIndex + 1).toLowerCase();
    }

    /**
     * To remove file extension from file name
     *
     * @param fileName
     * @return
     */
    public static String stripExt(String fileName)
    {
        int extIndex = fileName.lastIndexOf('.');
        if (extIndex == -1)
            return fileName;
        return fileName.substring(0, extIndex);
    }

    public static void deleteRecursively(String root) throws IOException
    {
        try
        {
            Files.walk(Paths.get(root))
                    .forEach(path ->
                    {
                        try
                        {
                            Files.delete(path);
                        } catch (IOException e)
                        {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (Exception e)
        {
            throw new IOException(e);
        }
    }

    public static void deleteRecursively(File root) throws IOException
    {
        deleteRecursively(root.getAbsolutePath());
    }

    public static void deleteQuietlyRecursively(String root)
    {
        try
        {
            Files.walk(Paths.get(root))
                    .forEach(FileIOUtil::deleteQuietly);
        } catch (IOException e)
        {
        }
    }

    public static void deleteQuietlyRecursively(File root)
    {
        deleteQuietlyRecursively(root.getAbsolutePath());
    }
}
