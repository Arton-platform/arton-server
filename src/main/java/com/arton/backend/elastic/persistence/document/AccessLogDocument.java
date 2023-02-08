package com.arton.backend.elastic.persistence.document;

import lombok.AccessLevel;
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
    @Field(type = FieldType.Text)
    private String loggerName;
    @Field(type = FieldType.Text)
    private String message;
    @Field(type = FieldType.Long)
    private Long port;
    @Field(type = FieldType.Text)
    private String stackTrace;
    @Field(type = FieldType.Text)
    private String threadName;
}
