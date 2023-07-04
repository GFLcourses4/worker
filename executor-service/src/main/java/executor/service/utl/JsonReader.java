package executor.service.utl;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.exception.JsonReaderException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class JsonReader {
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static <T> T[] parseToArray(InputStream inputStream, Class<T> tClass) {
        try {
            return objectMapper.readerForArrayOf(tClass).readValue(inputStream);
        } catch (IOException e) {
            throw new JsonReaderException(e);
        }
    }

    public static <T> List<T> parseToList(File file, Class<T> tClass) {
        try {
            return objectMapper.readerForListOf(tClass).readValue(file);
        } catch (IOException e) {
            throw new JsonReaderException(e);
        }
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static <T> T[] parseResourceToArray(String fileName, Class<T> tClass) {
        try (InputStream inputStream = JsonReader.class.getClassLoader().getResourceAsStream(fileName)) {
            return JsonReader.parseToArray(inputStream, tClass);
        } catch (IOException e) {
            throw new JsonReaderException(e);
        }
    }

    public static <T> List<T> parseResourceToList(String fileName, Class<T> tClass) {
        return Arrays.asList(parseResourceToArray(fileName, tClass));
    }
}


