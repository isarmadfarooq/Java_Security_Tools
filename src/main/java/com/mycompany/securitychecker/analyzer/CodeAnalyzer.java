package com.mycompany.securitychecker.analyzer;

import com.github.javaparser.ast.CompilationUnit;
import com.mycompany.securitychecker.model.Issue;
import com.mycompany.securitychecker.report.ReportGenerator;
import com.mycompany.securitychecker.report.ReportFormat;
import com.mycompany.securitychecker.rules.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Main driver that orchestrates scanning of Java files and applying security rules.
 */
public class CodeAnalyzer {

    private final JavaSourceParser parser;
    private final TaintAnalysisEngine taintAnalysisEngine;
    private final List<SecurityRule> rules;

    public CodeAnalyzer() {
        this.parser = new JavaSourceParser();
        this.taintAnalysisEngine = new TaintAnalysisEngine();
        this.rules = new ArrayList<>();

        // Register the default security rules
        rules.add(new SqlInjectionRule());
        rules.add(new CommandInjectionRule());
        rules.add(new HardCodedCredentialsRule());
        // Additional rules can be added here

        System.out.println("✅ Security rules initialized: " + rules.size());
    }

    /**
     * Analyzes a directory of .java files.
     * @param directoryPath Path to the directory containing .java files
     * @return A list of Issues found across all files
     */
    public List<Issue> analyzeDirectory(String directoryPath) {
        System.out.println("\n📂 Scanning directory: " + directoryPath);

        List<Issue> allIssues = new ArrayList<>();
        File dir = new File(directoryPath);

        if (!dir.exists() || !dir.isDirectory()) {
            System.err.println("❌ Error: Invalid directory - " + directoryPath);
            return allIssues;
        }

        File[] files = dir.listFiles((d, name) -> name.endsWith(".java"));
        System.out.println("🔍 Found Java Files: " + (files == null ? "None" : files.length));

        if (files == null || files.length == 0) {
            System.err.println("⚠️ No .java files found in: " + directoryPath);
            return allIssues;
        }

        for (File file : files) {
            System.out.println("📄 Analyzing file: " + file.getName());

            // Parse Java file
            CompilationUnit cu = parser.parse(file);
            if (cu == null) {
                System.err.println("❌ Failed to parse: " + file.getName());
                continue;
            }

            System.out.println("✅ Successfully parsed: " + file.getName());

            // Apply each security rule
            for (SecurityRule rule : rules) {
                List<Issue> issues = rule.analyze(cu, file.getName());
                if (!issues.isEmpty()) {
                    System.out.println("⚠️ Issues found in " + file.getName() + ": " + issues.size());
                    allIssues.addAll(issues);
                }
            }

            // Run taint analysis
            List<Issue> taintIssues = taintAnalysisEngine.runTaintAnalysis(cu, file.getName());
            if (!taintIssues.isEmpty()) {
                System.out.println("⚠️ Taint Issues found in " + file.getName() + ": " + taintIssues.size());
                allIssues.addAll(taintIssues);
            }
        }

        System.out.println("✅ Total Issues Found: " + allIssues.size());
        return allIssues;
    }

    /**
     * Main method for command-line usage.
     * Usage: java com.mycompany.securitychecker.analyzer.CodeAnalyzer <directoryPath> [<reportFormat>]
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java com.mycompany.securitychecker.analyzer.CodeAnalyzer <directoryPath> [<reportFormat>]");
            System.exit(1);
        }

        String directoryPath = args[0];
        ReportFormat format = ReportFormat.JSON; // default format
        if (args.length >= 2) {
            try {
                format = ReportFormat.valueOf(args[1].toUpperCase());
            } catch (IllegalArgumentException e) {
                System.err.println("❌ Unknown report format, defaulting to JSON.");
            }
        }

        CodeAnalyzer analyzer = new CodeAnalyzer();
        List<Issue> issues = analyzer.analyzeDirectory(directoryPath);

        // Generate report
        ReportGenerator reportGenerator = new ReportGenerator();
        String reportOutput = reportGenerator.generateReport(issues, format);
        System.out.println("\n📜 Final Report Output:");
        System.out.println(reportOutput);
    }
}
