# noroshi (烽) — photonics-electronics convergence (光電融合) comms chip + ISAC + packaging robotics

**DID**: `did:web:etzhayyim.com:actor:noroshi` · **Tier**: B · **Status**: R0 · **ADR**: 2606051600

## What this is

The **光電融合 (photonics-electronics convergence) communication-chip** actor — the silicon-photonic /
co-packaged-optics (CPO) sibling of the **electronic** silicon/iwakura/fuigo line and the RF **tsutae**
comms device. 烽 (狼煙, beacon-fire) is the original optical telecom: a watchtower **senses** a distant
fire and **relays** a coded message — one emission, two functions — which is exactly **ISAC**
(Integrated Sensing And Communication).

Three faces, each with a verifiable `methods/` core:

- **chip** — design + optical link budget of photonic-IC / CPO comms chips on open photonic-EDA.
  `src/noroshi/methods/link_budget.kotoba` (CPO = **3.96× lower energy/bit** than a front-panel pluggable on the
  reference designs).
- **isac** — one OFDM-JCAS waveform doing communication capacity **and** range-Doppler sensing.
  `src/noroshi/methods/isac_sim.kotoba` (OFDM-radar reciprocal processing + the comms↔sensing power-split tradeoff;
  **civilian objects only**).
- **packaging** — photonic assembly robotics (fibre↔grating active alignment, photonic wire-bond)
  under a laser-safety interlock. `src/noroshi/methods/active_alignment.kotoba` (Hooke-Jeeves search + IEC 60825 /
  civilian-use gate — the safety-critical coded core, like tazuna's `teleop_safety`).

## Cells (langgraph→WASM; Murakumo-only; `.solve()` raises at R0)

chip: **`device_design`** (naphtali — coded: civilian-gate G1/G3/N1 + open-EDA plan generation via
`src/noroshi/src/noroshi/methods/device-design.cljc`, calling `methods/pic-layout`) · `link_budget` (gad). isac:
`isac_waveform` (asher) · `sense_estimate` (benjamin). packaging: **`active_alignment`** (joseph —
coded, the laser-safety/no-server-key one) · **`reliability_qual`** (manasseh — coded: a real
Telcordia GR-468-SHAPE PASS/FAIL engine, `src/noroshi/src/noroshi/methods/reliability-qual.cljc`, representative thresholds
G10). Three of six cells are coded (`:cell/coded true`); `.solve()` itself stays an R0 stub on all
six regardless of coded status — coded means the phase-transition logic (and, for these two, the
underlying compliance-judgment engine) is real and tested, not that live activation is unlocked.

## Gates (immutable R0→R5)

**G1 cleanroom-epda** (open-source photonic + digital EDA ONLY — GDSFactory / Meep / KLayout /
Verilator / yosys / OpenLane + open PDK; NO Cadence/Synopsys/Lumerical/Ansys, no NDA foundry PDK,
no decompile/trademark/fork; iwakura open-EDA + sumitsubo G1 precedent) · **G2
displacement-dividend-coupling** (packaging robotics that displaces fibre-alignment technicians live
needs a funded cohort, ADR-2606032130) · **G3 civilian-force-separation** (optical power + ISAC
sensing civilian-only; weaponisation — directed-energy / laser-dazzle / fire-control radar — is
structurally unrepresentable, Mission Charter §1.12) · **G4 sensing-not-surveillance** (an ISAC
estimate is an OBJECT's range+velocity, never a person / biometric / pattern-of-life; watari G4) ·
**G5 laser-safety-soft** (IEC 60825 enclosure-interlock + class gate is best-effort soft-safety, NOT
certified; hard-RT/certified = R5/Lv7+, kotoba-os N2) · **G6 murakumo-only** (ADR-2605215000) · **G7
no-server-key** (tapeout / mask order / robot actuation member-signed; serverHeldKey=false,
ADR-2605231525) · **G8 outward-gated** (live tapeout / mask set / measurement / laser / actuation =
Council Lv6+ + operator; Class-3B/4 near humans Lv7+) · **G9 kotoba-EAVT audit** (canonical Datom log,
no RisingWave, ADR-2605312345) · **G10 sourcing-honesty** (`:representative`; sims are arithmetic/DSP,
no measured silicon) · **G11 sbom-provenance** (a fabricated die carries a CycloneDX SBOM into kotoba,
gated at tapeout; wasm-sbom / giemon precedent).

## Non-goals

N1 no weaponisation (`:weaponizable` unrepresentable) · N2 no person-surveillance · N3 not a certified
safety controller · N4 no fabricated coverage (honest aliasing) · N5 no proprietary EDA / NDA PDK · N6
no commercial GPU / cloud-EDA · N7 not a foundry / tapeout broker at R0 · N8 no cash for labour /
demonstrations.

## Build / test

```sh
kbb -M:test
kbb -M:audit
```

## R1 integrations (this session)

- **(c) `src/noroshi/methods/cable_endpoint.kotoba`** — joins noroshi CPO chips to the **watatsuna** submarine-cable medium:
  sizes the transceiver fleet per landing → per-chokepoint demand (luzon-strait → suez → malacca →
  gibraltar). Resilience framing inherited from watatsuna (G2, never a target-list).
- **(a) `src/noroshi/methods/kami_isac_bridge.kotoba` + `wire/wit/kami-isac.wit`** — ISAC sensor as a **kami-autodrive** plant
  (ADR-2606010600); scenario → per-object range/velocity tracks; civilian objects only (N1/N2).
- **(b) `src/noroshi/methods/pic_layout.kotoba`** — GDSFactory-shaped ModelOp layout plan → feeds waveguide length back into
  `src/noroshi/methods/link_budget.kotoba`; real GDS write remains G1/G8-gated.

Honest: the kami-engine submodule is unpopulated and gdsfactory isn't installed here, so (a)/(b) are
bridge + contract + gated backend (sumitsubo pattern); (c) is a full offline join.

The canonical runtime is Clojure/CLJC; deprecated Python and shell runners are forbidden by `kbb -M:audit`.
same as tazuna/karakuri). R0 = design + 3 method cores + `active_alignment` state-machine +
`:representative` device/waveform/fleet seed. **No silicon, no foundry, no live laser, no live
actuation** (all gated G8).

## Do not

- Do not introduce a `:weaponizable` force class, a directed-energy / dazzle / fire-control use, or a
  fire-control/targeting sensing mode — N1/G3 (unrepresentable in schema, lexicon, and
  `active_alignment.PERMITTED_USES`).
- Do not add a `:person` target class or any biometric / pattern-of-life field to `senseEstimate` —
  N2/G4 (an ISAC target is an object, not a person).
- Do not bundle a proprietary EDA tool, a vendor NDA foundry PDK, or decompiled/trademarked code — G1/N5.
- Do not let the platform sign a tapeout / mask order / robot actuation, or hold a robot's key — G7 /
  ADR-2605231525.
- Do not energise a hazardous-class (2/3R/3B/4) laser without an enclosure interlock + safety
  attestation — G5; and do not present this as a certified safety system — N3.
- Do not call any cell's `.solve()` — R0 scaffolds raise `RuntimeError` by design.
- Do not present a sim number as a measured device, or imply silicon exists — G10/N4.
- Do not route design/inference through a commercial GPU or cloud-EDA — G6/N6 (Murakumo-only).
