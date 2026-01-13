package searchengine.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import searchengine.dto.ApiResponse;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.services.IndexingService;
import searchengine.services.IndexPageService;
import searchengine.services.StatisticsService;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final StatisticsService statisticsService;
    private final IndexingService indexingService;
    private final IndexPageService indexPageService;
    
    public ApiController(StatisticsService statisticsService, 
                        IndexingService indexingService,
                        IndexPageService indexPageService) {
        this.statisticsService = statisticsService;
        this.indexingService = indexingService;
        this.indexPageService = indexPageService;
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> statistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }

    @GetMapping("/startIndexing")
    public ResponseEntity<ApiResponse> startIndexing() {
        if (indexingService.isIndexingInProgress()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Индексация уже запущена"));
        }
        
        boolean started = indexingService.startIndexing();
        
        if (started) {
            return ResponseEntity.ok(new ApiResponse(true));
        } else {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse(false, "Не удалось запустить индексацию"));
        }
    }

    @PostMapping("/indexPage")
    public ResponseEntity<ApiResponse> indexPage(@RequestParam String url) {
        try {
            boolean success = indexPageService.indexPage(url);
            
            if (success) {
                return ResponseEntity.ok(new ApiResponse(true));
            } else {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, "Данная страница находится за пределами сайтов, указанных в конфигурационном файле"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
}