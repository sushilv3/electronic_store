package com.sushil.electronic.store.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @Column(name = "id")
    private String categoryId;
    @Column(name = "category_title", length = 60, nullable = false)
    private String title;
    @Column(name = "category_desc", length = 50)
    private String description;
    private String coverImage;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "category",fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

}
