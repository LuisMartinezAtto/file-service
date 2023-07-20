package io.bshouse.dfsm.file.service.model;
public enum TypeFile {
    MAIL_SHOT("MAIL_SHOT"),
    NOTIFICATIONS("NOTIFICATIONS");
    private final String description;

    TypeFile(String description) {
        this.description = description;
    }

    String getDescription() {
        return this.description;
    }
}

