package uz.limon.chatsecurity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Authorities {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorities_id_seq")
    @SequenceGenerator(name = "authorities_id_seq", sequenceName = "authorities_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "authority")
    private String authority;


}
