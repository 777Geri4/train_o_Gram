package com.five.train_o_gram.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "avatar")
public class Avatar {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;

    @Column(name = "path")
    private String path;

    @Column(name = "created")
    private Date created;

    public Avatar() {
    }

    public Avatar(String path, Date created, User owner) {
        this.path = path;
        this.created = created;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
