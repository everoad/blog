import Vue from "vue"

import {library} from "@fortawesome/fontawesome-svg-core"
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome"
import {faLink, faRedo, faUndo, faSearch, faBars, faClock, faEye} from "@fortawesome/free-solid-svg-icons";

library.add(faLink, faRedo, faUndo, faSearch, faBars, faClock, faEye)

Vue.component("font-awesome-icon", FontAwesomeIcon)
