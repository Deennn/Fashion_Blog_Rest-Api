package com.example.fashion_blog.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = "title")})
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column( nullable = false)
    private String description;
    @Column(nullable = false)
    private String content;
   @NotNull
    private long likeCount;


//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name="post_category",joinColumns = {
//            @JoinColumn(name="post_id", referencedColumnName = "id")}
//            ,inverseJoinColumns = {@JoinColumn(name="category_id",referencedColumnName = "id")})
//    private Set<Category> categories = new HashSet<Category>();


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categories_id")
    private Category category;



    @OneToMany( mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments  = new HashSet<>();


}
