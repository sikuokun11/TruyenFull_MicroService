package vn.fit.hcmus.truyenfull_restapi.model;


import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "comic")
public class Comic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String urlname;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Chapter> getChapterList() {
        return chapterList;
    }

    public void setChapterList(List<Chapter> chapterList) {
        if(this.chapterList==null){
            this.chapterList=chapterList;
        }
        else if(this.chapterList!=chapterList){
            this.chapterList.clear();
            if(chapterList!=null){
                this.chapterList.addAll(chapterList);
            }
        };
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        if(this.categoryList==null){
            this.categoryList=categoryList;
        }
        else if(this.categoryList!=categoryList){
            this.categoryList.clear();
            if(categoryList!=null){
                this.categoryList.addAll(categoryList);
            }
        }
    }


    private String author;

    private String source;

    private String status;

    @Column(nullable = true)
    private Float rate;

    // map 1 to many voi chapter
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "comic_id")
    private List<Chapter> chapterList = new ArrayList<>();

//    Map voi Category
    @ManyToMany(cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name="comic_category",
    joinColumns = @JoinColumn(name = "comic_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categoryList = new ArrayList<>();


    public void addChapter(Chapter chapter){
        chapterList.add(chapter);
        chapter.setComic(this);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlname() {
        return urlname;
    }

    public void setUrlname(String urlname) {
        this.urlname = urlname;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }
}
