package com.finance.dbclmrestapp.service;

import com.finance.dbclmrestapp.helper.CSVHelper;
import com.finance.dbclmrestapp.model.Nace;
import com.finance.dbclmrestapp.repository.DbClmRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
public class DbClmServiceImpl implements DbClmService {

    @Autowired
    private CSVHelper csvHelper;

    @Autowired
    private DbClmRepo dbClmRepo;

    @Override
    public Nace getNaceDetails(String order) {
        return dbClmRepo.getById(order);
    }

    @Override
    public List<Nace> getAllNaceDetails() {
        return dbClmRepo.findAll();
    }

    @Override
    public List<Nace> putNaceDetails(MultipartFile naceDetailsFile) {
        if(Objects.nonNull(naceDetailsFile)){
            try {
                List<Nace> naceList = csvHelper.csvToNaceList(naceDetailsFile.getInputStream());
                dbClmRepo.saveAll(naceList);
                return naceList;
            } catch (Exception e) {
                throw new RuntimeException("fail to store csv data: " + e.getMessage());
            }
        }
        return null;
    }
}
