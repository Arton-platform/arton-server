package com.arton.backend.elastic.persistence.document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "logstash-#{@logIndexNameProvider.timeSuffix()}")
public class AccessLogDocument {
    @Id
    private String id;
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", name = "@timestamp")
    private LocalDateTime date;
    @Field(type = FieldType.Keyword, name = "@version")
    private String version;
    @Field(type = FieldType.Text)
    private String host;
    @Field(type = FieldType.Text)
    private String level;
    @Field(type = FieldType.Long)
    private Long levelValue;
    @Field(type = FieldType.Text, name = "logger_name")
    private String loggerName;
    @Field(type = FieldType.Text)
    private String message;
    @Field(type = FieldType.Long)
    private Long port;
    @Field(type = FieldType.Text)
    private String stackTrace;
    @Field(type = FieldType.Text)
    private String threadName;
    @Field(type = FieldType.Text)
    private String keyword;

    @Builder
    public AccessLogDocument(String id, LocalDateTime date, String version, String host, String level, Long levelValue, String loggerName, String message, Long port, String stackTrace, String threadName, String keyword) {
        this.id = id;
        this.date = date;
        this.version = version;
        this.host = host;
        this.level = level;
        this.levelValue = levelValue;
        this.loggerName = loggerName;
        this.message = message;
        this.port = port;
        this.stackTrace = stackTrace;
        this.threadName = threadName;
        this.keyword = keyword;
    }
}
