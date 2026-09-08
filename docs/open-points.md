# DishSpawn — Open Points Register

> **Document status:** a living list, not a step deliverable. It records points we have
> **touched and consciously chosen to stop short on** — either a lightweight fix was
> applied and a fuller treatment deferred, or a latent issue was spotted in passing and
> deliberately left for later. Each entry says what the state is, why that is acceptable
> for now, and what a later revisit would look like.
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

---

## OP-2 — `AbstractUnitConverter.convert` ignores `unitTo` unless `unitFrom` is the base unit

**Related finding:** none yet — spotted 2026-09-08 while writing the converter unit tests
for `02-improvement-opportunities.md` §G1 / roadmap item #4.
**Status:** latent, not fixed. No test asserts the buggy path (so the tests do not lock
the bug in).

### The issue

Both `MassConverter.convert(quantity, unitFrom, unitTo)` and
`VolumeConverter.convert(...)` only honour `unitTo` when `unitFrom` is the base unit
(`GRAM` / `MILLILITER`). For any **other** `unitFrom`, the outer `switch` returns
"`quantity` converted to the base unit" and never looks at `unitTo` at all:

```java
case KILOGRAM:
    return quantity / GRAM_TO_KILOGRAM_FACTOR;   // always grams, whatever unitTo says
```

So `massConverter.convert(1.0, KILOGRAM, POUND)` returns **1000.0 (grams)**, not
`2.20…` (pounds). `convert(1, CUP, LITER)` returns millilitres, not litres.

### Why it is acceptable for now

The only production caller is `RecipeIngredient.calculateMass()` / `calculateVolume()`
(`RecipeIngredient.java:125-139`), and both call the method exclusively in the supported
direction — `convert(quantity, someUnit, GRAM)` and `convert(quantity, someUnit,
MILLILITER)`. The class comments even say so explicitly ("input recipe: requires all
MassUnits to convert to GRAM"). Within that contract the result is correct.

It is logged here because the method **signature promises a general `from → to`
conversion** it does not deliver — the next person to reuse `convert` for an
"extract recipe" (base → other unit is handled; other → other is not) or anywhere else
will get silently wrong numbers with no exception.

### What a later revisit looks like

- **Minimal:** two-step internally — convert `unitFrom → base`, then `base → unitTo` — so
  every pair works. The per-unit factors already present are enough; only the control flow
  changes.
- **Better (ties into §A4 / roadmap):** replace the `String` unit names on
  `RecipeIngredient` with the `MassUnit` / `VolumeUnit` enums and collapse the two
  converters plus `massOrVolumeSetter`'s hand-written `switch` into one typed path.
- **Cheap guard in the meantime:** throw `UnsupportedOperationException` when
  `unitFrom != base && unitTo != base`, so the unsupported case fails loudly instead of
  returning a wrong number.

---

## OP-3 — `VolumeConverter` has no reachable exception path

**Related finding:** none — spotted 2026-09-08 alongside OP-2.
**Status:** observation only; nothing to fix. Recorded so the asymmetry with
`MassConverter` is not mistaken for a gap in the tests.

### The observation

`MassConverter.MassUnit` contains `PIECE`, which is **not** handled in either `switch` in
`convert`, so `MassConverter` has two reachable throw paths that the unit tests cover:

- `convert(x, PIECE, GRAM)` → `UnitDoesNotExistException` (outer `default`)
- `convert(x, GRAM, PIECE)` → `UnsupportedOperationException` (inner `default`)

`VolumeConverter.VolumeUnit` has no equivalent — all ten values are handled in both
switches. So with any valid `VolumeUnit` input, neither `default` branch is reachable; the
only way to hit an exception is to pass `null` (→ `NullPointerException` on the `switch`).
`VolumeConverterTest` therefore has no exception cases, by design, not by omission.

### If it ever matters

The dead `default` branches are harmless. If `VolumeUnit` later gains a
non-convertible member (a `PIECE`-style entry), the existing `default: throw` lines will
start doing their job and should get test coverage at that point.
