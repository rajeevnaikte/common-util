package com.rajeevn.common.util;

import com.rajeevn.common.interfaces.ThrowableConsumer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.rajeevn.common.util.ArraysUtil.itemAtIndex;

/**
 * Utility methods for file system operations.
 * @author Rajeev naik
 * @since 2018/03/04
 */
public abstract class FileIOUtil
{
    /**
     * This method will open the given file, then call the parameter, then will close the file.
     * @param file
     * @param operation to do after file is opened and before it is closed.
     */
    public static void processInputFile(File file, ThrowableConsumer<FileInputStream, Exception> operation)
    {
        try (FileInputStream in = new FileInputStream(file))
        {
            operation.accept(in);
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method will create outputstream, then call the parameter, then close the outputstream.
     * @param file
     * @param operation to do after creating outputstream and before closing it
     */
    public static void processOutputFile(File file, ThrowableConsumer<FileOutputStream, Exception> operation)
    {
        try (FileOutputStream out = new FileOutputStream(file))
        {
            operation.accept(out);
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * @see #processInputFile(File, ThrowableConsumer)
     * @param filePath
     * @param operation
     */
    public static void processInputFile(String filePath, ThrowableConsumer<FileInputStream, Exception> operation)
    {
        processInputFile(new File(filePath), operation);
    }

    /**
     * @see #processOutputFile(File, ThrowableConsumer)
     * @param filePath
     * @param operation
     */
    public static void processOutputFile(String filePath, ThrowableConsumer<FileOutputStream, Exception> operation)
    {
        processOutputFile(new File(filePath), operation);
    }

    /**
     * Will not throw exception if move fails
     * @see #move(String, String)
     * @param filePath
     * @param moveToFilePath
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
     * @see #moveQuietly(String, String)
     * @param file
     * @param moveToFilePath
     */
    public static void moveQuietly(File file, String moveToFilePath)
    {
        moveQuietly(file.getAbsolutePath(), moveToFilePath);
    }

    /**
     * Move file from 'filePath' to 'moveToFilePath'
     * @param filePath
     * @param moveToFilePath
     * @throws IOException
     */
    public static void move(String filePath, String moveToFilePath) throws IOException
    {
        Files.move(Paths.get(filePath), Paths.get(moveToFilePath));
    }

    /**
     * @see #move(String, String)
     * @param file
     * @param moveToFilePath
     * @throws IOException
     */
    public static void move(File file, String moveToFilePath) throws IOException
    {
        move(file.getAbsolutePath(), moveToFilePath);
    }

    /**
     * Delete given file.
     * @param filePath
     * @throws IOException
     */
    public static void delete(String filePath) throws IOException
    {
        Files.delete(Paths.get(filePath));
    }

    /**
     * @see #delete(String)
     * @param file
     * @throws IOException
     */
    public static void delete(File file) throws IOException
    {
        delete(file.getAbsolutePath());
    }

    /**
     * Will not throw exception if delete fails.
     * @see #delete(String)
     * @param filePath
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
     * @see #deleteQuietly(String)
     * @param file
     */
    public static void deleteQuietly(File file)
    {
        deleteQuietly(file.getAbsolutePath());
    }

    /**
     * To get extension of file in lowercase
     * @param file
     * @return
     */
    public static String getFileExtention(File file)
    {
        return itemAtIndex(file.getName().split("."), 1)
                .map(String::toLowerCase)
                .orElse("");
    }
}