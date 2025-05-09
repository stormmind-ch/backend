package com.stormmind.application;

import org.springframework.stereotype.Component;

@Component
public interface ModelToClustersLookupService extends LookupService{
    String getClusterFile(String model);
}
