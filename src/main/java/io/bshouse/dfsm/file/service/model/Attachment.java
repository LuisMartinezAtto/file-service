package io.bshouse.dfsm.file.service.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "attachments")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String originalName;
    private String mimeType;
    @Column(unique = true)
    private Long elementId;
    private String urlSource;
    @Enumerated(EnumType.STRING)
    private TypeFile typeFile;
    private Long createdByUserId;
    private Date creationDate;
    private Date updateDate;
    private Long fileSize;
}
