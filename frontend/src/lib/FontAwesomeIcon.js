import Vue from "vue"

import { library } from "@fortawesome/fontawesome-svg-core"
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome"
import { faLink, faRedo, faUndo, faSearch, faBars } from "@fortawesome/free-solid-svg-icons";

library.add(faLink, faRedo, faUndo, faSearch, faBars)

Vue.component("font-awesome-icon", FontAwesomeIcon)
