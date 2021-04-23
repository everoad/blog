<template>
  <article>
    <CategoryList
      :select="select"
      :insert="insert"
      :update="update"
      :remove="remove" />
  </article>
</template>

<script>
import CategoryList from "@/views/category/CategoryList"
import {categoryService} from "@/services"

export default {
  name: 'Category',
  components: {
    CategoryList
  },
  methods: {
    select() {
      return categoryService.getCategoryList()
    },
    insert(data) {
      return categoryService.addCategory(data)
    },
    update(id, data) {
      return categoryService.editCategory(id, data)
    },
    remove(id) {
      return categoryService.removeCategory(id)
    }
  }
}
</script>
<style scoped>


</style>