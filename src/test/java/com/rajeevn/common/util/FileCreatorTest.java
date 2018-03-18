package com.rajeevn.common.util;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileCreatorTest
{
    @Test
    public void test() throws Exception
    {
        Files.deleteIfExists(Paths.get("test1"));
        Files.createFile(Paths.get("test1"));
        TestState state = new FileCreator<>(new TestState("test1"))
                .onFileExists((t) -> t.onFileExistCalled = true)
                .onWriteComplete(t -> t.onWriteCompleteCalled = true)
                .createEmptyIfNoData()
                .create();

        assert state.onFileExistCalled;
        assert state.onWriteCompleteCalled;
        assert state.isFileExists();
        assert state.isWriteComplete();
    }

    public class TestState extends FileCreator.State
    {
        boolean onFileExistCalled = false;
        boolean onWriteCompleteCalled = false;

        public TestState(String file)
        {
            super(file);
        }
    }
}
