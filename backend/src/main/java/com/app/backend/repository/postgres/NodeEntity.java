package com.app.backend.repository.postgres;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "nodes")

public class NodeEntity {

    @Id
    private Long id;

    @Column(nullable = false)
    private String value;

    @Column(name = "parent_id")
    private Long parentId;

    protected NodeEntity() {
    }

    public NodeEntity(Long id, String value, Long parentId) {
        this.id = id;
        this.value = value;
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public Long getParentId() {
        return parentId;
    }
}