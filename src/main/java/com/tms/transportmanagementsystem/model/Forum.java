package com.tms.transportmanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String forumTitle;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "parentForum", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Forum(String forumTitle) {
        this.forumTitle = forumTitle;
    }

    @Override
    public String toString() {
        return getForumTitle();
    }
}
