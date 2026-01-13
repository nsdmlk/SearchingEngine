package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import searchengine.model.Site;
import searchengine.model.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<Site, Integer> {
    
    
    Optional<Site> findByUrl(String url);
    
    boolean existsByUrl(String url);
    
    List<Site> findByStatus(Status status);
    
    @Query("SELECT s FROM Site s WHERE s.lastError IS NOT NULL")
    List<Site> findSitesWithErrors();
    
    List<Site> findByNameContainingIgnoreCase(String name);
    
    void deleteByUrl(String url);
    
    @Query("UPDATE Site s SET s.status = :status, s.statusTime = :statusTime WHERE s.id = :id")
    void updateSiteStatus(@Param("id") int id, 
                          @Param("status") Status status, 
                          @Param("statusTime") LocalDateTime statusTime);
    
    @Query("UPDATE Site s SET s.lastError = :error, s.statusTime = :statusTime WHERE s.id = :id")
    void updateSiteError(@Param("id") int id, 
                         @Param("error") String error, 
                         @Param("statusTime") LocalDateTime statusTime);
    
    @Query("SELECT s.url FROM Site s")
    List<String> findAllUrls();
    
    long countByStatus(Status status);
}