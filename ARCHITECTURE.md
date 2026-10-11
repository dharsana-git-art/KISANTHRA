# Architecture and evaluation mapping

```text
React + TypeScript UI (5173)
  ├── auth-service (8081) ── PostgreSQL: user_accounts
  ├── farmer-service (8082) ── PostgreSQL: farmer_profiles + documents
  └── loan-service (8084) ──REST──> farmer-service (8082)
          └───────────────REST──> risk-service (8083)
                                   └── explainable rule assessment
```

| Requirement | Implementation |
|---|---|
| Farmer and banker authentication | BCrypt password hashes, JWT issuance, role-specific UI navigation |
| Farmer profile | Land area/ownership, crop/season/yield, income, debt and payment history |
| Loan request | Purpose, requested amount and tenure |
| Data analysis | Deterministic income/debt/land/repayment/crop-season factors |
| AI/risk assessment | Explainable 0–100 rules baseline; factors and category returned |
| Relevant data source | User-reported profile + configured crop/season fields; live external data connector can be added |
| Spring Boot API | Four separate Spring Boot services |
| REST inter-service communication | Loan service calls farmer service and risk service using WebClient |
| Database | PostgreSQL/JPA; local persistent Docker volume |
| End-to-end flow | Save profile → upload document → assess loan → view factor breakdown → banker decision |
| Microsoft services | Azure Blob SDK integration for uploaded files when a connection string is configured; Azure deployment guidance for Container Apps, Azure Database for PostgreSQL, Key Vault |
| Voice and five languages | Browser speech recognition/speech synthesis with locale selection; core UI labels include Tamil, English, Marathi, Malayalam and Hindi (full professional translations should be reviewed before public deployment) |
| 3D / motion | CSS perspective, orbital visual, animated layered fields and reduced-motion accessibility |

## Important implementation note
The backend exposes role claims in JWTs and the UI uses them for navigation, but the starter endpoints are not all protected by server-side JWT authorization yet. Add Spring Security JWT validation and farmer ownership checks before any real deployment. Banker list/decision endpoints must be restricted to banker roles. This is a development scaffold and must not be described as production-secure.
