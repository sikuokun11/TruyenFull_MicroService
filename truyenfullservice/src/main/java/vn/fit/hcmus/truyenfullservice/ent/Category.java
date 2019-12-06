package vn.fit.hcmus.truyenfullservice.ent;




import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "category")

public class Category  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String urlname;

    //    @ManyToMany(mappedBy = "categoryList")
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="comic_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "comic_id"))
    private List<Comic> comicList = new ArrayList<>();

    public void addComic(Comic comic){
        comicList.add(comic);
    }
}
