package searchengine.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import searchengine.model.Page;
import searchengine.model.Site;
import searchengine.model.Status;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class IndexPageService {
    
    @Value("${sites}")
    private String[] allowedSites;
    
    private final SiteRepository siteRepository;
    private final PageRepository pageRepository;
    
    public IndexPageService(SiteRepository siteRepository, PageRepository pageRepository) {
        this.siteRepository = siteRepository;
        this.pageRepository = pageRepository;
    }
    
    @Transactional
    public boolean indexPage(String urlStr) {
        try {
            URL url = new URL(urlStr);
            String siteUrl = extractSiteUrl(url);
            
            if (!isSiteAllowed(siteUrl)) {
                return false;
            }
            
            Site site = siteRepository.findByUrl(siteUrl)
                    .orElseGet(() -> createSite(siteUrl));
            
            site.setStatus(Status.INDEXING);
            site.setStatusTime(LocalDateTime.now());
            site.setLastError(null);
            siteRepository.save(site);
            
            String path = extractPath(url);
            indexSinglePage(site, path, urlStr);
            
            site.setStatus(Status.INDEXED);
            site.setStatusTime(LocalDateTime.now());
            siteRepository.save(site);
            
            return true;
            
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при индексации страницы: " + e.getMessage());
        }
    }
    
    private Site createSite(String siteUrl) {
        String siteName = extractSiteName(siteUrl);
        Site site = new Site();
        site.setUrl(siteUrl);
        site.setName(siteName);
        site.setStatus(Status.INDEXING);
        site.setStatusTime(LocalDateTime.now());
        return siteRepository.save(site);
    }
    
    private void indexSinglePage(Site site, String path, String fullUrl) throws Exception {
        Optional<Page> existingPage = pageRepository.findBySiteAndPath(site, path);
        
        Page page = existingPage.orElse(new Page());
        page.setSite(site);
        page.setPath(path);
        page.setCode(200);
        page.setContent(fetchPageContent(fullUrl));
        
        pageRepository.save(page);
    }
    
    private String extractSiteUrl(URL url) {
        return url.getProtocol() + "://" + url.getHost();
    }
    
    private String extractPath(URL url) {
        String path = url.getPath();
        if (path.isEmpty()) {
            path = "/";
        }
        String query = url.getQuery();
        if (query != null && !query.isEmpty()) {
            path += "?" + query;
        }
        return path;
    }
    
    private String extractSiteName(String siteUrl) {
        try {
            URL url = new URL(siteUrl);
            String host = url.getHost();
            if (host.startsWith("www.")) {
                host = host.substring(4);
            }
            return host.substring(0, 1).toUpperCase() + host.substring(1);
        } catch (Exception e) {
            return siteUrl;
        }
    }
    
    private boolean isSiteAllowed(String siteUrl) {
        for (String allowedSite : allowedSites) {
            if (siteUrl.equals(allowedSite)) {
                return true;
            }
        }
        return false;
    }
    
    private String fetchPageContent(String url) throws Exception {
        // Здесь будет реальная загрузка HTML контента
        // Временная заглушка
        return "<html><body>Content of " + url + "</body></html>";
    }
}