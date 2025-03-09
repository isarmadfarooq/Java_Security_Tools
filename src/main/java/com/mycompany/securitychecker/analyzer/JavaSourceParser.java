package com.mycompany.securitychecker.analyzer;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ParseResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Wraps JavaParser functionality to parse .java files into ASTs.
 */
public class JavaSourceParser {

    public CompilationUnit parse(File file) {
        try (FileInputStream in = new FileInputStream(file)) {
            JavaParser parser = new JavaParser();
            ParseResult<CompilationUnit> result = parser.parse(in);
            return result.getResult().orElse(null); // Fix for ParseResult
        } catch (IOException e) {
            System.err.println("Failed to parse file: " + file.getAbsolutePath());
            e.printStackTrace();
            return null;
        }
    }
}
