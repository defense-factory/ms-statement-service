package com.defensefactory.statement;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

@Component
public class StatementStore {
    private final Map<String, Object> statements;
    private final ObjectMapper objectMapper;

    public StatementStore(ObjectMapper objectMapper) throws IOException {
        this.objectMapper = objectMapper;
        try (InputStream input = new ClassPathResource("statements.yaml").getInputStream()) {
            statements = new Yaml().load(input);
        }
    }

    public Statement find(String accountId) {
        Object data = statements.get(accountId);
        return data == null ? null : objectMapper.convertValue(data, Statement.class);
    }

    public record Statement(String accountId, String period, String currency, double closingBalance) {
    }
}
