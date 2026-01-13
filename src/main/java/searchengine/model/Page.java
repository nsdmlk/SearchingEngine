package searchengine.model;

import javax.persistence.*;

@Entity
@Table(name = "page", indexes = {@Index(name = "path_index", columnList = "path")})
public class Page {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false, referencedColumnName = "id")
    private Site site;
    
    @Column(name = "path", columnDefinition = "TEXT", nullable = false)
    private String path;
    
    @Column(name = "code", nullable = false)
    private int code;
    
    @Lob
    @Column(name = "content", columnDefinition = "MEDIUMTEXT", nullable = false)
    private String content;
    
    public Page() {
    }
    
    public Page(Site site, String path, int code, String content) {
        this.site = site;
        this.path = path;
        this.code = code;
        this.content = content;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Site getSite() {
        return site;
    }
    
    public void setSite(Site site) {
        this.site = site;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    @Override
    public String toString() {
        return "Page{" +
                "id=" + id +
                ", site=" + (site != null ? site.getName() : "null") +
                ", path='" + path + '\'' +
                ", code=" + code +
                ", content length=" + (content != null ? content.length() : 0) +
                '}';
    }
}