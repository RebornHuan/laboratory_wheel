package my.skypiea.punygod.promise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


class PromiseTest {
    private Executor executor;
    private Promise<Integer> promise;


    @BeforeEach
    public void setUp() {
        executor = Executors.newFixedThreadPool(2);
        promise = new Promise();
    }

    @Test
    public void promiseIsFulfilledWithTheResultantValueOfExecutingTheTask()
            throws ExecutionException, InterruptedException {
        promise = new Promise<Integer>().fulfillInAsync(new NumberCrunchingTask(), executor);
        assertEquals(NumberCrunchingTask.NUMBER_CRUNCH, promise.get());
        assertTrue(promise.isDone());
        assertFalse(promise.isCancelled());
    }

    @Test
    public void promiseIsFulfilledWithAnExceptionIfTaskThrowsAnException()
            throws TimeoutException, InterruptedException {
        testWaitingForeverForPromiseToBeFulfilled();
        testWaitingSomeTimeForPromiseToBeFulfilled();
    }

    @Test
    public void dependentPromiseIsFulfilledAfterTheConsumerConsumesTheResultOfThisPromise()
            throws ExecutionException, InterruptedException {
        Promise<Void> dependentPromise = promise.fulfillInAsync(new NumberCrunchingTask(), executor)
                .thenAccept(value -> assertEquals(NumberCrunchingTask.NUMBER_CRUNCH, value));
        dependentPromise.get();
        assertTrue(dependentPromise.isDone());
        assertFalse(dependentPromise.isCancelled());
    }


    @Test
    public void dependentPromiseIsFulfilledWithAnExceptionIfConsumerThrowsAnException()
            throws InterruptedException {
        Promise<Void> dependentPromise = promise.fulfillInAsync(new NumberCrunchingTask(), executor)
                .thenAccept(value -> {
                    throw new RuntimeException("Error");
                });
        try {
            dependentPromise.get();
            fail("Fetching promise should result in exception if the task threw an exception");
        } catch (ExecutionException ex) {
            assertTrue(dependentPromise.isDone());
            assertFalse(dependentPromise.isCancelled());
        }
    }

    @Test
    public void dependentPromiseIsFulfilledAfterTheFunctionTransformsTheResultOfThisPromise()
            throws ExecutionException, InterruptedException {
        Promise<String> dependentPromise = promise.fulfillInAsync(new NumberCrunchingTask(), executor)
                .thenApply(value -> {
                    assertEquals(NumberCrunchingTask.NUMBER_CRUNCH, value);
                    return String.valueOf(value);
                });
        dependentPromise.get();
        assertTrue(dependentPromise.isDone());
        assertFalse(dependentPromise.isCancelled());
    }

    @Test
    public void dependentPromiseIsFulfilledWithAnExceptionIfTheFunctionTransformsThrowsAnException() throws InterruptedException {

        Promise<String> dependentPromise = promise.fulfillInAsync(new NumberCrunchingTask(), executor)
                .thenApply(value -> {
                    assertEquals(NumberCrunchingTask.NUMBER_CRUNCH, value);
                    throw new RuntimeException("Error");
                });
        try {
            dependentPromise.get();
            fail("Fetching promise should result in exception if the task threw an exception");
        } catch (ExecutionException ex) {
            assertTrue(dependentPromise.isDone());
            assertFalse(dependentPromise.isCancelled());
        }
    }

    private void testWaitingForeverForPromiseToBeFulfilled() throws InterruptedException, TimeoutException {
        promise = new Promise<Integer>().fulfillInAsync(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("Throw an error");
                throw new Exception("Error!");
            }
        }, executor);
        try {
            promise.get();
            fail("Fetching promise should result in exception if the task threw an exception");
        } catch (ExecutionException ex) {
            assertTrue(promise.isDone());
            assertFalse(promise.isCancelled());
        }
        try {
            promise.get(100, TimeUnit.SECONDS);
            fail("Fetching promise should result in exception if the task threw an exception");
        } catch (ExecutionException ex) {
            assertTrue(promise.isDone());
            assertFalse(promise.isCancelled());
        }

    }

    private void testWaitingSomeTimeForPromiseToBeFulfilled() throws InterruptedException, TimeoutException {
        promise = new Promise<Integer>().fulfillInAsync(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("Throw an error");
                throw new Exception("Error!");
            }
        }, executor);
        try {
            promise.get(100, TimeUnit.SECONDS);
            fail("Fetching promise should result in exception if the task threw an exception");
        } catch (ExecutionException ex) {
            assertTrue(promise.isDone());
            assertFalse(promise.isCancelled());
        }

        try {
            promise.get();
            fail("Fetching promise should result in exception if the task threw an exception");
        } catch (ExecutionException ex) {
            assertTrue(promise.isDone());
            assertFalse(promise.isCancelled());
        }
    }

    @Test
    public void fetchingAnAlreadyFulfilledPromiseReturnsTheFulfilledValueImmediately()
            throws ExecutionException, TimeoutException {
        Promise<Integer> promise = new Promise<>();
        promise.fulfill(NumberCrunchingTask.NUMBER_CRUNCH);

        promise.get(1000, TimeUnit.SECONDS);
    }

    @Test
    public void exceptionHandlerIsCalledWhenPromiseIsFulfilledExceptionally() {
        Promise<Object> promise = new Promise<>();
        Consumer<Throwable> exceptionHandler = mock(Consumer.class);
        promise.onError(exceptionHandler);
        Exception exception = new Exception("barf!");
        promise.fulfillExceptionally(exception);
        verify(exceptionHandler).accept(eq(exception));
    }


    private static class NumberCrunchingTask implements Callable<Integer> {

        public final static Integer NUMBER_CRUNCH = 100;

        @Override
        public Integer call() throws Exception {
            Thread.sleep(1000);
            System.out.println("321");
            return NUMBER_CRUNCH;
        }
    }
}