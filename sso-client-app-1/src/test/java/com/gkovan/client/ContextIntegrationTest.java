package com.gkovan.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gkovan.client.SSOClientApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { SSOClientApplication.class })
public class ContextIntegrationTest {

    @Test
    public void whenLoadApplication_thenSuccess() {
    }

}
