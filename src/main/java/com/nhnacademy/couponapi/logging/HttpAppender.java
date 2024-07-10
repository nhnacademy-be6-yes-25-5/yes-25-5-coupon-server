package com.nhnacademy.couponapi.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;

public class HttpAppender extends AppenderBase<ILoggingEvent> {

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String url;
    private String projectName;
    private String projectVersion;
    private String logVersion;
    private String logSource;
    private String logType;
    private String host;
    private String secretKey;
    private String platform;

    @Override
    protected void append(ILoggingEvent eventObject) {
        try {
            String logLevel = eventObject.getLevel().toString();
            LogEvent logEvent = new LogEvent.Builder()
                    .projectName(projectName)
                    .projectVersion(projectVersion)
                    .logVersion(logVersion)
                    .body(eventObject.getFormattedMessage())
                    .logSource(logSource)
                    .logType(logType)
                    .host(host)
                    .secretKey(secretKey)
                    .logLevel(logLevel)
                    .platform(platform)
                    .build();
            String json = objectMapper.writeValueAsString(logEvent);
            RequestBody body = RequestBody.create(json, JSON);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            addInfo("Sending log event: " + json);

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    addError("Failed to send log event", e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        addError("Failed to send log event, response code: " + response.code());
                    } else {
                        addInfo("Log event sent successfully");
                    }
                    response.close();
                }
            });

        } catch (IOException e) {
            addError("Failed to send log event", e);
        }
    }

    // Getters and setters for configuration
    public void setUrl(String url) {
        this.url = url;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
    }

    public void setLogVersion(String logVersion) {
        this.logVersion = logVersion;
    }

    public void setLogSource(String logSource) {
        this.logSource = logSource;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    static class LogEvent {
        private final String projectName;
        private final String projectVersion;
        private final String logVersion;
        private final String body;
        private final String logSource;
        private final String logType;
        private final String host;
        private final String secretKey;
        private final String logLevel;
        private final String platform;

        private LogEvent(Builder builder) {
            this.projectName = builder.projectName;
            this.projectVersion = builder.projectVersion;
            this.logVersion = builder.logVersion;
            this.body = builder.body;
            this.logSource = builder.logSource;
            this.logType = builder.logType;
            this.host = builder.host;
            this.secretKey = builder.secretKey;
            this.logLevel = builder.logLevel;
            this.platform = builder.platform;
        }

        // Getters
        public String getProjectName() {
            return projectName;
        }

        public String getProjectVersion() {
            return projectVersion;
        }

        public String getLogVersion() {
            return logVersion;
        }

        public String getBody() {
            return body;
        }

        public String getLogSource() {
            return logSource;
        }

        public String getLogType() {
            return logType;
        }

        public String getHost() {
            return host;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public String getLogLevel() {
            return logLevel;
        }

        public String getPlatform() {
            return platform;
        }

        public static class Builder {
            private String projectName;
            private String projectVersion;
            private String logVersion;
            private String body;
            private String logSource;
            private String logType;
            private String host;
            private String secretKey;
            private String logLevel;
            private String platform;

            public Builder projectName(String projectName) {
                this.projectName = projectName;
                return this;
            }

            public Builder projectVersion(String projectVersion) {
                this.projectVersion = projectVersion;
                return this;
            }

            public Builder logVersion(String logVersion) {
                this.logVersion = logVersion;
                return this;
            }

            public Builder body(String body) {
                this.body = body;
                return this;
            }

            public Builder logSource(String logSource) {
                this.logSource = logSource;
                return this;
            }

            public Builder logType(String logType) {
                this.logType = logType;
                return this;
            }

            public Builder host(String host) {
                this.host = host;
                return this;
            }

            public Builder secretKey(String secretKey) {
                this.secretKey = secretKey;
                return this;
            }

            public Builder logLevel(String logLevel) {
                this.logLevel = logLevel;
                return this;
            }

            public Builder platform(String platform) {
                this.platform = platform;
                return this;
            }

            public LogEvent build() {
                return new LogEvent(this);
            }
        }
    }
}