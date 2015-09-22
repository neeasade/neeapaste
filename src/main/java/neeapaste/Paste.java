package neeapaste;

import javax.persistence.*;
import java.rmi.activation.ActivationID;

/**
 * Class to represent a paste.
 */
@Entity
public class Paste {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long views;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User Owner;


    // No args for the JPA spec.
    protected Paste() { }

    public Paste( String title, String content ) {
        this.title = title;
        this.content = content;
        this.views = Long.parseLong("0");
    }

    public User getOwner()
    {
        return Owner;
    }

    public void setOwner(User aUser)
    {
        this.Owner = aUser;
    }

    public void setViews(Long aValue) {
        views = aValue;
    }

    public long getViews() {
        return views;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

}
