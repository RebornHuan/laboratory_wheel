package my.skypiea.punygod.promise;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test
    public void testApp() throws InterruptedException, ExecutionException {
        App.main(null);
    }

}