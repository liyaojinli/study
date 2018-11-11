package org.liyaojin.springboot.demo.bookmanager.controller;

import org.liyaojin.springboot.demo.bookmanager.dao.BookDao;
import org.liyaojin.springboot.demo.bookmanager.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    private BookDao dao;

    @Autowired
    public BookController(BookDao dao) {
        this.dao = dao;
    }

    @RequestMapping(path = "/{reader}", method = RequestMethod.GET)
    public String getBooksByReader(@PathVariable(name = "reader") String reader, Model model) {
        List<Book> books = dao.findByReader(reader);
        if (null != books) {
            model.addAttribute("books", books);
        }
        return "books";
    }

    @RequestMapping(path = "/{reader}",method = RequestMethod.POST)
    public String addBook(@PathVariable("reader") String reader, Book book) {
        book.setReader(reader);
        dao.save(book);
        return "redirect:/books/{reader}";
    }
}
