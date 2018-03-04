# common-util
Library for often used operations on various objects, such as Collections, Arrays, Files etc.

# FileCreator
A command pattern to create file with data, with ability to add event handlers.<br>
Usage Example:<br>
```
FileCreator.State state = new FileCreator<>(new FileCreator.State("file path"))
                .onFileExistsMove("move to location")
                .createEmptyIfNoData()
                .onWriteComplete(s -> System.out.println(s.getFile() + " wrote successfully"))
                .onWriteFail((s, e) -> System.out.println(s.getFile() + " failed with error " + e.getMessage()))
                .setData(data)
                .create();
```
