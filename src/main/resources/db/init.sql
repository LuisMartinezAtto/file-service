-- DFSM.dbo.announcements definition

-- Drop table

-- DROP TABLE DFSM.dbo.announcements;

CREATE TABLE announcements
(
    id                 numeric(19, 0) IDENTITY(1,1) NOT NULL,
    creation_date      datetime NULL,
    creation_user      datetime NULL, [
    date]
    datetime
    NULL,
    downloaded_by_user bit NOT NULL,
    name               varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    title              varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    user_id            numeric(19, 0) NULL,
    CONSTRAINT PK__announce__3213E83FB165F84D PRIMARY KEY (id)
);


CREATE TABLE attachments
(
    id              numeric(19, 0) NOT NULL,
    file_extension  varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    file_name       varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    source_url      varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    notification_id numeric(19, 0) NULL,
    external_id     varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    announcement_id numeric(19, 0) NULL,
    CONSTRAINT PK__attachme__3213E83FDF930617 PRIMARY KEY (id)
);


-- DFSM.dbo.attachments foreign keys

ALTER TABLE attachments
    ADD CONSTRAINT FKetytrjank57m38iwf31wl0l5e FOREIGN KEY (announcement_id) REFERENCES announcements (id);
ALTER TABLE attachments
    ADD CONSTRAINT FKqdvavpvswdcv299rhxvxmvv4c FOREIGN KEY (notification_id) REFERENCES notifications (id);
