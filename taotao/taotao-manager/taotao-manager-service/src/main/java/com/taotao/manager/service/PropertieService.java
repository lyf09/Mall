package com.taotao.manager.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class PropertieService {
    @Value(value="${REPOSITORY_PATH}")
    public String REPOSITORY_PATH;
    
    @Value(value="${IMAGE_BASE_URL}")
    public String IMAGE_BASE_URL;
}
