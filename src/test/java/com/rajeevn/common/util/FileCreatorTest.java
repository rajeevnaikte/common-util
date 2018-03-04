package com.rajeevn.common.util;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileCreatorTest
{
    @Test
    public void test() throws IOException
    {
        Files.deleteIfExists(Paths.get("test1"));
        Files.createFile(Paths.get("test1"));
        TestState state = new FileCreator<>(new TestState("test1"))
                .onFileExists((t) -> t.onFileExistCalled = true)
                .onWriteComplete(t -> t.onWriteCompleteCalled = true)
                .createEmptyIfNoData()
                .create();

        assert state.onFileExistCalled == true;
        assert state.onWriteCompleteCalled == true;
        assert state.isFileExists() == true;
        assert state.isWriteComplete() == true;
    }

    public class TestState extends FileCreator.State
    {
        public boolean onFileExistCalled = false;
        public boolean onWriteCompleteCalled = false;
        public boolean onWriteFailCalled = false;

        public TestState(String file)
        {
            super(file);
        }
    }
}
