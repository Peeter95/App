
(ns backend.page
  (:require
    [hiccup.page :refer [include-js include-css html5]]))

;; -----------------------------------------------------------------------------
;; ---- Head ----

(defn head []
  [:head
    [:meta {:charset "utf-8"}]
    [:meta {:name    "viewport"
            :content "width=device-width, initial-scale=1"}]
    [:title "App"]

    (include-css "/css/app.css")])

;; ---- Head ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Body ----

(defn body []
  [:body
    [:h1 "Static content"]
    [:div#app]
    (include-js "/js/app.js")])

;; ---- Body ----
;; -----------------------------------------------------------------------------

(defn page []
  (html5
    (head)
    (body)))
