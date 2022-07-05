package guru.springframework.spring5webapp.bootstrap;

import guru.springframework.spring5webapp.domain.Author;
import guru.springframework.spring5webapp.domain.Book;
import guru.springframework.spring5webapp.domain.Publisher;
import guru.springframework.spring5webapp.repo.AuthorRepository;
import guru.springframework.spring5webapp.repo.BookRepository;
import guru.springframework.spring5webapp.repo.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BootstrapData implements CommandLineRunner {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Author eric = new Author("Eric", "Evans");
        Book ddd = new Book("Domain Driven Design", "123123");
        eric.getBooks().add(ddd);
        ddd.getAuthors().add(eric);

        authorRepository.save(eric);
        bookRepository.save(ddd);

        Author rod = new Author("Rod", "Johnson");
        Book noEJB = new Book("J2EE Development Without EJB", "39928938493");
        rod.getBooks().add(noEJB);
        noEJB.getAuthors().add(rod);
        authorRepository.save(rod);
        bookRepository.save(noEJB);

        System.out.println("data bootstrap...");
        System.out.println("number of books: " + bookRepository.count());

        Publisher wiley = new Publisher("Wiley & Associates", "Chicago");
        publisherRepository.save(wiley);
        System.out.println("there is " + publisherRepository.count() + " publisher");

        ddd.setPublisher(wiley);
        bookRepository.save(ddd);
        noEJB.setPublisher(wiley);
        bookRepository.save(noEJB);
        wiley.getBooks().add(noEJB);
        wiley.getBooks().add(ddd);
        publisherRepository.save(wiley);

        System.out.println("publishers: "+ publisherRepository.count());
        Optional<Book> findMe = bookRepository.findById(noEJB.getId());
        System.out.println(findMe);
//        System.out.println(findMe.get().getAuthors());

    }
}
