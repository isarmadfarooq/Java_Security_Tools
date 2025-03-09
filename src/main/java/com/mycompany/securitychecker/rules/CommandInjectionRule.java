package com.mycompany.securitychecker.rules;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.mycompany.securitychecker.model.Issue;
import com.mycompany.securitychecker.model.Severity;

import java.util.ArrayList;
import java.util.List;

/**
 * Flags potential command injection via Runtime.getRuntime().exec() usage.
 */
public class CommandInjectionRule extends SecurityRule {

    public CommandInjectionRule() {
        super("Command Injection Risk", Severity.HIGH);
    }

    @Override
    public List<Issue> analyze(Node node, String fileName) {
        List<Issue> issues = new ArrayList<>();

        node.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(MethodCallExpr callExpr, Void arg) {
                // Check if we're calling "exec" on "Runtime.getRuntime()"
                if ("exec".equals(callExpr.getNameAsString()) &&
                    callExpr.toString().contains("Runtime.getRuntime()")) {
                    Issue issue = new Issue();
                    issue.setDescription("Potential command injection via exec() call.");
                    issue.setSeverity(severity);
                    issue.setFileName(fileName);
                    issue.setLineNumber(callExpr.getBegin().map(pos -> pos.line).orElse(-1));
                    issues.add(issue);
                }
                super.visit(callExpr, arg);
            }
        }, null);

        return issues;
    }
}
