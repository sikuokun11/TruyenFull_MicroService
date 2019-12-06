package vn.fit.hcmus.truyenfull_restapi.model;



import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "category")
public class Category {
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

    public List<Comic> getComicList() {
        return comicList;
    }

    public void setComicList(List<Comic> comicList) {
        if(this.comicList==null){
            this.comicList=comicList;
        }
        else if(this.comicList!=comicList){
            this.comicList.clear();
            if(comicList!=null){
                this.comicList.addAll(comicList);
            }
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String urlname;

//    @ManyToMany(mappedBy = "categoryList")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="comic_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "comic_id"))
    private List<Comic> comicList = new ArrayList<>();

    public void addComic(Comic comic){
        comicList.add(comic);
    }
}
