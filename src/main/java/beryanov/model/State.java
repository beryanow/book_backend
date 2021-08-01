package beryanov.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true, nullable = false)
    private String id;

    @Column
    private boolean flag;

    @Column(length = 1)
    private String rating;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdDate;

    @ToString.Exclude
    @OneToMany(mappedBy = "read")
    private List<Book> booksRead;

    @ToString.Exclude
    @OneToMany(mappedBy = "reading")
    private List<Book> booksReading;

    @ToString.Exclude
    @OneToMany(mappedBy = "toRead")
    private List<Book> booksToRead;

    @ToString.Exclude
    @OneToMany(mappedBy = "favourite")
    private List<Book> booksFavourite;
}
