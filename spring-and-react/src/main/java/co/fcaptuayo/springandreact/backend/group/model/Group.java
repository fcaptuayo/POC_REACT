package co.fcaptuayo.springandreact.backend.group.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "user_group")
public class Group {

    public static final Group NULL_GROUP = new Group();

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;

    private String address;

    private String city;

    private String stateOrProvince;

    private String country;

    private String postalCode;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Event> events;
}
