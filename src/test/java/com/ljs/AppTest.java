package com.ljs;

import static org.junit.Assert.assertTrue;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.redisson.RedissonLock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
@Slf4j
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Autowired
    RedissonLock lock;
    @Test
    public void shouldAnswerWithTrue() throws InterruptedException {
        boolean isLock = lock.tryLock(1L, TimeUnit.SECONDS);
    }
}
