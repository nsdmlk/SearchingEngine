package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import searchengine.model.Page;
import searchengine.model.Site;

import java.util.List;
import java.util.Optional;

@Repository
public interface PageRepository extends JpaRepository<Page, Integer> {
    
    Optional<Page> findBySiteAndPath(Site site, String path);
    
    List<Page> findBySite(Site site);
    
    List<Page> findByCode(int code);
    
    List<Page> findBySiteAndCode(Site site, int code);
    
    void deleteBySite(Site site);
    
    boolean existsBySiteAndPath(Site site, String path);
    
    @Query("SELECT p FROM Page p WHERE p.content LIKE %:text%")
    List<Page> findByContentContaining(@Param("text") String text);
    
    @Query("SELECT p FROM Page p WHERE p.site = :site AND p.content LIKE %:text%")
    List<Page> findBySiteAndContentContaining(@Param("site") Site site, 
                                             @Param("text") String text);
    
    long countBySite(Site site);
    
    long countBySiteAndCode(Site site, int code);
}