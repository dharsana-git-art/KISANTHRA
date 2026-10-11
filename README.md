# Kishanthra — Agricultural Lending & Farmer Protection Platform

A runnable, source-first full-stack project for local development: React + TypeScript, Java 21 / Spring Boot REST microservices, PostgreSQL, farmer/banker role separation, loan eligibility assessment, six-language selector (English, Hindi, Tamil, Telugu, Kannada and Malayalam), voice input/read-out, document upload, and optional Microsoft Azure integrations.

## Services
- `auth-service` (8081): registration/login and role-based JWT issuance.
- `farmer-service` (8082): farmer profile, land/crop/income/financial fields and document uploads.
- `risk-service` (8083): explainable baseline risk score and eligible-amount estimate.
- `loan-service` (8084): loan application lifecycle; calls farmer-service and risk-service over REST.
- `frontend` (5173): responsive dashboard, role-specific views, multilingual UI, browser voice support.

## Start locally (Docker recommended)
1. Install Docker Desktop and ensure it is running.
2. Copy `.env.example` to `.env` and set a long `JWT_SECRET` (at least 32 characters).
3. From this folder run `docker compose up --build`.
4. Open `http://localhost:5173`.
5. Register a farmer or banker account. Registration creates the role you select; for real deployments, banker role assignment must be invitation/admin-controlled (do not expose self-service banker registration).

The database is persistent in the `kishanthra-db` volume. To reset local data: `docker compose down -v`.

## Windows / VS Code
Docker Desktop is the simplest one-command path. To run services individually, use Java 21 and Maven 3.9+: `mvn spring-boot:run` in each backend service directory; set `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`, `JWT_SECRET`, and service URLs as shown in `.env.example`. Frontend: `cd frontend`, `npm install`, `npm run dev`.

## End-to-end flow
1. Register/login as farmer.
2. Save farmer profile (land area, crop, season, yield, annual income, obligations, prior repayment history).
3. Upload land record / supporting document (PDF/JPG/PNG; max 10 MB per file).
4. Submit a loan request with purpose, amount and tenure.
5. Loan service fetches profile from farmer-service, calls risk-service, saves the result, and returns a score, category, eligible amount, factors and recommendation.
6. Banker dashboard lists applications and lets the banker record a decision. The score is decision support, not an automatic credit sanction.

## Risk model: important
The included score is an **explainable rules baseline**, not a trained/validated credit model and not a real lender's sanction decision. It uses profile inputs and simple crop-season exposure. It does not query private credit bureau records. Never use it for real lending without validation, fairness testing, consent, audited data, and a regulated lending workflow. The amount is computed as `requested amount × score-derived eligibility ratio`, then capped by a conservative income/land affordability ceiling; the displayed example is illustrative.

## Microsoft services
Optional Azure integrations are environment-driven so the app still works locally without cloud credentials:
- **Azure Blob Storage**: set `AZURE_STORAGE_CONNECTION_STRING` and `AZURE_STORAGE_CONTAINER` on farmer-service to store uploads in Blob Storage; otherwise files are stored in the service's `uploads/` directory (local development only).
- Azure AI Foundry / Azure OpenAI can be added as a separate explanation service, but it is **not wired into this ZIP**. The risk service currently returns a deterministic, explainable rules baseline.
- Deploy containers to **Azure Container Apps**, PostgreSQL to **Azure Database for PostgreSQL**, and secrets to **Azure Key Vault** for production. Cloud resources are not provisioned automatically by this source ZIP.

## Security / production checklist
- Replace the development JWT secret, disable open banker registration, add email/OTP verification, CSRF/CORS hardening, rate limiting and audit logging.
- Add antivirus scanning, signed download URLs, retention policies and encryption for land documents.
- Use HTTPS, Azure Key Vault, private networking, backups, monitoring, model validation and human review.
- This project is a working development foundation, not a certified production banking system.


## Added in the integrated project package
- **Government scheme centre:** searchable catalogue of 31 agriculture/allied-sector programmes with short benefit and eligibility summaries plus official portal links. Scheme details and availability can change; users should verify current notifications before applying.
- **EMI calculator:** adjustable principal, annual interest rate and tenure with monthly EMI, total repayment and total-interest estimates. This is an estimate, not a lender quote.
- **Expanded assistant guidance:** offline topic-based responses for loan application steps, KCC, PMFBY, documents, risk score, government schemes and EMI. Browser speech recognition and speech synthesis are used where supported. This assistant is **not a connected generative-AI service** and does not query live government/bank records; configure a real AI provider before presenting it as a production AI assistant.
- **Accessibility:** larger default text and more readable assistant/form controls.
- **Build correction:** the authentication screen JSX has the missing closing element corrected; CSS module declaration is included.

## Important limitations before production
This is a development/demo baseline, not a bank-ready deployment. The risk score is an illustrative rules-based estimate and is not validated for credit decisions. Scheme data is a manually curated directory and must be reviewed by a domain expert. Public self-registration currently allows selecting a banker role for demo purposes; restrict banker creation to invitations/admin provisioning before any real deployment. Review authentication/authorization on every API route, upload malware scanning, encryption, retention policy, audit logging, rate limiting, secrets and regulatory requirements before using real farmer data.

## Start the updated package
From the extracted `Kishanthra-Enterprise` directory in PowerShell:
```powershell
Copy-Item .env.example .env
docker compose up --build
```
Then open `http://localhost:5173`. Keep Docker Desktop running. If you already ran the project before, preserve your database by **not** using `docker compose down -v`.


## Latest usability updates
- Login tagline: “Every farm deserves a fair chance to grow.” The platform brand is Kishanthra.
- Added English, Hindi, Tamil, Telugu, Kannada and Malayalam language choices. Core labels and the scheme/EMI page controls include Telugu and Kannada translations; some detailed explanatory content and scheme descriptions may remain in English.
- Simplified the loan-application guidance text and assessment summary.
- Farmer application history includes CSV report download, search and status filters. Open the CSV in Excel or another spreadsheet app.
- Banker workspace has five navigation areas: overview, all applications, risk insights, government schemes and EMI calculator. The application queue has visible Approve, Review and Reject actions, plus refresh/export controls.
- Added repayment records in the farmer profile. Enter each instalment's due date, payment date and amounts; the system calculates late/on-time counts from those saved records. This does not connect to bank transaction feeds or credit bureaus, so only enter verified history. If a bank supplies an authorised repayment feed in a future version, those records can be imported instead.

## Build verification note
The ZIP structure and source files were checked. A full frontend build could not be completed in the packaging environment because package installation timed out; run `npm install` and `npm run build` from `frontend` after extraction, or use `docker compose up --build` from the project root. The application should be tested with sample data before real use.
### Browser voice support

The voice controls use the browser Web Speech APIs (`SpeechRecognition` where supported and `speechSynthesis` for spoken responses). They do not require Google Gemini, Ollama, or a separate AI service. Recognition support and installed voices vary by browser and operating system; use a current Chrome or Edge browser and allow microphone access. The existing assistant answers using its built-in topic guidance; browser speech alone does not provide open-ended generative AI.
