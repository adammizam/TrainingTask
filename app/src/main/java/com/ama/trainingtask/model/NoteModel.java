package com.ama.trainingtask.model;

import java.io.Serializable;

public class NoteModel implements Serializable {
    private String noteId, userId, noteTitle, noteContent;

    public NoteModel() {
    }

    public NoteModel(String noteId, String userId, String noteTitle, String noteContent) {
        this.noteId = noteId;
        this.userId = userId;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }
}
