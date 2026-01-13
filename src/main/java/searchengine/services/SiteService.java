package searchengine.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import searchengine.model.Site;
import searchengine.model.Status;
import searchengine.repository.SiteRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SiteService {
    
    private final SiteRepository siteRepository;
    
    public SiteService(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }
    
    /**
     * Сохраняет или обновляет сайт
     */
    @Transactional
    public Site saveOrUpdate(Site site) {
        return siteRepository.save(site);
    }
    
    /**
     * Находит сайт по URL
     */
    public Optional<Site> findByUrl(String url) {
        return siteRepository.findByUrl(url);
    }
    
    /**
     * Проверяет, существует ли сайт
     */
    public boolean existsByUrl(String url) {
        return siteRepository.existsByUrl(url);
    }
    
    /**
     * Получает все сайты
     */
    public List<Site> findAll() {
        return siteRepository.findAll();
    }
    
    /**
     * Находит сайты по статусу
     */
    public List<Site> findByStatus(Status status) {
        return siteRepository.findByStatus(status);
    }
    
    /**
     * Обновляет статус сайта
     */
    @Transactional
    public void updateStatus(int siteId, Status status) {
        siteRepository.updateSiteStatus(siteId, status, LocalDateTime.now());
    }
    
    /**
     * Обновляет ошибку сайта
     */
    @Transactional
    public void updateError(int siteId, String error) {
        siteRepository.updateSiteError(siteId, error, LocalDateTime.now());
    }
    
    /**
     * Удаляет сайт по URL
     */
    @Transactional
    public void deleteByUrl(String url) {
        siteRepository.deleteByUrl(url);
    }
    
    /**
     * Находит все сайты с ошибками
     */
    public List<Site> findSitesWithErrors() {
        return siteRepository.findSitesWithErrors();
    }
    
    /**
     * Считает сайты по статусу
     */
    public long countByStatus(Status status) {
        return siteRepository.countByStatus(status);
    }
}