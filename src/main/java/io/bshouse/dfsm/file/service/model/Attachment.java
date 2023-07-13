package io.bshouse.dfsm.file.service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
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
    private String elementId;
    private Long externalFileId;
    @Enumerated(EnumType.STRING)
    private TypeFile typeFile;
    private Long createdByUserId;
    private Date creationDate;
    private Date updateDate;
}
