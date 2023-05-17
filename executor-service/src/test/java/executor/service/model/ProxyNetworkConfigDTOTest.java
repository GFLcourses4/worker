package executor.service.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProxyNetworkConfigDTOTest {
    private static final String EXPECTED_HOSTNAME = "Test";
    private static final Integer EXPECTED_PORT = 1234;
    private static final String EXPECTED_TO_STRING
            = "ProxyNetworkConfigDTO{hostname='Test', port=1234}";
    private static ProxyNetworkConfigDTO proxyNetworkConfigDTOWithNoArgs;
    private static ProxyNetworkConfigDTO proxyNetworkConfigDTOWithNullArgs;
    private static ProxyNetworkConfigDTO proxyNetworkConfigDTOWithArgs;

    @BeforeAll
    public static void beforeAll(){
        proxyNetworkConfigDTOWithNoArgs = new ProxyNetworkConfigDTO();
        proxyNetworkConfigDTOWithNullArgs = new ProxyNetworkConfigDTO(null, null);
        proxyNetworkConfigDTOWithArgs = new ProxyNetworkConfigDTO(EXPECTED_HOSTNAME, EXPECTED_PORT);
    }

    @Test
    public void testDefaultConstructor(){
        assertNotEquals(null, proxyNetworkConfigDTOWithNoArgs);
    }

    @Test
    public void testArgsConstructorWithNulls(){
        assertNotNull(proxyNetworkConfigDTOWithNullArgs);
        assertNull(proxyNetworkConfigDTOWithNullArgs.getHostname());
        assertNull(proxyNetworkConfigDTOWithNullArgs.getHostname());
    }

    @Test
    public void testArgsConstructor(){
        assertNotNull(proxyNetworkConfigDTOWithArgs);
        assertEquals(EXPECTED_HOSTNAME,proxyNetworkConfigDTOWithArgs.getHostname());
        assertEquals(EXPECTED_PORT,proxyNetworkConfigDTOWithArgs.getPort());
    }

    @Test
    public void testSetters(){
        proxyNetworkConfigDTOWithNoArgs.setHostname(EXPECTED_HOSTNAME);
        proxyNetworkConfigDTOWithNoArgs.setPort(EXPECTED_PORT);
        assertEquals(EXPECTED_HOSTNAME, proxyNetworkConfigDTOWithNoArgs.getHostname());
        assertEquals(EXPECTED_PORT,proxyNetworkConfigDTOWithNoArgs.getPort());
    }

    @Test
    public void testSettersWithNull(){
        proxyNetworkConfigDTOWithNoArgs.setHostname(null);
        proxyNetworkConfigDTOWithNoArgs.setPort(null);
        assertNull(proxyNetworkConfigDTOWithNoArgs.getHostname());
        assertNull(proxyNetworkConfigDTOWithNoArgs.getPort());
    }

    @Test
    public void testGetters(){
        assertEquals(EXPECTED_HOSTNAME, proxyNetworkConfigDTOWithArgs.getHostname());
        assertEquals(EXPECTED_PORT, proxyNetworkConfigDTOWithArgs.getPort());
    }

    @Test
    public void testEqualsSameObjects() {
        ProxyNetworkConfigDTO expected = new ProxyNetworkConfigDTO
                (proxyNetworkConfigDTOWithArgs.getHostname(), proxyNetworkConfigDTOWithArgs.getPort());
        assertEquals(expected, proxyNetworkConfigDTOWithArgs);
    }

    @Test
    public void testEqualsDifferentObjects() {
        ProxyNetworkConfigDTO expected = new ProxyNetworkConfigDTO("hostname",0);
        assertNotEquals(expected, proxyNetworkConfigDTOWithArgs);
    }
    @Test
    public void testHashCode() {
        ProxyNetworkConfigDTO expected = new ProxyNetworkConfigDTO("hostname",0);
        assertNotEquals(expected.hashCode(), proxyNetworkConfigDTOWithArgs.hashCode());
    }

    @Test
    public void testToString(){
        assertEquals(proxyNetworkConfigDTOWithArgs.toString(), EXPECTED_TO_STRING);
    }

}