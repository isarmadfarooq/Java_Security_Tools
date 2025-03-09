package com.mycompany.securitychecker.rules;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.mycompany.securitychecker.model.Issue;
import com.mycompany.securitychecker.model.Severity;

import java.util.ArrayList;
import java.util.List;

/**
 * Flags potential hardcoded credentials by searching string literals
 * for keywords like "password", "pwd", "secret", etc.
 */
public class HardCodedCredentialsRule extends SecurityRule {

    public HardCodedCredentialsRule() {
        super("Hardcoded Credentials Risk", Severity.MEDIUM);
    }

    @Override
    public List<Issue> analyze(Node node, String fileName) {
        List<Issue> issues = new ArrayList<>();

        node.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(StringLiteralExpr literalExpr, Void arg) {
                String lowerVal = literalExpr.getValue().toLowerCase();
                if (lowerVal.contains("password") ||
                    lowerVal.contains("pwd") ||
                    lowerVal.contains("secret")) {

                    Issue issue = new Issue();
                    issue.setDescription("Hardcoded credentials detected: " + literalExpr.getValue());
                    issue.setSeverity(severity);
                    issue.setFileName(fileName);
                    issue.setLineNumber(literalExpr.getBegin().map(pos -> pos.line).orElse(-1));
                    issues.add(issue);
                }
                super.visit(literalExpr, arg);
            }
        }, null);

        return issues;
    }
}

