
(ns compiler
  (:require 
    [hf.depstar               :as depstar]
    [shadow.cljs.devtools.api :as shadow])
  (:gen-class))

(defn compile! [{:keys [java-config js-builds]}]
  (doseq [js-build js-builds]
    (println "Compiling:" js-build)
    (shadow/release js-build))
  (depstar/jar java-config))
