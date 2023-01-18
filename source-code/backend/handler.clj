
(ns backend.handler
  (:require
      [reitit.ring                   :as reitit-ring]
      [ring.middleware.gzip          :as ring.gzip]
      [ring.middleware.reload        :as ring.reload]
      [ring.middleware.transit       :as ring.transit]
      [ring.middleware.defaults      :as ring.defaults]
      [ring.middleware.params        :as ring.params]
  
      [buddy.auth.backends.session :as auth.session]
     
      [backend.page :as backend.page]))

(def backend
  "For The Buddy authenticating services"
  (auth.session/session-backend))

(defn request-wrap [status content-type body]
  "Wrap request with status and headers"
  {:status  status
   :headers {"Content-Type" content-type}
   :body    body})

(def router
  (reitit-ring/router
    [["/" {:get {:handler (fn [req] 
                            (request-wrap 200 "text/html" (backend.page/page)))}}]]))

(def routes 
  (reitit-ring/routes
    (reitit-ring/create-resource-handler {:path "/" :root "/public"})
    (reitit-ring/create-default-handler)))

(def middleware-handlers 
  [#(ring.params/wrap-params %)
   #(ring.defaults/wrap-defaults % ring.defaults/site-defaults)
   #(ring.reload/wrap-reload % {:dirs ["source-code/backend"]})
   #(ring.transit/wrap-transit-params % {:opts {}})
   #(ring.gzip/wrap-gzip %)])

(def app
  (reitit-ring/ring-handler
    router
    routes
    {:middleware middleware-handlers}))
     