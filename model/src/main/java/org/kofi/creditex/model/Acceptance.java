package org.kofi.creditex.model;

public enum Acceptance {
    Accepted("Принята"),
    Rejected("Отклонена"),
    InProcess("В рассмотрении");
    private Acceptance(final String text){
        this.text=text;
    }

    private final String text;

    @Override
    public String toString() {
        return text;
    }
}
