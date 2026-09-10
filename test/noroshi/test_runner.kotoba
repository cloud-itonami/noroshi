(ns noroshi.test-runner
  (:require [clojure.test :as t]
            [noroshi.murakumo-test]
            [noroshi.cells.active-alignment.test-state-machine]
            [noroshi.cells.device-design.test-state-machine]
            [noroshi.cells.fibre-loop.test-state-machine]
            [noroshi.cells.reliability-qual.test-state-machine]
            [noroshi.methods.test-active-alignment]
            [noroshi.methods.test-cable-endpoint]
            [noroshi.methods.test-charter-invariants]
            [noroshi.methods.test-complex]
            [noroshi.methods.test-consistency]
            [noroshi.methods.test-device-design]
            [noroshi.methods.test-fibre-loop]
            [noroshi.methods.test-governance]
            [noroshi.methods.test-isac-sim]
            [noroshi.methods.test-kami-isac-bridge]
            [noroshi.methods.test-lexicons]
            [noroshi.methods.test-link-budget]
            [noroshi.methods.test-mt19937]
            [noroshi.methods.test-pic-layout]
            [noroshi.methods.test-pid-controller]
            [noroshi.methods.test-reliability-qual]))

(def suites
  '[noroshi.murakumo-test
    noroshi.cells.active-alignment.test-state-machine
    noroshi.cells.device-design.test-state-machine
    noroshi.cells.fibre-loop.test-state-machine
    noroshi.cells.reliability-qual.test-state-machine
    noroshi.methods.test-active-alignment
    noroshi.methods.test-cable-endpoint
    noroshi.methods.test-charter-invariants
    noroshi.methods.test-complex
    noroshi.methods.test-consistency
    noroshi.methods.test-device-design
    noroshi.methods.test-fibre-loop
    noroshi.methods.test-governance
    noroshi.methods.test-isac-sim
    noroshi.methods.test-kami-isac-bridge
    noroshi.methods.test-lexicons
    noroshi.methods.test-link-budget
    noroshi.methods.test-mt19937
    noroshi.methods.test-pic-layout
    noroshi.methods.test-pid-controller
    noroshi.methods.test-reliability-qual])

(defn -main [& _]
  (let [{:keys [fail error]} (apply t/run-tests suites)]
    (when (pos? (+ fail error))
      (System/exit 1))))
