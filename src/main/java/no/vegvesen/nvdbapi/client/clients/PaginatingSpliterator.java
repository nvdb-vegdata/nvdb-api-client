package no.vegvesen.nvdbapi.client.clients;

import jersey.repackaged.com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

class PaginatingSpliterator<T> implements Spliterator<T> {
    private static final Logger log = LoggerFactory.getLogger(PaginatingSpliterator.class);
    private static final Object DUMMY = new Object();

    private final BlockingQueue<T> queue;
    private final AtomicBoolean isDone = new AtomicBoolean(false);

    PaginatingSpliterator(ExecutorService executorService, GenericResultSet<T> resultSet, Integer pageSize) {
        this.queue = new ArrayBlockingQueue<>(pageSize);
        log.debug("Creating PaginatingSpliterator with page size {}", pageSize);
        executorService.execute(() -> {
            log.debug("Starting consumer");
            while (resultSet.hasNext()) {
                log.debug("resultSet.hasNext()");
                List<T> next = resultSet.next();
                for (T o : next) {
                    try {
                        log.debug("Trying to put {}", o);
                        queue.put(o);
                        log.debug("Put {}", o);
                    } catch (InterruptedException e) {
                        throw Throwables.propagate(e);
                    }
                }
                log.debug("Done resultSet.hasNext()");
            }
            log.debug("No more results");
            try {
                queue.put((T) DUMMY);
            } catch (InterruptedException e) {
                throw Throwables.propagate(e);
            }
            isDone.set(true);
        });
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        if(isDone.get()) {
            log.debug("isDone was true");
            return false;
        } else {
            try {
                log.debug("Take from queue");
                T object = queue.take();
                if(object == DUMMY) {
                    log.debug("Got dummy");
                    return false;
                }
                action.accept(object);
                log.debug("{} taken from queue", object);
            } catch (InterruptedException e) {
                throw Throwables.propagate(e);
            }
            return true;
        }
    }

    @Override
    public Spliterator<T> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return Long.MAX_VALUE;
    }

    @Override
    public int characteristics() {
        return NONNULL | IMMUTABLE | ORDERED;
    }


}
