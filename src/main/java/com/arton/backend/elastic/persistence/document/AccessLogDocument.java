package com.arton.backend.elastic.persistence.document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "accesslog-*")
public class AccessLogDocument {
    @Id
    private String id;
    @Field(type = FieldType.Text)
    private String requestURI;
    @Field(type = FieldType.Text)
    private String requestMethod;
    @Field(type = FieldType.Text)
    private String queryString;
}
