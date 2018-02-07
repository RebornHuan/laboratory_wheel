package my.skypiea.punygod.promise;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Utility {
    private static final Logger LOGGER = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    public static Map<Character, Integer> characterFrequency(String fileLocation) {
        Map<Character, Integer> characterToFrequency = new HashMap<>();
        try (Reader reader = new FileReader(fileLocation);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            for (String line; (line = bufferedReader.readLine()) != null; ) {
                for (char character : line.toCharArray()) {
                    if (characterToFrequency.containsKey(character)) {
                        characterToFrequency.put(character, characterToFrequency.get(character) + 1);
                    } else {
                        characterToFrequency.put(character, 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return characterToFrequency;
    }

    public static Character lowestFrequencyChar(Map<Character, Integer> charFrequency) {
        Character lowestFrequencyChar = null;
        int minFrequency = 0;
        for (Map.Entry<Character, Integer> entry : charFrequency.entrySet()) {
            if (minFrequency > entry.getValue()) {
                minFrequency = entry.getValue();
                lowestFrequencyChar = entry.getKey();
            }
        }
        return lowestFrequencyChar;
    }

    public static Integer countLines(String fileLocation) {
        LOGGER.info("count  lines of file {}",fileLocation);
        int countLines = 0;
        try (Reader reader = new FileReader(fileLocation);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            while (bufferedReader.readLine() != null) {
                countLines++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return countLines;
    }

    public static String downloadFile(String urlString) throws IOException {
        LOGGER.info("download file from {}",urlString);
        URL url = new URL(urlString);
        File localFile = File.createTempFile("promise_file", null);
        try (Reader reader = new InputStreamReader(url.openStream());
             BufferedReader bufferedReader = new BufferedReader(reader);
             FileWriter writer = new FileWriter(localFile)) {
            for (String line; (line = bufferedReader.readLine()) != null; ) {
                writer.write(line);
                writer.write('\n');
            }
            writer.flush();
        }
        LOGGER.info("File downloaded at: {}", localFile.getAbsolutePath());
        return localFile.getAbsolutePath();
    }

}
