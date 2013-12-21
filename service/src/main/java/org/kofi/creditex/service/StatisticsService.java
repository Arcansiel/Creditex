package org.kofi.creditex.service;

import org.kofi.creditex.model.*;
import java.util.List;
import java.util.Map;

public interface StatisticsService {
    List<Map<String,Object>> GetAllProductsStatistics(long[] out);
    Map<String,Object> GetCreditsStatistics();
    Map<String,Object> GetClientsStatistics();
    Map<String,Object> GetApplicationsStatistics();
    Map<String,Object> GetPriorsStatistics();
    Map<String,Object> GetProlongationsStatistics();
    Map<String,Object> GetPaymentsStatistics();
}
