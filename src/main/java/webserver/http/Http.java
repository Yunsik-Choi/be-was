package webserver.http;

import java.util.Arrays;
import webserver.http.response.process.ContentProcessStrategy;
import webserver.http.response.process.CssContentProcessStrategy;
import webserver.http.response.process.HtmlContentProcessStrategy;

public class Http {
    public static final String CRLF = "\r\n";
    public static final String LINE_DELIMITER = "\r\n";
    public static final String SPACING_DELIMITER = " ";
    public static final String HEADER_SEPARATOR = ": ";

    public enum Headers {
        CONTENT_LENGTH("Content-Length"),
        CONTENT_TYPE("Content-Type");

        private final String name;

        Headers(final String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    public enum Method {
        GET,
        HEAD,
        POST,
        PUT,
        PATCH,
        DELETE,
        OPTIONS
    }

    public enum Version {
        HTTP_0_9("HTTP/0.9"),
        HTTP_1_0("HTTP/1.0"),
        HTTP_1_1("HTTP/1.1"),
        HTTP_2_0("HTTP/2.0"),
        HTTP_3_0("HTTP/3.0");

        public static final Version DEFAULT = Version.HTTP_1_1;

        private final String value;

        Version(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Version findBy(final String text) {
            return Arrays.stream(Version.values())
                    .filter(version -> version.value.equals(text))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("버전이 존재하지 않습니다."));
        }
    }

    public enum StatusCode {
        OK("OK", 200),
        CREATED("Created", 201),
        ACCEPTED("Accepted", 202),
        NON_AUTHORITATIVE_INFORMATION("Non-Authoritative Information", 203),
        NO_CONTENT("No Content", 204),
        MOVED_PERMANENTLY("Moved Permanently", 301),
        FOUND("Found", 302),
        SEE_OTHER("See Other", 303),
        NOT_MODIFIED("Not Modified", 304),
        BAD_REQUEST("Bad Request", 400),
        UNAUTHORIZED("Unauthorized", 401),
        PAYMENT_REQUIRED("Payment Required", 402),
        FORBIDDEN("Forbidden", 403),
        NOT_FOUND("Not Found", 404),
        INTERNAL_ERROR("Internal Server Error", 500),
        NOT_IMPLEMENTED("Not Implemented", 501),
        BAD_GATEWAY("Bad Gateway", 502),
        GATEWAY_TIMEOUT("Gateway Timeout", 504);

        private final String code;
        private final int value;

        StatusCode(final String code, final int value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public int getValue() {
            return value;
        }
    }

    public enum MIME {
        HTM(".htm", "text/html", new HtmlContentProcessStrategy()),
        HTML(".html", "text/html", new HtmlContentProcessStrategy()),
        CSS(".css", "text/css", new CssContentProcessStrategy()),
        JS(".js", "text/javascript", new HtmlContentProcessStrategy()),
        ICO(".ico", "image/x-icon", new HtmlContentProcessStrategy()),
        PNG(".png", "image/png", new HtmlContentProcessStrategy()),
        JSON(".json", "application/json", new HtmlContentProcessStrategy()),
        JPG(".jpg", "image/jpeg", new HtmlContentProcessStrategy()),
        JPEG(".jpeg", "image/jpg", new HtmlContentProcessStrategy()),
        EOT(".eot", "application/vnd.ms-fontobject", new HtmlContentProcessStrategy()),
        SVG(".svg", "image/svg+xml", new HtmlContentProcessStrategy()),
        TTF(".ttf", "application/x-font-ttf", new HtmlContentProcessStrategy()),
        WOFF(".woff", "application/x-font-woff", new HtmlContentProcessStrategy()),
        WOFF2(".woff2", "application/font-woff2", new HtmlContentProcessStrategy()),
        NONE("", "", new HtmlContentProcessStrategy());

        private final String extension;
        private final String type;
        private final ContentProcessStrategy strategy;

        MIME(final String value, final String type, final ContentProcessStrategy strategy) {
            this.extension = value;
            this.type = type;
            this.strategy = strategy;
        }

        public static MIME findBy(final String text) {
            return Arrays.stream(MIME.values())
                    .filter(m -> m.extension.equals(text))
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException(text + "에 해당하는 확장자가 없습니다"));
        }

        public String getExtension() {
            return extension;
        }

        public String getType() {
            return type;
        }

        public ContentProcessStrategy getStrategy() {
            return strategy;
        }
    }
}
