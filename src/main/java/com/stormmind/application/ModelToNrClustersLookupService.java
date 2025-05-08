package com.stormmind.application;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface ModelToNrClustersLookupService extends LookupService{
    int getNrOfClusters(String model);
}
