package com.mycompany.securitychecker.rules;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.mycompany.securitychecker.model.Issue;
import com.mycompany.securitychecker.model.Severity;

import java.util.ArrayList;
import java.util.List;

/**
 * Flags possible SQL injection vulnerabilities by detecting string concatenations
 * that appear to form SQL queries (containing "select" and "from").
 */
public class SqlInjectionRule extends SecurityRule {

    public SqlInjectionRule() {
        super("SQL Injection Risk", Severity.HIGH);
    }

    @Override
    public List<Issue> analyze(Node node, String fileName) {
        List<Issue> issues = new ArrayList<>();

        node.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(BinaryExpr binExpr, Void arg) {
                if (binExpr.getOperator() == BinaryExpr.Operator.PLUS) {
                    String exprString = binExpr.toString().toLowerCase();
                    if (exprString.contains("select") && exprString.contains("from")) {
                        Issue issue = new Issue();
                        issue.setDescription("Possible SQL injection risk in query construction.");
                        issue.setSeverity(severity);
                        issue.setFileName(fileName);
                        issue.setLineNumber(binExpr.getBegin().map(pos -> pos.line).orElse(-1));
                        issues.add(issue);
                    }
                }
                super.visit(binExpr, arg);
            }
        }, null);

        return issues;
    }
}
