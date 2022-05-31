package com.finance.dbclmrestapp.controller;

import com.finance.dbclmrestapp.controller.DbClmRestController;
import com.finance.dbclmrestapp.model.DbClmRestResponse;
import com.finance.dbclmrestapp.model.Nace;
import com.finance.dbclmrestapp.helper.CSVHelper;
import com.finance.dbclmrestapp.service.DbClmService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class DbClmRestControllerTest {

    @Mock
    DbClmService dbClmService;

    @Mock
    CSVHelper csvHelper;

    @InjectMocks
    DbClmRestController controller;

    @Test
    void putNaceDetailsSuccess() {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        List<Nace> naceList = getNaceList();

        when(csvHelper.hasCSVFormat(file)).thenReturn(true);
        when(dbClmService.putNaceDetails(file)).thenReturn(naceList);

        ResponseEntity<DbClmRestResponse> result = controller.putNaceDetails(file);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getMessage()).contains("Uploaded the file successfully: ");
        assertThat(result.getBody().getData()).isNotNull();
        assertThat(result.getBody().getData().size()).isEqualTo(2);
        assertThat(result.getBody().getData().get(0).getOrderNo()).isEqualTo("orderNo1");
    }

    @Test
    void putNaceDetailsNotCSV() {
        MultipartFile file = Mockito.mock(MultipartFile.class);

        when(csvHelper.hasCSVFormat(file)).thenReturn(false);

        ResponseEntity<DbClmRestResponse> result = controller.putNaceDetails(file);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.EXPECTATION_FAILED);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getMessage()).isEqualTo("Please upload a csv file!");
        assertThat(result.getBody().getData()).isNull();
    }

    @Test
    void putNaceDetailsFail() {
        MultipartFile file = Mockito.mock(MultipartFile.class);

        when(csvHelper.hasCSVFormat(file)).thenReturn(true);
        when(dbClmService.putNaceDetails(file)).thenThrow(RuntimeException.class);

        ResponseEntity<DbClmRestResponse> result = controller.putNaceDetails(file);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.EXPECTATION_FAILED);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getMessage()).contains("Could not upload the file: ");
        assertThat(result.getBody().getData()).isNull();
    }

    @Test
    void getNaceDetails() {
        String order = "orderNo1";
        List<Nace> naceList = getNaceList();

        when(dbClmService.getNaceDetails("orderNo1")).thenReturn(naceList.get(0));

        ResponseEntity<DbClmRestResponse> result = controller.getNaceDetails(order);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getMessage()).contains("NACE Details found for order :");
        assertThat(result.getBody().getData()).isNotNull();
        assertThat(result.getBody().getData().size()).isEqualTo(1);
        assertThat(result.getBody().getData().get(0).getOrderNo()).isEqualTo("orderNo1");
    }

    @Test
    void getNaceDetailsNotFound() {
        String order = "orderNo1";

        when(dbClmService.getNaceDetails("orderNo1")).thenReturn(null);

        ResponseEntity<DbClmRestResponse> result = controller.getNaceDetails(order);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getMessage()).contains("NACE Details not found for order :");
        assertThat(result.getBody().getData()).isNotNull();
        assertThat(result.getBody().getData().size()).isEqualTo(0);
    }

    @Test
    void getAllNaceDetails() {
        List<Nace> naceList = getNaceList();

        when(dbClmService.getAllNaceDetails()).thenReturn(naceList);

        ResponseEntity<DbClmRestResponse> result = controller.getAllNaceDetails();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getMessage()).contains("All NACE Details found.");
        assertThat(result.getBody().getData()).isNotNull();
        assertThat(result.getBody().getData().size()).isEqualTo(2);
        assertThat(result.getBody().getData().get(0).getOrderNo()).isEqualTo("orderNo1");
    }

    @Test
    void getAllNaceDetailsNotFound() {
        when(dbClmService.getAllNaceDetails()).thenReturn(null);

        ResponseEntity<DbClmRestResponse> result = controller.getAllNaceDetails();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getMessage()).contains("No NACE Details found.");
        assertThat(result.getBody().getData()).isNull();
    }

    private List<Nace> getNaceList() {
        List<Nace> naceList = new ArrayList<>();
        Nace nace1 = new Nace("orderNo1",1,"code 1","parent 1","description 1","itemIncludes 1","itemAlsoIncludes 1","rulings 1","itemExcludes 1","reference 1");
        Nace nace2 = new Nace("orderNo2",1,"code 2","parent 2","description 2","itemIncludes 2","itemAlsoIncludes 2","rulings 2","itemExcludes 2","reference 2");
        naceList.add(nace1);
        naceList.add(nace2);

        return naceList;
    }
}