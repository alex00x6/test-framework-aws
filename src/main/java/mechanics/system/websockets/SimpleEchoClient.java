package mechanics.system.websockets;

import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.net.URI;
import java.util.concurrent.TimeUnit;

public class SimpleEchoClient {
    public static void main(String[] args) {
//        String destUri = "ws://echo.websocket.org";
        String destUri = "wss://a2awmps9ermju9.iot.us-east-1.amazonaws.com/system.mqtt?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=ASIAIUOQMFKHPEE7JFPA%2F20170414%2Fus-east-1%2Fiotdata%2Faws4_request&X-Amz-Date=20170414T084339Z&X-Amz-SignedHeaders=host&X-Amz-Signature=f8d2acfd8f87f62e3f5164a70620d6d9f8f588f2085ea207aff97e7923cb38af&X-Amz-Security-Token=FQoDYXdzENL%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEaDFY2DuXeZHPMdpRk1SLmA8TfmRq4xxuGQASJDE8eJNKsorA80YArZhv7eCXwsM0puZE3Gl4zqZBE4ArVQs6jaCeAEcv5%2B42i9CCgR3f9uzp7%2Bhs%2F2Xyu9bX%2BPesW7aU0Rbpv4EO8NPCdXH9jC7i46N2LjUHOds%2BRAZPAu5tuy8r68h1iOV3OKAnqfCjbk9%2Fuqtx8ooOkfZNrFjy%2FlCMVAP%2FxNz7mTNmgZGv2DRpabOQE9lfq8P2wksBHJW8XQ1p8LiAyr6sLeiDelec7AwyXTX64h8AwFCKCRRcEFk4%2FGIakBs5NmpkwjKZA4aqrCWGlYrKWyjGmGfTRONFdS0g%2F4UO0mrGLmpMeXy1zPON2iI8Iawisdkq5XWYJSbBafS4J7o0VNT4kYvnwVFOOT13YDnbBCUJWK8oAvfqOwJgNFf5Z6QxQnNcwZL8%2BosHCkLOvESf6Bws2nep9iLetXEGCNsiLXDOYp7MJQCktkc8tNY9GNtbs4W3GXFEwZPJweCg7ni670FeEMP0tL9IXcsKls5%2FcFaAyf1liMGN277ysqskVE0feH%2FUlUHwwGo%2BOVA0EC5t%2B6NynRar77hlYzG406HGvZiEQFHCFXEng89jxBYxMPTfOh1t%2FSjJjpHd%2FvCawHy1NehE2YvcmWUQViz3O7%2FQwiQaq8Si3l8LHBQ%3D%3D";
//        String destUri = "ws://a2awmps9ermju9.iot.us-east-1.amazonaws.com";
        if (args.length > 0) {
            destUri = args[0];
        }


        org.eclipse.jetty.util.log.Log.setLog(new NoLogging());
        WebSocketClient client = new WebSocketClient();
        SimpleEchoSocket socket = new SimpleEchoSocket();
        try {
            client.start();

            URI echoUri = new URI(destUri);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(socket, echoUri, request);
            System.out.printf("Connecting to : %s%n", echoUri);


            // wait for closed socket connection.
            socket.awaitClose(500, TimeUnit.SECONDS);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                client.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class NoLogging implements Logger {
        @Override
        public String getName() {
            return "no";
        }

        @Override
        public void warn(String msg, Object... args) {
        }

        @Override
        public void warn(Throwable thrown) {
        }

        @Override
        public void warn(String msg, Throwable thrown) {
        }

        @Override
        public void info(String msg, Object... args) {
        }

        @Override
        public void info(Throwable thrown) {
        }

        @Override
        public void info(String msg, Throwable thrown) {
        }

        @Override
        public boolean isDebugEnabled() {
            return false;
        }

        @Override
        public void setDebugEnabled(boolean enabled) {
        }

        @Override
        public void debug(String msg, Object... args) {
        }

        @Override
        public void debug(String s, long l) {

        }

        @Override
        public void debug(Throwable thrown) {
        }

        @Override
        public void debug(String msg, Throwable thrown) {
        }

        @Override
        public Logger getLogger(String name) {
            return this;
        }

        @Override
        public void ignore(Throwable ignored) {
        }
    }
}