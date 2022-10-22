package com.example.crudweb.repository;

import com.sun.xml.bind.v2.model.core.ID;
import com.example.crudweb.entity.User;
import java.util.List;

public interface UserRepository {

    void delete(Long id);

    void update(User item);

    void add(User item);

    User getById(Long id);

    List<User> getAll();
}
