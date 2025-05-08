package com.stormmind.application;

import org.springframework.stereotype.Component;

@Component
public interface ModelToClustersLookupService extends LookupService{
    int getClusterFile(String model);
}
