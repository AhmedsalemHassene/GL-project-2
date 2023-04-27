

/** test pour la classe CompressionCustomizer */
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpServerRequest;
import io.netty.handler.codec.http.HttpServerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CompressionCustomizerTest {

    @Mock
    private Compression compression;

    @Mock
    private HttpServerRequest httpServerRequest;

    @Mock
    private HttpServerResponse httpServerResponse;

    private CompressionCustomizer compressionCustomizer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        compressionCustomizer = new CompressionCustomizer(compression);
    }

    @Test
    public void testApplyCompression() {
        when(compression.getMinResponseSize()).thenReturn(DataSize.ofKilobytes(1));
        when(compression.getMimeTypes()).thenReturn(new String[] { "text/plain" });
        when(compression.getExcludedUserAgents()).thenReturn(new String[] { "Mozilla" });

        HttpServer httpServer = mock(HttpServer.class);
        when(httpServer.compress(anyInt())).thenReturn(httpServer);
        when(httpServer.compress(any(CompressionPredicate.class))).thenReturn(httpServer);

        HttpServer actual = compressionCustomizer.apply(httpServer);

        verify(compression).getMinResponseSize();
        verify(compression).getMimeTypes();
        verify(compression).getExcludedUserAgents();

        verify(httpServer).compress(1024);
        verify(httpServer).compress(any(CompressionPredicate.class));

        assertEquals(httpServer, actual);
    }

    @Test
    public void testApplyCompressionWithEmptyMimeTypesAndExcludedUserAgents() {
        when(compression.getMinResponseSize()).thenReturn(DataSize.ofKilobytes(1));
        when(compression.getMimeTypes()).thenReturn(new String[] {});
        when(compression.getExcludedUserAgents()).thenReturn(new String[] {});

        HttpServer httpServer = mock(HttpServer.class);
        when(httpServer.compress(anyInt())).thenReturn(httpServer);
        when(httpServer.compress(any(CompressionPredicate.class))).thenReturn(httpServer);

        HttpServer actual = compressionCustomizer.apply(httpServer);

        verify(compression).getMinResponseSize();
        verify(compression).getMimeTypes();
        verify(compression).getExcludedUserAgents();

        verify(httpServer).compress(1024);
        verify(httpServer).compress(any(CompressionPredicate.class));

        assertEquals(httpServer, actual);
    }

    @Test
    public void testGetMimeTypesPredicate() {
        when(compression.getMimeTypes()).thenReturn(new String[] { "text/plain", "application/json" });

        ServerHttpResponse serverHttpResponse = mock(ServerHttpResponse.class);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
        when(serverHttpResponse.getHeaders()).thenReturn(httpHeaders);

        ServerHttpRequest serverHttpRequest = mock(ServerHttpRequest.class);
        when(serverHttpRequest.getHeaders()).thenReturn(new HttpHeaders());

        assertTrue(compressionCustomizer.getMimeTypesPredicate(compression.getMimeTypes()).test(serverHttpRequest, serverHttpResponse));
    }

    @Test
    public void testGetExcludedUserAgentsPredicate() {
        when(compression.getExcludedUserAgents()).thenReturn(new String[] { "Mozilla", "Chrome"
