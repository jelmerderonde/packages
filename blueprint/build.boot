(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.10.0" :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "1.11.0")
(def +version+ (str +lib-version+ "-0"))

(task-options!
  pom {:project     'cljsjs/blueprint
       :version     +version+
       :description "React components for BlueprintJS"
       :url         "http://blueprintjs.com/"
       :scm         {:url "https://github.com/cljsjs/packages"}
       :license     {"BSD" "http://opensource.org/licenses/BSD-3-Clause"}})

(deftask package []
  (comp
    (download :url (str "https://unpkg.com/@blueprintjs/core@" +lib-version+ "/dist/core.bundle.js")
              :target "cljsjs/blueprint/common/core.bundle.inc.js")
    (deps-cljs :requires ["cljsjs.classnames" "cljsjs.dom4" "cljsjs.tether" "cljsjs.react" "cljsjs.react.dom"]
               :provides ["cljsjs.blueprint"]
               :global-exports '{cljsjs.blueprint Blueprint})
    (pom :project 'cljsjs/blueprint
         :dependencies [['cljsjs/classnames "2.2.3-0"]
                        ['cljsjs/dom4 "1.5.2-2"]
                        ['cljsjs/tether "1.4.0-0"]
                        ['cljsjs/react "15.3.1-0"]
                        ['cljsjs/react-dom "15.3.1-0"]])
    (jar)
    (validate)))
