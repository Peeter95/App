
(ns backend.main
  (:require
    [backend.handler             :refer [app]]
    [org.httpkit.server          :as http.server]
    [shadow.cljs.devtools.api    :as shadow.core]
    [shadow.cljs.devtools.server :as shadow.server])
  (:gen-class))

(defonce server (atom nil))

(defonce MAX_BODY_SIZE 1048576000)

(defn server-start! [{:keys [port]}]
  (let [server-config {:port     3000
                       :max-body MAX_BODY_SIZE}]
    (when-not (nil? @server)
              (@server)
              (reset! server nil))
    (reset! server (http.server/run-server #'app server-config))))

(defn -main [& [port]]
  (server-start! {:port port}))

(defn dev [{:keys [port shadow-build]}]
  (server-start! {:port port})
  (shadow.server/stop!)
  (shadow.server/start!)
  (shadow.core/watch shadow-build))
