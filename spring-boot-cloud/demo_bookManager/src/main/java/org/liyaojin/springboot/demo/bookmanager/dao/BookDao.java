package org.liyaojin.springboot.demo.bookmanager.dao;

import org.liyaojin.springboot.demo.bookmanager.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookDao extends JpaRepository<Book,Long> {
    List<Book> findByReader(String reader);
}
