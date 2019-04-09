package carddeck.impl;

import java.util.concurrent.atomic.AtomicInteger;

class IdGenerator {

    private final AtomicInteger counter = new AtomicInteger();

    String generate() {

        return Integer.toString(counter.incrementAndGet());
        // for the sake of simplicity, the generator is really simple, just incrementing a counter.
        // A typical implementation should generate a hash based on a coun
        //final long l = System.nanoTime();
        //final HashFunction hf = Hashing.sha1();
        //return hf.newHasher().putLong(l).putInt(counter.getAndIncrement()).hash().toString();
    }
}
