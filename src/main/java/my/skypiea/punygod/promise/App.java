package my.skypiea.punygod.promise;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.*;

public class App {
    private static final Logger LOGGER = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    private static final String DEFAULT_URL = "https://raw.githubusercontent.com/iluwatar/java-design-patterns/master/promise/README.md";
    private ExecutorService executor;
    private CountDownLatch countDownLatch;

    private App() {
        executor = Executors.newFixedThreadPool(2);
        countDownLatch = new CountDownLatch(2);
    }

    public static void main(String[] args) throws InterruptedException {
        App app = new App();
        try {
            app.promiseUsage();
        } finally {
            app.stop();
        }

    }

    private void promiseUsage() {
        calculateLineCount();
        calculateLowestFrequencyChar();
    }


    private void calculateLineCount() {
        countLines()
                .thenAccept(count ->
                {
                    LOGGER.info("Line count is: {}", count);
                    taskCompleted();
                });
    }

    private void calculateLowestFrequencyChar() {
        lowestFrequencyChar()
                .thenAccept(charFrequency ->
                {
                    LOGGER.info("Char with lowest frequency is: {}", charFrequency);
                    taskCompleted();
                });
    }

    private Promise<String> download(String urlString) {
        return new Promise<String>()
                .fulfillInAsync(() ->
                        Utility.downloadFile(urlString), executor)
                .onError(throwable ->
                        {
                            throwable.printStackTrace();
                            taskCompleted();
                        }
                );
    }

    private Promise<Integer> countLines() {
        return download(DEFAULT_URL)
                .thenApply(Utility::countLines);
    }

    private Promise<Map<Character, Integer>> characterFrequency() {
        return download(DEFAULT_URL)
                .thenApply(Utility::characterFrequency);

    }

    private Promise<Character> lowestFrequencyChar() {
        return download(DEFAULT_URL)
                .thenApply(Utility::characterFrequency)
                .thenApply(Utility::lowestFrequencyChar);
    }

    private void stop() throws InterruptedException {
        countDownLatch.await();
        executor.shutdownNow();
    }

    private void taskCompleted() {
        countDownLatch.countDown();
    }
}
