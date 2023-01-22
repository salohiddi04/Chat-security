package uz.limon.chatsecurity.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message")
@Data
public class Message {

    @Id
    @GeneratedValue(generator = "message_id_seq")
    @SequenceGenerator(name = "message_id_seq", sequenceName = "message_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "chat_id")
    private Integer chatId;

    @Column(name = "author_id")
    private Integer authorId;

    @Column(name = "type")
    private String type;

    @Column(name = "content")
    private String content;

    @Column(name = "ext")
    private String ext;

    @Column(name = "created_at")
    private Date createdAt;
}
