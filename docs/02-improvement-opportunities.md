# DishSpawn — Improvement Opportunities

> **Document status:** Step 2 deliverable, now also the **live tracker** for Step-2
> execution. Where Step 1 (`01-functional-overview.md`) described *what the app does and how
> it fits together*, this document describes *what could be better and why*. It began as an
> **analysis and a menu**; as of 2026-09-08 we have started acting on it, so each item now
> also carries a **status** (see the §10 table and the per-finding "Update" notes).
>
> Written by reading the source directly on branch `testLint`, 2026-08-07. Evidence is
> cited as `file:line` so you can verify every claim yourself.
>
> **Companion document:** `docs/open-points.md` records fixes we applied *deliberately
> partially* (a light fix now, a fuller one deferred) and latent issues spotted in passing.
> Entries there are cross-referenced from the findings below as **OP-1**, **OP-2**, ….
>
> **Execution log is §13** at the foot of this document.

---

## 0. How to read this document

Improvements are grouped into seven themes:

- **A. Correctness & concurrency** — where the code can produce wrong results or corrupt
  state, especially with more than one user.
- **B. Security**
- **C. Persistence & efficiency** — the database and JPA layer.
- **D. Architecture & design** — how responsibilities are divided.
- **E. Readability & hygiene** — the day-to-day "can I understand and trust this?" layer.
- **F. Libraries & build**
- **G. Testing**

Every item carries two quick tags so you can triage at a glance:

- **Severity** — 🔴 high (can cause wrong behaviour / data loss / security exposure),
  🟠 medium (real problem, limited blast radius today), 🟡 low (hygiene / polish).
- **Effort** — **S** (an afternoon), **M** (a focused session or two), **L** (a project
  in its own right, likely overlapping Steps 3–5).

Section 10 collects everything into a single ranked table — with a suggested order and a
**Status** column tracking what's done, in progress, deferred, or not started. If you only
read one section, read that one — then come back up here for the "why".

**A deliberate framing note.** Many of these findings share a *single root cause*: the app
was built and tested as a **single-user desktop application** (one developer, one browser,
a real Processing window opening on screen). Almost none of that is "bad code" in that
context — it's code that made a different assumption than "a web server with concurrent
users" requires. I'll point this out repeatedly because seeing the *one* underlying
assumption makes a dozen separate findings click into place at once.

---

## 1. Housekeeping first — the uncommitted working tree (your requested "step 0") — ✅ DONE (2026-08-29)

This started as a loose thread: the working tree on `testLint` had **uncommitted changes**
(the in-progress generative-art work from previous sessions):

- `pom.xml` — Java 11 → 19, Lombok annotation-processor path added.
- `graphics/processing/TheSketch.java` — adds `setComplementaryBackground(...)` and a
  `setDominantIngredientColor(...)` setter.
- `graphics/processing/shapes/Shape.java` + `Circle/Ellipse/Triangle/Rectangle.java` —
  adds `applyTextureStyle()` and wires texture → stroke/fill/opacity into rendering.
- `graphics/processing/util/Transformer.java` — passes the ingredient's texture to the shape.

**What was done (2026-08-29):**

- [x] The changes were **committed** on `testLint` (they were coherent and self-contained —
      texture-driven rendering + a complementary-background hook).
- [x] `testLint` was then **fast-forward-merged into `main`** (`main`: `2a700bf` → `bebb240`,
      a clean fast-forward with no divergence and no merge commit).
- [x] `main` was **pushed to GitHub** (`github.com/dikketerry/dishspawn`, public). Remote
      `main` is now at `bebb240`.
- [x] **Security cleanup done in passing:** an old GitHub token was found embedded in plaintext
      in the `origin` remote URL. It was removed from the local git config (both remotes are
      now tokenless; auth flows through the macOS keychain with a fresh classic PAT). The old
      token turned out to be already-dead on GitHub, so there was nothing exploitable to
      revoke. Nothing compromised remains.

So the base is clean and the analysis work below can proceed without tangling with
uncommitted art changes. This section is now historical — no action outstanding.

---

## 2. Corrections to the Step 1 document (accuracy first) — ✅ FOLDED IN (2026-09-08)

While reading the code closely for Step 2, I found that a few statements in
`01-functional-overview.md` — including one *I* added last session — are not quite right.
Honesty about the map matters more than the map looking finished, so here they are.

**All four are now applied to `01-functional-overview.md`** (§2 profiles paragraph, §7
"Generated output", §8 Security Model). Kept below as the record of what changed.

1. **The generated PNGs are *not* in version control.** Step 1 §7 says the ~200 (I
   corrected to 150) `visualN.png` files live "inside the source tree **and in version
   control**." The count is right (150 on disk), but `/src/main/webapp/spawns/` is listed
   in `.gitignore:6`, and `git ls-files` tracks **zero** files under it. So: they sit in
   the source *folder* on disk, but git ignores them. The accurate statement is "generated
   artefacts are written into the source tree on disk but are git-ignored." (My earlier
   correction fixed the number but left the wrong "in version control" clause — mea culpa.)

2. **`application-prod.properties` *is* committed, despite being git-ignored.** It's in
   `.gitignore:5`, but it was committed *before* being ignored (git-ignore doesn't untrack
   already-tracked files), so it's still in history. It contains only `${RDS_*}`
   placeholders, so no real secret leaks — but the file is there.

3. **Local DB credentials *are* committed.** `application-local.properties` is tracked and
   contains `spring.datasource.username=hbstudent` / `password=hbstudent` in plaintext (see
   §B1). Worth stating plainly in the "current state" section.

4. **The app actually runs under the `local` profile, not `prod`.** Step 1 §2 says the
   `prod` profile is "active by default" (true of the *Maven* profile). But
   `application.properties` hard-codes `spring.profiles.active=local`, which is what the
   running app obeys. So the effective default runtime profile is **local**.

None of these change Step 1's conclusions; they tighten its facts.

---

## 3. Theme A — Correctness & concurrency

This is the most important section. These are the places where the code can produce a
**wrong result or corrupted state**, and most of them are invisible with one user and
appear the moment a second user (or a background tab) acts at the same time.

### A1. 🔴 Request state is stored on singleton beans (the big one) — Effort **M**

Spring `@Controller` and `@Service` beans are **singletons**: one instance shared by every
request on every thread. Several of them keep per-request, per-user data in **instance
fields**:

- `SpawnController.java:27-46` — the spawn basket (`ingredientSpawnList`), the results
  (`recipeSpawnList`), the current page lists, paging counters, totals, the
  `findRecipeMethodIsUsed` flag, the `searchKey`, and four message `StringBuilder`s. All
  mutable, all shared.
- `ImageServiceImpl.java:43-44` — `theSketch` and `pImg` (the in-progress image) are
  instance fields, mutated across `generateImage(...)` and later read in `saveVisual(...)`.
- `ImageController.java:24` — `private Recipe recipe;` holds "the recipe being spawned"
  between the generate request and the save request.
- `ChefController.java:26-27` — `totalFoundVisualsChefPages` and a `message` builder.

**Why it matters (the concrete failure):** Chef A searches for "tomato, basil"; before A
clicks a result, Chef B searches "flour". They share `ingredientSpawnList`, so B's search
mutates A's basket. Worse, in the image flow: A generates an image (sets `pImg`), B
generates an image (overwrites `pImg`), A clicks *Save* → A saves **B's** image under A's
name. This is not theoretical; it's the direct consequence of the field placement. It also
makes the code impossible to reason about under load and impossible to unit-test in
isolation (state leaks between tests too).

**Direction (in rough order of preference):**
- Move this state to the **HTTP session** (`@SessionAttributes`, or an explicit
  `@Scope("session")` bean for the basket) — smallest conceptual change, matches "this is
  per-user workflow state."
- Or make it **stateless**: pass the basket/selection as request parameters or a form, and
  return results directly to the model rather than stashing them on the bean.
- For the image pipeline specifically, `generateImage` should **return** everything the
  save step needs (or hold the pending image in the session), so no `@Service` field is
  reused across requests.

This one finding is the strongest argument for the whole "built as single-user" framing,
and it deserves to be tackled early because so much else (testability, the image race
below, statelessness for future scaling) sits downstream of it.

> **Update (2026-09-08) — ✅ DONE, in five small steps (A1.1–A1.5). Details in §13.**
> - **`HomeController` / `ChefController`** (the latter named above; `HomeController` had the
>   same `totalFoundVisualPages` field, missed in the original write-up) — the page-count
>   fields were within-request values leaked out of a paging helper as a side effect. The
>   helper now returns the `PagedListHolder`; no fields.
> - **`ImageController.recipe`** — deleted. `saveVisual` reads the recipe id from its own
>   URL (`/spawn/spawn/{id}/save`), which the form already supplied.
> - **`ImageServiceImpl`** — now **stateless**. `theSketch` → local variable. `pImg` →
>   `generateImage` returns a `GeneratedImage(previewBase64, pngBytes)` record; the
>   controller holds the bytes in the **HTTP session** until save, then passes them to
>   `saveVisual(recipe, newId, pngBytes)`.
> - **`SpawnController`** — the 12 workflow fields moved into a new `@SessionScope`
>   `SpawnBasket` bean (Spring injects a per-session proxy); the controller is now
>   stateless. The four `StringBuilder` messages became plain `String`s along the way.
> - **Regression tests added:** `ImageControllerSessionIsolationTest` (save persists *this*
>   session's image, not another's) and `SpawnControllerSessionIsolationTest` (two sessions,
>   two baskets). Suite: 22 green.
> - **Not** addressed here (still their own findings): the Processing on-screen window / fixed
>   sleep (**A3**), the id/filename sequence race (**A2**), the 1/2/3-ingredient branching
>   (**D1/D2**), null-into-basket (**A6**).

### A2. 🔴 The visual's filename/id is derived from a sequence read — race + fragility — Effort **M**

`ImageController.saveVisual` (`ImageController.java:62-73`) asks
`visualService.findNextIdValue()` (`VisualServiceImpl.java:24-26`, which runs
`getNextValSequence()` against the DB), then `ImageServiceImpl.saveVisual`
(`ImageServiceImpl.java:143-179`) both **names the PNG** `visual{newId}.png` *and* assumes
the newly-persisted `Visual` will have exactly that id (`findVisualById(newId)` at the end).

**Why it matters:** the id is read from the sequence **separately** from the insert that
actually assigns the id (via `@GeneratedValue(AUTO)`). Under concurrency these can diverge,
so the file can be named for one id while the row gets another — the follow-up
`findVisualById(newId)` then fetches the wrong row or throws. Even single-user, it couples
a **filename on disk** to a **DB sequence internal**, which is brittle.

**Direction:** persist the `Visual` first, let JPA assign the id, then name the file from
`visual.getId()`. One source of truth, no second sequence read, no race.

> **Update (2026-09-09) — ✅ DONE.** `ImageServiceImpl.saveVisual` reordered to
> **persist-then-name**: build the `Visual` (chef + recipe), `visualService.saveVisual(visual)`
> so JPA assigns the id, *then* `fileName = "visual" + visual.getId() + ".png"` and write the
> bytes, then set `fileName`/`fileLocation` on the still-managed entity. Method is now
> `@Transactional`, so a failed file write rolls the insert back — no orphan row. The
> `newId` parameter, the `findVisualById(newId)` re-fetch, and the whole
> `findNextIdValue()` / `getNextValSequence()` chain (native `SELECT next_val FROM
> dishspawn_db.hibernate_sequence`, hard-coded schema name) are **deleted** —
> `VisualService`, `VisualServiceImpl`, `VisualRepository` all lose the method.
> `ImageController` redirects with `visual.getId()` and no longer injects `VisualService`
> at all (3 service deps → 2). `ImageControllerSessionIsolationTest` updated (mocks the
> returned `Visual` for its `getId()`); suite 22 green. Commit `ddb6050`.

### A3. 🔴 The image pipeline opens a real desktop window and blocks the request thread — Effort **L**

`ImageServiceImpl.getTheSketch()` sets `java.awt.headless=false`
(`ImageServiceImpl.java:197`) and launches a Processing `PApplet` — an actual on-screen
window — then the request thread **sleeps 7.777 seconds** (`:127`) waiting for it to draw,
then screenshots it. Step 1 flagged this; from the *improvement* angle the key points are:

- **It cannot run on a headless server** (no display) without a virtual framebuffer, and
  opening GUI windows from a web request is fundamentally the wrong execution model.
- **It serialises throughput**: each spawn holds a request thread for ~8s; a handful of
  concurrent spawns exhaust the thread pool.
- Combined with **A1**, concurrent spawns also fight over the shared `pImg`/`theSketch`.

**Direction (this is really a Step 4/5 topic, noted here for completeness):** render
**off-screen** (Processing/`PGraphics` in headless mode, or the future Clojure renderer
producing a `BufferedImage` directly), make generation **asynchronous** (a job + polling or
websocket, rather than a blocked thread), and drop the fixed sleep in favour of "render
completes → return." This is the natural seam where Step 5's Clojure rewrite can enter.

### A4. 🟠 `String ==` comparison — a latent unit-conversion bug — Effort **S**

`RecipeIngredient.massOrVolumeSetter()` compares strings with `==`:
`if (this.unitName == "PIECE")` (`RecipeIngredient.java:94`) and again at `:97`. `==`
compares **references**, not contents; it only appears to work when the string happens to
be interned. For a value read back from the database this is unreliable, so the "PIECE"
and null branches can silently fall through to the `switch`, which throws
`UnsupportedOperationException` for an unhandled unit. Use `.equals()` / `"PIECE".equals(x)`
(null-safe order), or better, make `unitName` an enum.

> **Update (2026-09-08) — ✅ DONE (minimal fix).** `RecipeIngredient.java:94` now reads
> `if ("PIECE".equals(this.unitName))`; the `== null` check on the next line is correct as
> written and was left alone. The `unitName`-as-enum idea is *not* done — deferred, and it
> now also connects to **OP-2** (the converters' `from → to` contract). Note discovered
> while fixing: `massOrVolumeSetter()` is currently **unreachable** — nothing calls it, and
> `mass`/`volume` carry `@Setter(AccessLevel.NONE)` — so this was a *latent* bug; the fix
> makes the method correct for whenever it gets wired in.

### A5. 🟠 Entities used in `Set`/`contains`/`distinct` but define no `equals`/`hashCode` — Effort **M**

No entity in `model/` overrides `equals`/`hashCode` (grep confirms none; the classes even
carry `// equals / hash` "todo" comments — `Recipe.java:125`, `RecipeIngredient.java:171`,
`Chef.java:102`). Yet:

- `Recipe.recipeIngredients` is a `Set<RecipeIngredient>` (`Recipe.java:51`).
- The recipe-intersection search relies on `List.contains`/`distinct` over `Recipe`
  objects (`SpawnController.java:213-219`).

Today this *happens* to work because Hibernate's first-level cache returns the **same
instance** for a given id within one session, and Spring's Open-Session-In-View keeps that
session open for the whole request — so reference-equality accidentally behaves like
identity-equality. That's a **fragile coincidence**: change the transaction boundaries
(A1's fixes, adding `@Transactional`, turning OSIV off) and the intersection can silently
start returning wrong/empty results. Define `equals`/`hashCode` on a **stable business key**
(or id-with-care) so correctness doesn't depend on session scope.

### A6. 🟡 Sentinel-value error handling hides failures — Effort **S**

`Parser.convertStringIdToLong` returns `0L` on bad input (`Parser.java:6-17`) and callers
test `idLong == 0l` to branch to an error page (`SpawnController.java:103`,
`ImageController.java:42`). `checkIngredientIdExists` returns `null` on
`NoSuchElementException` (`SpawnController.java:259-270`), and the caller immediately does
`ingredientSpawnList.add(ingredientDB)` (`:110`) — so a not-found id adds a **null** to the
basket, which later NPEs elsewhere. Prefer exceptions (you already have
`ResourceNotFoundException`) handled centrally, or `Optional`, over magic return values.

---

## 4. Theme B — Security

### B1. 🔴 Database credentials committed in plaintext — Effort **S**

`application-local.properties` (tracked) contains
`spring.datasource.username=hbstudent` / `spring.datasource.password=hbstudent` and
`useSSL=false`. These are the well-known defaults from the Java/Spring course this project
grew out of, so the *immediate* risk is low — but committing any credential trains a bad
habit and leaks the moment the repo goes public or the pattern is copied to real creds.
**Direction:** externalise to environment variables (as `application-prod.properties`
already does), and consider `git rm --cached` + history scrubbing if the repo will ever be
shared.

> **Update (2026-09-08) — ✅ DONE (option 1 of 4).** The plaintext values in
> `application-local.properties` are replaced with
> `spring.datasource.username=${DB_USERNAME:hbstudent}` /
> `password=${DB_PASSWORD:hbstudent}` — env-var placeholders keeping the well-known course
> default so zero-setup local dev still works. The three heavier options (drop the default,
> `git rm --cached` + `.gitignore`, git-history scrub) were **consciously deferred** and are
> written up in `docs/open-points.md` **§OP-1** with their trade-offs. Verified the Maven
> resource-filtering pass leaves the `${…}` tokens intact for Spring to resolve at runtime.

### B2. 🟠 Authorisation is URL-pattern-only, and the patterns are easy to get subtly wrong — Effort **M**

All authz lives in one `SecurityFilterChain` as `mvcMatchers` (`SecurityConfiguration.java`).
Two concrete concerns:

- **A likely typo weakening a rule:** the permit-all list contains `"recipe**"`
  (`SecurityConfiguration.java:21`) with **no leading slash**, whereas every sibling uses
  `/...`. As written this almost certainly does not match `/recipe/**` the way intended,
  which interacts with the `/recipe/add` rule above it. This needs verifying against actual
  request behaviour — it's exactly the kind of silent gap URL-based security produces.
- **No defence in depth:** there are **no method-level** `@PreAuthorize` checks anywhere,
  so a future controller added without a matching URL rule inherits whatever the catch-all
  gives it. Ownership checks are also absent — e.g. can a chef save/love/act on another
  chef's resource? Worth auditing per action.

**Direction:** keep the filter chain, but add method-level authorisation on the sensitive
service/controller methods, and add explicit **owner checks** where a resource belongs to a
chef. Write a few `spring-security-test` slices (`@WithMockUser`) to *prove* each role
boundary — this doubles as Step-G testing.

### B3. 🟠 `ddl-auto=update` against the real database — Effort **S/M**

Both `application-local.properties` and `application-prod.properties` set
`spring.jpa.hibernate.ddl-auto=update`. Letting Hibernate mutate the schema from entity
diffs is convenient in dev but risky in prod (it never drops/renames safely, can lock
tables, and makes schema history invisible). **Direction:** move to a managed migration
tool (**Flyway** or **Liquibase**) with versioned SQL, and set `ddl-auto=validate` (or
`none`) in prod. This also becomes the clean delivery mechanism for **Step 3's** bulk
recipe/ingredient seed data.

### B4. 🟡 Verbose diagnostics leak into stdout / could leak to users — Effort **S**

39 `System.out.println` / `printStackTrace` calls across 15 files (e.g. the entire image
pipeline narrates itself to stdout; `Parser` prints stack traces on bad input). Stack
traces to stdout can expose internals and are the wrong tool for a server. Rolls up with
**E1**.

---

## 5. Theme C — Persistence & efficiency

### C1. 🔴 "Find recipes containing all N ingredients" is done in Java, not SQL — Effort **M/L**

Step 1 described this; here is the efficiency verdict. For 2–3 ingredients,
`SpawnController` calls `createRecipeList(ingredient)` (`:232-242`), which calls
`findAllRecipeIngredientByIngredient` — **loading every `RecipeIngredient` for that
ingredient**, mapping each to its `Recipe`, then intersecting the lists **in memory**
(`:213-219`). With a large catalogue (the explicit goal of **Step 3**), a popular
ingredient like "salt" pulls thousands of rows into the app just to intersect them.

**Direction:** express the intersection as **one SQL/JPQL query** — e.g.
`SELECT ri.recipe FROM RecipeIngredient ri WHERE ri.ingredient.id IN (:ids) GROUP BY
ri.recipe HAVING COUNT(DISTINCT ri.ingredient.id) = :n` — paged at the database. This
collapses the 1/2/3 branch duplication into a single code path *and* makes Step 3's scaling
target achievable. This is the single most important efficiency change and it directly
enables Step 3.

### C2. 🟠 Almost everything is `FetchType.EAGER` — Effort **M**

11 EAGER associations, several on the hot entities: `Recipe` eagerly loads
`recipeIngredients`, `visuals`, **and** `chef` (`Recipe.java:42-67`); `RecipeIngredient`
eagerly loads both `ingredient` and `recipe` (`:33-40`); `Chef` eagerly loads all `roles`
(`:59`). Loading one `Recipe` therefore drags in its ingredients, each ingredient's data,
all its visuals, and the chef — often as multiple queries or a cartesian-product join.
Combined with **C1** (which already over-fetches), a single search can be surprisingly
heavy. **Direction:** default to `LAZY` and fetch **explicitly** where needed
(`JOIN FETCH`, entity graphs, or projection DTOs for read-only views). Pairs naturally with
adding `@Transactional` boundaries (**C3**).

### C3. 🟠 No `@Transactional` anywhere — Effort **M**

Grep finds **zero** `@Transactional` annotations in `src/main` (there are commented-out
ones in `RecipeIngredientServiceImpl`). Service methods that do multiple repository
operations therefore run without an explicit transaction, and the app leans entirely on
Open-Session-In-View to keep lazy loading working — which is also what props up **A5**'s
accidental correctness. **Direction:** add `@Transactional` on service methods
(read-only where applicable), which gives real atomicity, lets you turn OSIV off, and makes
the fetch strategy (**C2**) something you control rather than inherit.

### C4. 🟡 Deprecated Hibernate dialect + `useSSL=false` — Effort **S**

`spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect` targets
MySQL 5 and is deprecated in the Hibernate version Boot 2.7 ships; with a modern MySQL 8
(the driver is `mysql-connector-j` 8.2) you generally shouldn't set the dialect by hand at
all (let Hibernate detect it) or use the MySQL 8 dialect. `useSSL=false` disables transport
encryption. Both are small config fixes.

---

## 6. Theme D — Architecture & design

### D1. 🟠 Controllers hold business logic that belongs in services — Effort **M**

`SpawnController.findRecipes` (`:115-189`) contains the entire search algorithm — the
1/2/3-ingredient branching, list building, intersection, and paging — with the code's own
`// todo move to service layer` (`:132`) admitting it. Controllers should translate HTTP ↔
domain and delegate; the recipe-finding logic should live in a service (where it can be
unit-tested and where **C1**'s single-query rewrite naturally lands). Same pattern in the
image save flow (`ImageController` `// TODO: move logic to ImageService`, `:61`).

### D2. 🟠 The three-way ingredient branch is copy-paste that won't generalise — Effort **M**

`findRecipes` has near-identical blocks for 1, 2, and 3 ingredients (`:133-186`). Beyond
duplication, it **hard-caps the feature at 3 ingredients** by construction. Once the search
is a single query (**C1**), N ingredients is just `IN (:ids)` + `HAVING COUNT = :n` — the
branch collapses to one path and the arbitrary cap becomes a tunable parameter.

### D3. 🟡 The `service` interface / `service.implementation` split adds ceremony with one impl each — Effort **S**

Every service is an interface plus a single `*Impl`. This is a common enterprise habit, but
with exactly one implementation it mostly adds indirection (and files) without buying
substitutability. Not wrong — just worth a conscious decision: keep it as a deliberate
convention, or collapse to concrete `@Service` classes and reintroduce interfaces only
where a second implementation actually appears. (Mentioned as a readability/altitude call,
not a defect.)

### D4. 🟠 Error handling is half-wired — Effort **S/M**

- `GlobalDefaultExceptionHandler` (`exception/`) is a **completely empty class** — dead
  code that implies an intention never finished.
- `ExceptionControllerAdvice` has a **malformed annotation**:
  `@RequestMapping("${server.error.path")` (`ExceptionControllerAdvice.java:11`) — note the
  missing closing `}` on the property placeholder — and `@RequestMapping` on a
  `@ControllerAdvice` is meaningless anyway.
- It handles only `ResourceNotFoundException` and `UsernameAlreadyExistsException`; the
  other custom exceptions (`SaveImageNotPossible`, `UnitDoesNotExistException`) and runtime
  ones (the `UnsupportedOperationException` from **A4**) fall through.

**Direction:** delete the empty class, fix/remove the annotation, and make the advice cover
the full set of app exceptions with appropriate views/status codes.

---

## 7. Theme E — Readability & hygiene

### E1. 🟡 Replace `System.out`/`printStackTrace` with a logger — Effort **S**
39 occurrences across 15 files (§B4). Swap for SLF4J (`log.debug/info/warn/error`), which
Boot already provides, so verbosity is controllable per environment and errors are captured
properly.

### E2. 🟡 Scratch and legacy code ships inside `src/main` — Effort **S**
`play/` (`PlaySketch`, `TestJDBC`, `BallOld`, `PlayHexa`, …) and
`graphics/vanillajava/` (`Ball`, `ImageCanvas`, `ImageWindow`) — **11 tracked files** — are
experiments and a superseded non-Processing rendering attempt, compiled and shipped as part
of the app. They blur "the app" vs "the sketchbook." **Direction:** move to a separate
module/branch, a `sandbox/` outside `src/main`, or delete (git history keeps them). Clears
the ground before Step 5 introduces the Clojure renderer.

### E3. 🟡 Pervasive `// todo` / dead commented code — Effort **S (ongoing)**
The hot files are dotted with `todo`, commented-out alternatives (e.g. `Recipe.toString`'s
commented loops, `Chef`'s commented `role` field), and "not yet in use" constructors. Each
is small; together they add friction to reading. Worth a hygiene pass — and each `todo` is
either a real backlog item (capture it) or noise (delete it).

### E4. 🟡 Magic numbers / hardcoded ids — Effort **S**
The default role is fetched by a **hardcoded id** `roleRepository.findById(276l)`
(`ChefServiceImpl.java:76`) — brittle if the row isn't exactly 276, and `role.get()` will
throw if absent. Prefer look-up **by role name**. Similar magic constants: page sizes of 3
sprinkled around, the `120–180` shape budget, the `7777`ms sleep.

### E5. 🟡 Stray files in the tree — Effort **S**
`.DS_Store` files exist under `src/main/resources` (not tracked — good, but present) and an
untracked `.output.txt` sits in the repo root. Minor cleanup; make sure `.DS_Store` is
globally ignored.

---

## 8. Theme F — Libraries & build

### F1. 🟠 Spring Boot 2.7.5 is end-of-life — Effort **L**
Boot 2.7.x reached end of OSS support (Nov 2023); 2.7.5 specifically is several patch
releases behind even within 2.7, so it's missing accumulated security fixes. The modern
line is Boot 3.x (Spring Framework 6, Jakarta EE — `javax.*` → `jakarta.*`). **Direction:**
plan a deliberate upgrade to a supported 3.x. It's an **L** because of the `javax`→`jakarta`
namespace migration across the entities and security config — worth doing, but as its own
scoped effort, not a drive-by.

### F2. 🟠 Java 19 is a non-LTS, already-EOL release — Effort **S/M**
The `pom.xml` change set moves to Java 19, which is past end-of-life (non-LTS releases get
~6 months). **Direction:** target an **LTS** — Java 17 (pairs with Boot 2.7/3.x) or Java 21.
Small change, meaningful for security patches and tooling support.

### F3. 🟠 The Processing dependency is a personal fork on JitPack — Effort **M**
`com.github.micycle1:processing-core-4:4.0.1` via JitPack (`pom.xml:24-29,78-82`) is a
**third-party individual's** repackaging of Processing, built on demand by JitPack. That's
a supply-chain and longevity risk (no guarantees it stays available or maintained). This is
also precisely the dependency **Step 5** aims to move away from by reimplementing the
renderer in Clojure — so the mitigation and the roadmap align. Until then, at least pin and
document why this coordinate is used.

---

## 9. Theme G — Testing

### G1. 🔴 There is effectively no test suite — Effort **M (ongoing)**
The entire `src/test` tree is one file, `DishSpawnApplicationTests.java`, containing only
the default `contextLoads()` smoke test. There are **no** unit tests for the search
intersection, the unit converters, the image parameter math, or the security rules; and no
integration tests for controllers. Every refactor proposed above is therefore being made
**without a safety net**.

**Why this is both a symptom and a blocker:** the current design actively *resists* testing
— singleton request-state (**A1**), logic trapped in controllers (**D1**), and a rendering
step that opens a GUI window and sleeps (**A3**) are all hard to test precisely *because*
of how they're built. So testing and the design fixes reinforce each other.

**Direction — a pragmatic order that also de-risks the refactors:**
1. **Pure logic first (easy wins, no Spring):** unit-test `MassConverter`/`VolumeConverter`,
   `Transformer` (form → shape), and the shape-count math. These are already close to pure
   functions.
2. **The search algorithm:** as it moves into a service (**D1/C1**), test the
   intersection/`HAVING COUNT` behaviour with an in-memory or Testcontainers MySQL.
3. **Security slices:** `@WebMvcTest` + `spring-security-test` (`@WithMockUser`) to *prove*
   each role boundary from **B2** — these tests are the verification for that finding.
4. **A regression test per bug fixed:** e.g. a test that two "sessions" don't share a basket
   (**A1**), locking in each correctness fix as you make it.

The dev dependencies you need are already on the classpath (`spring-boot-starter-test`,
`spring-security-test`).

> **Update (2026-09-08) — 🔧 IN PROGRESS (slice 1 of 4 done).** Step 1 of the plan above is
> done: `MassConverterTest` (11 cases) and `VolumeConverterTest` (8 cases) added under
> `src/test/java/io/eho/dishspawn/model/util/unitconversion/` — plain JUnit 5, no Spring
> context. `./mvnw -Plocal test` → **20 tests, all green** (the 19 new + the original
> `contextLoads()`). Two latent converter issues surfaced while writing them and are logged
> in `docs/open-points.md` **§OP-2** (`convert` ignores `unitTo` unless `unitFrom` is the
> base unit) and **§OP-3** (`VolumeConverter` has no reachable exception path — why its test
> has no throw cases). Slices 2–4 (search algorithm, security slices, regression-per-bug)
> are not started.

---

## 10. Prioritised roadmap (the one table to keep)

Ranked by *value ÷ risk-of-leaving-it*. "Enables" shows how a fix unblocks later roadmap
steps.

**Status legend:** ✅ done · 🔧 in progress · ⏸️ deferred by decision · ⬜ not started.
Status last reviewed **2026-09-08**.

| # | Finding | Theme | Sev | Effort | Status | Enables |
|---|---|---|---|---|---|---|
| 1 | Request state on singleton beans → session/stateless | A1 | 🔴 | M | ✅ 2026-09-08 (A1.1–A1.5; also fixed `HomeController`) | testability; Step 4/5; scaling |
| 2 | Search intersection in Java → single SQL query | C1 | 🔴 | M/L | ⬜ | **Step 3** scaling |
| 3 | Visual id/filename race → persist-then-name | A2 | 🔴 | M | ✅ 2026-09-09 (`ddb6050`; also dropped the native sequence read + `VisualService` dep from `ImageController`) | correct saves |
| 4 | No tests → start the pyramid (pure logic first) | G1 | 🔴 | M | 🔧 slices 1 + 4 underway (converters; 2 session-isolation regression tests) — suite at 22 | safe refactoring of all others |
| 5 | Committed DB credentials → externalise | B1 | 🔴 | S | ✅ 2026-09-08 (→ OP-1) | security hygiene |
| 6 | `String ==` unit bug → `.equals`/enum | A4 | 🟠 | S | ✅ 2026-09-08 (`.equals`; enum deferred) | correct mass/volume |
| 7 | `equals`/`hashCode` on entities | A5 | 🟠 | M | ⬜ | robust search under tx changes |
| 8 | Logic in controllers → services | D1/D2 | 🟠 | M | ⬜ | prep for #2; N-ingredient search |
| 9 | EAGER → LAZY + `@Transactional` | C2/C3 | 🟠 | M | ⬜ | performance; controlled fetching |
| 10 | Role-based authz audit + method security + owner checks | B2 | 🟠 | M | ⬜ | security |
| 11 | `ddl-auto=update` → Flyway/Liquibase + `validate` | B3 | 🟠 | S/M | ⬜ | **Step 3** seed delivery |
| 12 | Error handling half-wired → fix/complete advice | D4 | 🟠 | S/M | ⬜ | robustness |
| 13 | Boot 2.7 EOL → 3.x; Java 19 → LTS 17/21 | F1/F2 | 🟠 | L/S | ⏸️ deferred — "last-last thing" (see §12 Q2) | supportability, security |
| 14 | Processing fork dependency risk | F3 | 🟠 | M | ⬜ | aligns with **Step 5** |
| 15 | Headless/async image rendering | A3 | 🔴* | L | ⬜ (headless part = deploy prereq, see §12 Q1) | **Step 4/5** |
| 16 | Logging, dead code, scratch packages, magic numbers | E1–E5 | 🟡 | S | ⬜ | readability |
| 17 | Dialect / `useSSL` config | C4 | 🟡 | S | ⬜ | polish |
| 18 | Service interface/impl split — conscious call | D3 | 🟡 | S | ⬜ | simplicity |

\* A3 is high-*impact* but large and best sequenced with Steps 4–5, so it sits lower in
*order* despite its severity.

**Step-2 execution so far:**
- Session 1 (2026-09-08) — the "prove the loop" batch: #5 (credentials), #6 (`String ==`),
  first slice of #4 (converter tests). Suite 20 green.
- Session 2 (2026-09-08) — **#1** (singleton request-state), five steps A1.1–A1.5 with two
  session-isolation regression tests. Suite 22 green.
- Session 3 (2026-09-09) — **#3** (Visual id/filename race), persist-then-name + drop the
  native sequence read. Suite 22 green. Also folded the §2 corrections into doc 01 (§12 Q4).

**Next:** **#2** (SQL intersection) + **#11** (Flyway) — the two that unblock Step 3 —
tackled with the same staged treatment #1 got. **#8** (move `findRecipes` into a service,
collapse the 1/2/3 branch) is the natural first move of that.

---

## 11. How this feeds the rest of the roadmap

- **Step 3 (grow the database)** depends on **#2** (SQL intersection) and **#11** (Flyway as
  the seed-data delivery mechanism). Doing those two first turns "add thousands of recipes"
  from a performance cliff into a config-and-data task.
- **Step 4 (better images)** depends on **#15** (headless/async rendering) and benefits from
  **#1** (no shared `pImg`). The half-wired complementary-background and texture work in the
  uncommitted tree (§1) is the first content of Step 4.
- **Step 5 (Clojure renderer)** slots in at the `ImageService` → renderer seam once **#1**
  and **#15** make that boundary clean and stateless; it's also the exit strategy for the
  Processing-fork risk (**#14**).

So the correctness/design work here isn't a detour from your goals — it's the groundwork
that makes Steps 3–5 tractable.

---

## 12. Open questions for you (to steer the next sessions)

1. **Deployment reality:** is DishSpawn ever meant to run on a real server with multiple
   users, or is it explicitly a single-user local/portfolio app?
   → **ANSWERED (2026-08-29): multi-user web app, ~10–20 users to start, possibly upscaled
   later.** Implications, now locked in:
   - The concurrency findings are **genuine must-fixes** — the trigger is **2 concurrent
     users, not 20** (e.g. one chef saving another chef's image via the shared `pImg`).
     **A1** and **A2** stay at the top of the order.
   - **A3 splits in two:** "render headless / off-screen" (drop `java.awt.headless=false`,
     no on-screen window) is a **deployment prerequisite** — the app can't run on a normal
     server without it; "make rendering asynchronous" is **deferrable** — a handful of ~8s
     spawns won't exhaust the thread pool at this scale.
   - **B2 (authorization + resource-ownership audit)** rises in importance: "can chef A act
     on chef B's recipe/visual?" is now a real question.
   - **No scaling infrastructure needed** at 10–20 users — no Redis/session-store, load
     balancer, or read replicas. Plain HTTP sessions on a single instance suffice, and the
     A1 fix (state → session) is exactly what keeps a future upscale cheap.
2. **Boot/Java upgrade appetite:** are you open to the Boot 3 + Jakarta migration (#13) as a
   dedicated session, or should we stay on 2.7 for now and revisit later?
   → **ANSWERED (2026-09-08): stay on Boot 2.7 for now.** The Boot 3 + Jakarta migration is
   explicitly the **last** thing we do — after Steps 3–5. #13 is marked ⏸️ *deferred* in the
   §10 table; don't let it block anything else.
3. **Where do you want to start executing?** The "prove the loop" batch in §10, or straight
   at the highest-value item (#1 or #2)?
   → **ANSWERED (2026-09-08): the "prove the loop" batch first, then Step 3.** The batch
   (#5, #6, first slice of #4) is ✅ done, and **#1** (singleton request-state) is now ✅
   done too — see §13. After folding the §2 corrections into doc 01 (Q4), the next targets
   are **#2** + **#11** (unblock Step 3) or **#3** (quick correctness win).
4. **Scope of this doc:** shall I fold the four Step-1 corrections (§2) back into
   `01-functional-overview.md` so both documents stay consistent?
   → **DONE (2026-09-08).** All four applied to doc 01 (§2, §7, §8). Correction 3 was
   updated in passing to note the credentials are now externalised (→ OP-1), not just
   "committed in plaintext".

---

## 13. Execution log

Newest first. Each entry: what changed, where, and how it was verified.

### 2026-09-09 — #3 Visual id/filename race (finding A2)

Commit `ddb6050`. Assistant provided hunks, user applied.

- **`ImageServiceImpl.saveVisual(Recipe, byte[])`** (was `(Recipe, Long newId, byte[])`) —
  reordered to **persist-then-name**: create `Visual` with chef + recipe →
  `visualService.saveVisual(visual)` (JPA assigns the id) → `fileName = "visual" +
  visual.getId() + ".png"`, write bytes → set `fileName`/`fileLocation` on the managed
  entity → return it. Method is now `@Transactional` (rollback on a failed file write; no
  orphan row). Dropped the `findVisualById(newId)` re-fetch.
- **Deleted the guessed-id path:** `VisualService.findNextIdValue()`,
  `VisualServiceImpl.findNextIdValue()`, `VisualRepository.getNextValSequence()` (+ its
  native `SELECT next_val FROM dishspawn_db.hibernate_sequence` with the hard-coded schema
  name) and the now-unused `@Query` import.
- **`ImageController.saveVisual`** — no more `findNextIdValue()`; redirects with
  `visual.getId()`. `VisualService` was its only user of that dep, so the field, ctor
  param and import are removed (3 injected services → 2). Dropped two no-op
  `model.addAttribute` calls before the redirect.
- **`ImageControllerSessionIsolationTest`** — drop the `VisualService` mock + the
  `findNextIdValue` stub; `saveVisual` verify is now 2-arg; mock the returned `Visual` for
  `getId()` so `redirectedUrl("/visual?visualId=7")` is asserted against the persisted
  entity's id.
- **Verification:** `./mvnw -Plocal test` → `Tests run: 22`, `BUILD SUCCESS`.
- **Left for their own findings:** A3 (on-screen window / fixed sleep), and a DB-backed
  test that `visual.getFileName()` matches `visual{getId()}.png` after a real save — wants
  Testcontainers / embedded MySQL, deferred to G1 slice 2.

### 2026-09-08 — doc sync: §2 corrections folded into doc 01 (§12 Q4)

The four accuracy corrections in §2 applied to `01-functional-overview.md`: §2 profiles
paragraph (runtime profile is always `local`; both env-property files tracked), §7
"Generated output" (spawn PNGs are git-ignored, not versioned), §8 Security Model (new
bullet on datasource credentials, noting the OP-1 externalisation). Doc-only; no build.

### 2026-09-08 — #1 request state off the singleton beans (Step-2 execution session 2)

Finding **A1**. Done in five independently-testable steps; working style: assistant
proposed diffs, user applied.

- **A1.1 — `HomeController` + `ChefController` page-count fields.** `totalFoundVisualPages`
  / `totalFoundVisualsChefPages` (and `ChefController`'s `message` builder) were
  within-request values leaked out of a paging helper via a side effect. Helper now
  returns the `PagedListHolder`; handler reads `getPageCount()` / `getPageList()`. No
  behaviour change. (`HomeController` was not in the original A1 write-up — same bug.)
- **A1.2 — `ImageController.recipe` deleted.** `saveVisual` now takes `@PathVariable id`
  (the save form already posts to `/spawn/spawn/{id}/save`) and re-loads the recipe.
- **A1.3 — `ImageServiceImpl.theSketch` → local variable.** Only ever used within
  `generateImage`.
- **A1.4 — `ImageServiceImpl.pImg` off the service.** `generateImage` returns a
  `GeneratedImage(previewBase64, pngBytes)` record; `ImageController` stashes `pngBytes`
  in the `HttpSession` under `PENDING_IMAGE` and passes them back into
  `saveVisual(recipe, newId, pngBytes)`, then clears the attribute. Disk write switched
  from `PImage.save(...)` to `Files.write(...)`. Service is now stateless.
  New test: `ImageControllerSessionIsolationTest` — two sessions with different pending
  images; save in session A persists A's bytes.
- **A1.5 — `SpawnController` → `@SessionScope SpawnBasket`.** New
  `controller/SpawnBasket.java` holds the 12 workflow fields (the 4 `StringBuilder`
  messages became `String`) plus the `reset*` helpers; Spring injects a per-session
  proxy. Controller is now stateless.
  New test: `SpawnControllerSessionIsolationTest` — two `MockHttpSession`s, two baskets.
  (`@SpringBootTest` + `@AutoConfigureMockMvc` **with** the security filter chain on —
  `spawn-i` renders `fragments/header`, which uses `#authorization` / `sec:authorize`.)
- **Verification:** `./mvnw -Plocal test` → `Tests run: 22, Failures: 0, Errors: 0`,
  `BUILD SUCCESS`.
- **Left for their own findings:** A3 (on-screen window / fixed sleep), A2 (id/filename
  sequence race), D1/D2 (1/2/3-ingredient branching), A6 (null into basket).

### 2026-09-08 — "prove the loop" batch (Step-2 execution session 1)

Branch: `UNIT_BUG_AND_TESTS_START` (tests + #6) and a separate branch for #5.
Working style: assistant proposed diffs, user applied and committed them.

- **#6 — `String ==` bug (finding A4) — ✅ done.**
  `RecipeIngredient.java:94`: `this.unitName == "PIECE"` → `"PIECE".equals(this.unitName)`.
  One line; `== null` on the next line left as-is (correct). Noted that
  `massOrVolumeSetter()` is currently dead code, so this was latent.
- **#4 — first test slice (finding G1) — 🔧 in progress.**
  New: `src/test/java/io/eho/dishspawn/model/util/unitconversion/MassConverterTest.java`
  (11 cases) and `VolumeConverterTest.java` (8 cases). Plain JUnit 5, no Spring.
  Cover: identity conversions, each factor both directions, round-trips within `1e-6`,
  `parseStringToUnit` happy + bad input, and (mass only) the two exception paths.
- **#5 — DB credentials (finding B1) — ✅ done (lightest option).**
  `application-local.properties`: hard-coded `hbstudent` / `hbstudent` →
  `${DB_USERNAME:hbstudent}` / `${DB_PASSWORD:hbstudent}`. Deferred options captured in
  `docs/open-points.md` §OP-1.
- **New companion doc:** `docs/open-points.md` created — register for deliberately-partial
  fixes and latent issues. Entries: OP-1 (credentials), OP-2 (`convert` ignores `unitTo`
  off the base unit), OP-3 (`VolumeConverter` has no reachable exception path).
- **Verification:** `./mvnw -Plocal test` → `Tests run: 20, Failures: 0, Errors: 0`.
  Full build `BUILD SUCCESS`. Maven resource filtering confirmed to leave the `${…}`
  placeholders intact for Spring.

### 2026-08-29 — housekeeping (see §1)

Committed the in-progress generative-art work on `testLint`, fast-forward-merged to `main`
(`2a700bf` → `bebb240`), pushed. Removed a dead GitHub token from the `origin` remote URL.

---

*End of Step 2 document. It is no longer "nothing has been changed" — Step-2 execution is
underway; §10 and §13 track exactly where.*
