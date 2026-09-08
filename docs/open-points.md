# DishSpawn — Open Points Register

> **Document status:** a living list, not a step deliverable. It records decisions that
> were made **deliberately partial** — a lightweight fix was applied now, but a fuller
> treatment was consciously deferred. Each entry says what was done, why that was enough
> for today, and what a later revisit would look like.
>
> This is distinct from `02-improvement-opportunities.md`, which is the full ranked menu of
> *possible* work. An item lands here only once we have touched it and chosen to stop
> short on purpose.

---

## OP-1 — Database credentials in `application-local.properties`

**Related finding:** `02-improvement-opportunities.md` §B1 (🔴, Effort S).
**Date decided:** 2026-09-08.
**Status:** partial fix applied; deeper options deferred.

### The issue

`src/main/resources/application-local.properties` is tracked in git and contained the
local database credentials in plaintext:

```
spring.datasource.username=hbstudent
spring.datasource.password=hbstudent
```

These are the well-known throwaway defaults from the Java/Spring course this project grew
out of. The MySQL instance they unlock is only reachable at `localhost:3306`. The repo is
public (`github.com/dikketerry/dishspawn`), so the values are already visible in history.

### What was done now

The hardcoded values were replaced with environment-variable placeholders that keep the
course default as a fallback, so local development still works with zero setup:

```
spring.datasource.username=${DB_USERNAME:hbstudent}
spring.datasource.password=${DB_PASSWORD:hbstudent}
```

A developer who wants to point at a different local database sets `DB_USERNAME` /
`DB_PASSWORD` in their environment; otherwise the app falls back to `hbstudent`.

**Why this is enough for today:** it removes the bare credential literal from the file and
establishes the env-var pattern, at no cost to local-dev convenience. The residual risk is
low — the value is a localhost-only throwaway that is already public in git history, so
nothing is newly exposed.

### What a later revisit looks like

In rough order of increasing effort / disruption:

1. **Drop the default** — `${DB_USERNAME}` / `${DB_PASSWORD}` with no fallback, so the app
   refuses to start unless the environment provides them. Removes anything
   credential-shaped from the file entirely. Costs: every dev and every CI job must now set
   the two variables.
2. **Untrack the file** — `git rm --cached src/main/resources/application-local.properties`
   and add it to `.gitignore` (the same treatment `application-prod.properties` already
   has). Ship an `application-local.properties.example` as the template. Costs: a fresh
   clone no longer runs until the file is created.
3. **Scrub git history** — rewrite past commits to purge the old `hbstudent` values from
   the repository history (e.g. `git filter-repo`). Requires a force-push and breaks every
   existing clone/fork. Only worth it if a *real* secret is ever found to have been
   committed — not for this throwaway.

### Watch out for

`pom.xml` enables Maven resource filtering on `src/main/resources`
(`<filtering>true</filtering>`), so `${...}` tokens are seen by Maven at build time before
Spring sees them at runtime. Maven leaves tokens it cannot resolve untouched, so
`${DB_USERNAME:hbstudent}` passes through to Spring intact — but if resource filtering is
ever reconfigured, re-check that the placeholders still reach the runtime. Verify the
built output with:

```
./mvnw -Plocal -DskipTests process-resources && cat target/classes/application-local.properties
```
