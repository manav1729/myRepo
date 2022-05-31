package com.finance.dbclmrestapp.service;

import com.finance.dbclmrestapp.model.Nace;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DbClmService {
    Nace getNaceDetails(String order);

    List<Nace> getAllNaceDetails();

    List<Nace> putNaceDetails(MultipartFile nace);
}
