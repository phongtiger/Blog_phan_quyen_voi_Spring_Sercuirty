package com.codegym.cms.model;

import javax.persistence.*;

@Entity
@Table(name = "blogs2")
public class Blog {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String tittle;
    private String content;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Blog() {}

    public Blog(String tittle, String content) {
        this.tittle = tittle;
        this.content = content;
    }

    @Override
    public String toString() {
        return String.format("Blog[id=%d, firstName='%s', lastName='%s']", id, tittle, content);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

