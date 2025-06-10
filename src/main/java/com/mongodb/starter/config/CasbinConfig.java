package com.mongodb.starter.config;

import org.casbin.jcasbin.main.Enforcer;
import org.casbin.jcasbin.model.Model;
import org.casbin.jcasbin.persist.Adapter;
import org.casbin.jcasbin.persist.file_adapter.FileAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class CasbinConfig {

    @Bean
    public Enforcer enforcer() throws IOException {
        // Load resource files from classpath
        ClassPathResource modelResource = new ClassPathResource("casbin/model.conf");
        ClassPathResource policyResource = new ClassPathResource("casbin/policy.csv");
        
        // Create temporary files
        File modelTempFile = File.createTempFile("casbin-model", ".conf");
        modelTempFile.deleteOnExit();
        
        File policyTempFile = File.createTempFile("casbin-policy", ".csv");
        policyTempFile.deleteOnExit();
        
        // Copy model resource to temp file
        try (InputStream is = modelResource.getInputStream();
             FileOutputStream os = new FileOutputStream(modelTempFile)) {
            FileCopyUtils.copy(is, os);
        }
        
        // Copy policy resource to temp file
        try (InputStream is = policyResource.getInputStream();
             FileOutputStream os = new FileOutputStream(policyTempFile)) {
            FileCopyUtils.copy(is, os);
        }
        
        // Get absolute paths
        String modelPath = modelTempFile.getAbsolutePath();
        String policyPath = policyTempFile.getAbsolutePath();
        
        // Create and return the enforcer
        Model model = new Model();
        model.loadModel(modelPath);
        
        Adapter adapter = new FileAdapter(policyPath);
        
        return new Enforcer(model, adapter);
    }
} 