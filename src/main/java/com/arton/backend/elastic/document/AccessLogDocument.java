package com.arton.backend.elastic.document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "accesslog")
public class AccessLogDocument {
    @Field(type = FieldType.Text)
    private String requestURI;
    @Field(type = FieldType.Text)
    private String requestMethod;
    @Field(type = FieldType.Text)
    private String queryString;
    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis})
    private LocalDateTime date;
}
