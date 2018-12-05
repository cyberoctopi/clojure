(ns simpleserver.domaindb.domain-factory
  (:require
    [environ.core :refer [env]]
    [simpleserver.domaindb.domain-single-node :as ss-domain-single-node]
    [simpleserver.domaindb.domain-local-dynamodb :as ss-domain-local-dynamodb]))

(defmulti -m-create-domain (fn [ssenv] ssenv))

(defmethod -m-create-domain "single-node"
  [env]
  (ss-domain-single-node/->Env-single-node env))

(defmethod -m-create-domain "local-dynamodb"
  [env]
  (ss-domain-local-dynamodb/->Env-local-dynamodb env))

(defmethod -m-create-domain "aws-dynamodb"
  [env]
  (throw (IllegalArgumentException.
           (str "Not yet implemented for aws environment"))))

(defmethod -m-create-domain :default
  [env]
  (throw (IllegalArgumentException.
           (str "Unknown environment: " env))))


(defn create-domain
  []
  (let [ssenv (env :ss-env)]
    (-m-create-domain ssenv)))
