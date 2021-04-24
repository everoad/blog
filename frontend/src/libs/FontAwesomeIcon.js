import Vue from "vue"

import {library} from "@fortawesome/fontawesome-svg-core"
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome"
import {faSearch, faBars, faSpinner, faTimes, faAngleRight, faCheckSquare} from "@fortawesome/free-solid-svg-icons"

library.add(faSearch, faBars, faSpinner, faTimes, faAngleRight, faCheckSquare)

Vue.component("font-awesome-icon", FontAwesomeIcon)
