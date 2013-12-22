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
    Map<String,Object> GetClientCreditsStatistics(long client_id);
    Map<String,Object> GetClientApplicationsStatistics(long client_id);
    Map<String,Object> GetClientPriorsStatistics(long client_id);
    Map<String,Object> GetClientProlongationsStatistics(long client_id);
    Map<String,Object> GetClientPaymentsStatistics(long client_id);
}
