package com.finance.dbclmrestapp.service;

import com.finance.dbclmrestapp.helper.CSVHelper;
import com.finance.dbclmrestapp.model.Nace;
import com.finance.dbclmrestapp.repository.DbClmRepo;
import com.finance.dbclmrestapp.service.DbClmServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class DbClmServiceTest {

    @Mock
    CSVHelper csvHelper;

    @Mock
    DbClmRepo dbClmRepo;

    @InjectMocks
    DbClmServiceImpl dbClmService;

    @Test
    void putNaceDetails() throws IOException {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        InputStream in = Mockito.mock(InputStream.class);
        List<Nace> naceList = getNaceList();

        when(file.getInputStream()).thenReturn(in);
        when(csvHelper.csvToNaceList(in)).thenReturn(naceList);
        when(dbClmRepo.saveAll(naceList)).thenReturn(naceList);

        List<Nace> result = dbClmService.putNaceDetails(file);
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getOrderNo()).isEqualTo("orderNo1");
        assertThat(result.get(1).getOrderNo()).isEqualTo("orderNo2");
    }

    @Test
    void getNaceDetails() {
        String order = "orderNo1";

        when(dbClmRepo.getById(order)).thenReturn(getNaceList().get(0));

        Nace result = dbClmService.getNaceDetails(order);
        assertThat(result).isNotNull();
        assertThat(result.getOrderNo()).isEqualTo("orderNo1");
    }

    @Test
    void getAllNaceDetails() {
        List<Nace> naceList = getNaceList();
        when(dbClmRepo.findAll()).thenReturn(naceList);

        List<Nace> result = dbClmService.getAllNaceDetails();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getOrderNo()).isEqualTo("orderNo1");
        assertThat(result.get(1).getOrderNo()).isEqualTo("orderNo2");
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