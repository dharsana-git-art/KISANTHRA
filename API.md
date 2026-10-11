# API quick reference

- `POST /api/auth/register` `{name,email,password,role}` — role is `FARMER` or `BANKER` (self-service banker selection is development-only; restrict it in deployment).
- `POST /api/auth/login` `{email,password}` — returns JWT and user identity.
- `GET /api/farmers/{email}` and `POST /api/farmers` — farmer profile.
- `POST /api/farmers/{email}/documents` multipart field `file` — PDF/JPG/PNG up to 10 MB.
- `GET /api/farmers/{email}/repayments` — repayment records sorted by due date.
- `POST /api/farmers/{email}/repayments` `{loanReference,dueDate,paymentDate,amountDue,amountPaid}` — records an instalment and derives `ON_TIME`, `LATE`, or `PENDING` from the due/payment dates and amounts.
- `POST /api/loans/assess` `{farmerEmail,purpose,requestedAmount,tenureMonths}` — reads farmer profile via REST, calls risk service via REST, persists assessment.
- `GET /api/loans/farmer/{email}`, `GET /api/loans` — applications.
- `PATCH /api/loans/{id}/decision` `{decision,note}` — decision is `APPROVED`, `REJECTED`, or `NEEDS_REVIEW`.
- `POST /api/risk/assess` — direct rules-baseline assessment.

Health routes: `/api/auth/health`, `/api/farmers/health`, `/api/loans/health`, `/api/risk/health`.
