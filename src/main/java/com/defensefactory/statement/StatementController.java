package com.defensefactory.statement;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatementController {
    private final StatementStore statementStore;

    public StatementController(StatementStore statementStore) {
        this.statementStore = statementStore;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }

    @GetMapping("/statements/{accountId}")
    public ResponseEntity<StatementStore.Statement> statement(@PathVariable String accountId) {
        StatementStore.Statement statement = statementStore.find(accountId);
        return statement == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(statement);
    }
}
