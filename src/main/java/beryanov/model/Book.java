package beryanov.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(unique = true, nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String fileName;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private State read;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private State reading;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private State toRead;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private State favourite;

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Quote> quotes;

    @OneToOne(mappedBy = "book", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Critique critique;
}
