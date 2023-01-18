
(ns frontend.core
  (:require
    [clerk.core      :as clerk]
    [reitit.frontend :as reitit]
    [reagent.dom     :as reagent-dom]
    [accountant.core :as accountant]
    [reagent.core    :as reagent :refer [atom]]))

(def router
  (reitit/router
    [["/" :index]]))

(defn configure-navigation! []
  (accountant/configure-navigation!
    {:nav-handler  (fn [path]
                       (let [match (reitit/match-by-path router path)]
                            (reagent/after-render clerk/after-render!)
                            (clerk/navigate-page! path)))
     :path-exists? (fn [path]
                       (boolean (reitit/match-by-path router path)))}))


(defn app []
  [:div#app 
    [:h1 "App"]
    [:p "Lorem Ipsum is simply dummy text of the printing and typesetting industry."]])

(defn render! []
  (reagent-dom/render [app] (.getElementById js/document "app")))

(defn start! []
  (clerk/initialize!)
  (configure-navigation!)
  (accountant/dispatch-current!)
  (render!))
