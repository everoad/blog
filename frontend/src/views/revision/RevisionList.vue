<template>
  <div>Revision List
    <table>
      <thead>
      <tr>
        <th v-for="column in columns" :key="column">
          {{ column }}
        </th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="item in items" :key="item.id">
        <td v-for="column in columns" :key="column">
          {{ item[column] }}
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>
<script>
export default {
  props: {
    selectItems: {
      type: Function,
      required: true
    },
    selectColumns: {
      type: Function,
      required: true
    }
  },
  data() {
    return {
      columns: [],
      items: [],
      page: 0,
      size: 10,
      hasNext: true,
      loading: true,
      keywords: {},
      options: {
        columns: []
      }
    }
  },
  methods: {
    async getData() {
      this.loading = true
      const {page, size, selectItems, keywords} = this
      const {items, hasNext} = await selectItems(keywords.classType, {page, size, ...keywords})
      items.forEach(one => this.items.push(one))
      this.hasNext = hasNext
      this.page = this.page + 1
      this.loading = false
    },
    async getColumnData() {
      const {keywords: {classType}} = this
      const {columns} = await this.selectColumns({classType})
      columns.forEach(column => this.columns.push(column))
    },
    createOptions() {

    },
    async filter(keywords) {
      this.keywords = keywords || {}
      this.page = 0
      this.columns.splice(0)
      this.items.splice(0)
      await this.getColumnData()
      await this.getData()
    }
  }
}
</script>