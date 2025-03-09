package com.mycompany.securitychecker.rules;

import com.github.javaparser.ast.Node;
import com.mycompany.securitychecker.model.Issue;
import com.mycompany.securitychecker.model.Severity;

import java.util.List;

/**
 * Abstract base class for all security rules.
 * Each rule inspects the AST and returns a list of Issues found.
 */
public abstract class SecurityRule {
    protected String ruleName;
    protected Severity severity;

    public SecurityRule(String ruleName, Severity severity) {
        this.ruleName = ruleName;
        this.severity = severity;
    }

    public String getRuleName() {
        return ruleName;
    }

    public Severity getSeverity() {
        return severity;
    }

    /**
     * Analyze a given AST Node (e.g., a CompilationUnit) and return a list of Issues.
     * @param node     The AST node to inspect
     * @param fileName The file name from which the node originated
     * @return A list of security issues found
     */
    public abstract List<Issue> analyze(Node node, String fileName);
}
