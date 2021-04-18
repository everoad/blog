<template>
  <router-link :to="{ name: 'PostDetail', params: { id: item.id }}" tag="article">
    <div class="content">
      <header>{{ item.title }}</header>
      <div>{{ item.description }}</div>
      <footer>{{ item.createdTime }}</footer>
    </div>
    <div v-if="item.image" class="image"></div>
  </router-link>
</template>

<script>

export default {
  name: 'PostItem',
  components: {},
  props: {
    item: {
      id: Number,
      title: String,
      image: String,
      description: String,
      createdTime: String
    }
  }
}
</script>

<style scoped>
article {
  width: 100%;
  padding: 1.5rem 1.5rem;
  display: flex;
  justify-content: space-between;
  height: 142px;
  cursor: pointer;
  box-sizing: border-box;
}

article:hover {
  background-color: #eee;
}

header {
  color: #222;
  font-size: 1.5rem;
}

.content {
  color: #999;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.image {
  width: 128px;
  border: 1px solid #eee;
  height: 142px;
  box-sizing: border-box;
}

footer {
  color: #999;
  font-size: 0.9rem;
}
</style>
