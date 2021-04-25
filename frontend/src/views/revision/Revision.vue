<template>
  <div>
    <select class="input" @change="handleClassTypeChange">
      <option value="">선택</option>
      <option value="post" selected>POST</option>
    </select>
    <RevisionList
        ref="list"
        :select-items="getRevisionData"
        :select-columns="getRevisionColumnData"/>
  </div>
</template>

<script>
import {revisionService} from "@/services/revision.service"
import RevisionList from "@/views/revision/RevisionList"

export default {
  name: "Revision",
  components: {
    RevisionList
  },
  data() {
    return {
      classType: 'post'
    }
  },
  methods: {
    async getRevisionData(params) {
      const {data: {body: {content, last}}} = await revisionService.getRevisionList(params)
      return {items: content, hasNext: !last}
    },
    async getRevisionColumnData({classType}) {
      const {data: {body}} = await revisionService.getRevisionColumnList(classType)
      return body
    },
    handleClassTypeChange(event) {
      const classType = event.target.value
      const keywords = {classType}
      this.$refs.list.filter(keywords)
    }
  }
}
</script>