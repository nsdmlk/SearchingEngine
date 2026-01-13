package searchengine.services;

import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class IndexingService {
    
    private final AtomicBoolean indexingInProgress = new AtomicBoolean(false);
    
    public synchronized boolean startIndexing() {
        if (indexingInProgress.get()) {
            return false;
        }
        
        indexingInProgress.set(true);

        new Thread(this::runIndexing).start();
        
        return true;
    }
    
    private void runIndexing() {
        try {
            System.out.println("Запуск полной индексации...");
            
            // Здесь будет основная логика индексации:
            // 1. Получение списка сайтов для индексации
            // 2. Для каждого сайта:
            //    - Парсинг главной страницы
            //    - Рекурсивный обход всех ссылок
            //    - Сохранение страниц в базу данных
            //    - Парсинг и сохранение лемм
            
            System.out.println("Индексация завершена");
            
        } catch (Exception e) {
            System.err.println("Ошибка при индексации: " + e.getMessage());
            e.printStackTrace();
        } finally {
            indexingInProgress.set(false);
        }
    }
    
    
    public boolean isIndexingInProgress() {
        return indexingInProgress.get();
    }
    
    public synchronized void stopIndexing() {
        indexingInProgress.set(false);
        System.out.println("Индексация остановлена");
    }
}