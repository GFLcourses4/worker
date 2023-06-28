package executor.service.proxy;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;

class LoggingProxyProviderTest {
    private Logger mockLogger;
    private MyInterface mockTarget;

    @BeforeEach
    void setUp() {
        mockLogger = Mockito.mock(Logger.class);
        mockTarget = Mockito.mock(MyInterface.class);
    }

    @Test
    public void testCreateProxy() {
        var proxy = LoggingProxyProvider.createProxy(mockTarget, MyInterface.class, mockLogger);
        assertThat(proxy).isNotNull();
        assertThat(proxy).isInstanceOf(MyInterface.class);
    }

    interface MyInterface {
    }
}