package com.finance.dbclmrestapp.controller;

import com.finance.dbclmrestapp.DbClmRestApplication;
import com.finance.dbclmrestapp.model.DbClmRestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(classes = DbClmRestApplication.class, webEnvironment = RANDOM_PORT)
public class DbClmRestControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Sql({"classpath:schema.sql"})
    @BeforeEach
    void setup() {
    }

    @Test
    void putNaceDetails() throws Exception {
        ResponseEntity<DbClmRestResponse> result = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/v1/dbclm/putNaceDetails",
                        getRequestEntity(), DbClmRestResponse.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getMessage()).isEqualTo("Uploaded the file successfully: NACE_Data.csv");
        assertThat(result.getBody().getData()).isNotNull();
        assertThat(result.getBody().getData().size()).isEqualTo(996);
    }

    @Test
    void getNaceDetails() throws Exception {
        putNaceDetails();
        String order = "398481";
        ResponseEntity<DbClmRestResponse> result = this.restTemplate
                .getForEntity("http://localhost:" + port + "/api/v1/dbclm/getNaceDetails/" + order, DbClmRestResponse.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getMessage()).isEqualTo("NACE Details found for order :398481");
        assertThat(result.getBody().getData()).isNotNull();
        assertThat(result.getBody().getData().size()).isEqualTo(1);
        assertThat(result.getBody().getData().get(0).getOrderNo()).isEqualTo(order);
    }

    @Test
    void getAllNaceDetails() throws Exception {
        putNaceDetails();
        ResponseEntity<DbClmRestResponse> result = this.restTemplate
                .getForEntity("http://localhost:" + port + "/api/v1/dbclm/getAllNaceDetails", DbClmRestResponse.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getMessage()).isEqualTo("All NACE Details found.");
        assertThat(result.getBody().getData()).isNotNull();
        assertThat(result.getBody().getData().size()).isEqualTo(996);
    }

    private HttpEntity<MultiValueMap<String, Object>> getRequestEntity() throws FileNotFoundException {
        File file = ResourceUtils.getFile(this.getClass().getResource("/NACE_Data.csv"));

        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(file));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return requestEntity;
    }
}