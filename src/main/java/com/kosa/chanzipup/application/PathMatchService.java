package com.kosa.chanzipup.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PathMatchService {

    private final String domainAddress;

    public PathMatchService(@Value("${domain.address}") String domainAddress) {
        this.domainAddress = domainAddress;
    }


    public String match(String uploadEndPoint) {
        return String.format("%s%s", domainAddress, uploadEndPoint);
    }
}
