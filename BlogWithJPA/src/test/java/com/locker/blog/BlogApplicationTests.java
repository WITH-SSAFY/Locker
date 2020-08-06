package com.locker.blog;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTests {
    @Test
    public void deployTest() {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("deploy - success");
    }
}
