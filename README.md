# ms-statement-service

Spring Boot statement API backed by bundled YAML data and Jackson mapping.
Imports the PCB shared dependency BOM so upstream dependency fixes flow to consumers.

## Prerequisite

Use Java 17 and Maven. Install pcb-common from the tag selected by this service:

```bash
git clone --branch v$(grep -oP '(?<=<pcb-common.version>)[^<]+' pom.xml) https://github.com/defense-factory/pcb-common /tmp/pcb-common && mvn -B -f /tmp/pcb-common install
```

Use a fresh `/tmp/pcb-common` destination for this command.
To remediate shared dependency CVEs, fix pcb-common first, bump its version and
tag it, then bump `pcb-common.version` here and install that tag.

## Run

```bash
mvn spring-boot:run
```

The service listens on port 8080. `GET /health` returns status;
`GET /statements/account-1001` returns JSON loaded from `statements.yaml`.
Unknown accounts return 404. YAML is a bundled resource, not user-supplied input.

## Test

```bash
mvn -B test
```

⚠️ This repo contains deliberately vulnerable dependencies for demo purposes —
do not deploy. Seeded findings include CVE-2022-42003 and CVE-2022-42004
(Jackson databind), and CVE-2022-1471 (SnakeYAML), managed by pcb-common,
plus the intentionally old Spring Boot 2.7.5 stack.
