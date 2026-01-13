package searchengine.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "site", 
       indexes = @Index(name = "url_index", columnList = "url", unique = true))
public class Site {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "ENUM('INDEXING', 'INDEXED', 'FAILED')", 
            nullable = false)
    private Status status;
    
    @Column(name = "status_time", nullable = false)
    private LocalDateTime statusTime;
    
    @Column(name = "last_error", columnDefinition = "TEXT")
    private String lastError;
    
    @Column(name = "url", columnDefinition = "VARCHAR(255)", nullable = false, unique = true)
    private String url;
    
    @Column(name = "name", columnDefinition = "VARCHAR(255)", nullable = false)
    private String name;
    
    @OneToMany(mappedBy = "site", 
               cascade = CascadeType.ALL, 
               fetch = FetchType.LAZY, 
               orphanRemoval = true)
    private List<Page> pages = new ArrayList<>();
    
    public Site() {}
    
    public Site(String url, String name, Status status) {
        this.url = url;
        this.name = name;
        this.status = status;
        this.statusTime = LocalDateTime.now();
    }
    
    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Status getStatus() { return status; }
    public void setStatus(Status status) { 
        this.status = status; 
        this.statusTime = LocalDateTime.now();
    }
    
    public LocalDateTime getStatusTime() { return statusTime; }
    public void setStatusTime(LocalDateTime statusTime) { this.statusTime = statusTime; }
    
    public String getLastError() { return lastError; }
    public void setLastError(String lastError) { this.lastError = lastError; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public List<Page> getPages() { return pages; }
    public void setPages(List<Page> pages) { this.pages = pages; }
    
    public void addPage(Page page) {
        pages.add(page);
        page.setSite(this);
    }
    
    public void removePage(Page page) {
        pages.remove(page);
        page.setSite(null);
    }
}