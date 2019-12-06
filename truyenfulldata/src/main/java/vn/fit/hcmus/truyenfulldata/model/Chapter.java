package vn.fit.hcmus.truyenfulldata.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Proxy;

import javax.persistence.Id;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Entity
@Table(name = "chapter")

public class Chapter  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "index_chapter")
    private Long index; // so thu tu chua chapter


    private String name;

    @Column(columnDefinition = "TEXT")
    private String content;

    @JsonIgnore
    @ManyToOne
    private Comic comic;
}
