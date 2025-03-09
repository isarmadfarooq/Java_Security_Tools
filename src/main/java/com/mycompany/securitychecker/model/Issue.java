package com.mycompany.securitychecker.model;

public class Issue {
    private String description;
    private Severity severity;
    private String fileName;
    private int lineNumber;

    public Issue() {
    }

    public Issue(String description, Severity severity, String fileName, int lineNumber) {
        this.description = description;
        this.severity = severity;
        this.fileName = fileName;
        this.lineNumber = lineNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return String.format(
            "Issue[file=%s, line=%d, severity=%s, description=%s]",
            fileName, lineNumber, severity, description
        );
    }
}
