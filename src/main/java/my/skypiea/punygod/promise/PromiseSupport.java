package my.skypiea.punygod.promise;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class PromiseSupport<T> implements Future<T> {

    //需要标识结果的状态值  --常量变量
    private static final int RUNNING = 1;
    private static final int FAILED = 2;
    private static final int COMPLETED = 3;

    //state
    private volatile int state = RUNNING;

    private T value;
    private Exception exception;
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();


    void fulfill(T value) {
        this.value = value;
        state = COMPLETED;
        lock.lock();
        try {
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }


    void fulfillExceptionally(Exception exception) {
        this.exception = exception;
        state = FAILED;
        lock.lock();
        try {
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return state > RUNNING;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        lock.lock();
        try {
            while (state == RUNNING) {
                condition.await();
            }
        } finally {
            lock.unlock();
        }
        if (state == COMPLETED) return value;
        throw new ExecutionException(exception);
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws ExecutionException, TimeoutException {
        lock.lock();
        try {
            while (state == RUNNING) {
                condition.await(timeout, unit);
            }
        } catch (InterruptedException i) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
        if (state == COMPLETED) return value;
        throw new ExecutionException(exception);
    }
}
