package com.mycompany.securitychecker.report;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.securitychecker.model.Issue;

import java.util.List;

/**
 * Responsible for producing reports in JSON, XML, or HTML format.
 */
public class ReportGenerator {

    /**
     * Generate a report string in the specified format.
     *
     * @param issues A list of Issues to include in the report
     * @param format The desired output format (JSON, XML, HTML)
     * @return A string representation of the report
     */
    public String generateReport(List<Issue> issues, ReportFormat format) {
        switch (format) {
            case XML:
                return generateXmlReport(issues);
            case HTML:
                return generateHtmlReport(issues);
            case JSON:
            default:
                return generateJsonReport(issues);
        }
    }

    /**
     * Generate a JSON report using Gson.
     */
    private String generateJsonReport(List<Issue> issues) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(issues);
    }

    /**
     * Generate a simple XML report.
     */
    private String generateXmlReport(List<Issue> issues) {
        StringBuilder sb = new StringBuilder();
        sb.append("<issues>\n");
        for (Issue issue : issues) {
            sb.append("  <issue>\n");
            sb.append("    <file>").append(issue.getFileName()).append("</file>\n");
            sb.append("    <line>").append(issue.getLineNumber()).append("</line>\n");
            sb.append("    <severity>").append(issue.getSeverity()).append("</severity>\n");
            sb.append("    <description>").append(issue.getDescription()).append("</description>\n");
            sb.append("  </issue>\n");
        }
        sb.append("</issues>");
        return sb.toString();
    }

    /**
     * Generate a simple HTML report.
     */
    private String generateHtmlReport(List<Issue> issues) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n<html>\n<head>\n<title>Security Report</title>\n</head>\n<body>\n");
        sb.append("<h1>Security Report</h1>\n");
        sb.append("<table border='1'>\n");
        sb.append("<tr><th>File</th><th>Line</th><th>Severity</th><th>Description</th></tr>\n");

        for (Issue issue : issues) {
            sb.append("<tr>");
            sb.append("<td>").append(issue.getFileName()).append("</td>");
            sb.append("<td>").append(issue.getLineNumber()).append("</td>");
            sb.append("<td>").append(issue.getSeverity()).append("</td>");
            sb.append("<td>").append(issue.getDescription()).append("</td>");
            sb.append("</tr>\n");
        }

        sb.append("</table>\n</body>\n</html>");
        return sb.toString();
    }
}
