<template>
  <div>
    <div class="file"
         @click="handleFileInputClick">
      <span>{{ msg }}</span>
      <span class="remove"
            v-if="file"
            @click.stop="handleRemoveBtnClick">
        <font-awesome-icon icon="times"/>
      </span>
    </div>
    <input type="file" ref="file"
           @change="handleFileInputChange"
           :accept="accept"/>
  </div>
</template>

<script>
export default {
  props: {
    file: {
      type: Object,
      defaults: {
        id: null,
        originalName: null,
        name: null,
        size: null,
        type: null
      }
    },
    onChange: {
      type: Function,
      required: true
    },
    accept: {
      type: String,
      required: false
    },
    message: {
      type: String,
      default: "파일 선택"
    }
  },
  computed: {
    msg() {
      const {file, message} = this
      return file?.originalName || message
    }
  },
  methods: {
    handleFileInputClick() {
      this.$refs.file.click()
    },
    handleFileInputChange(event) {
      this.onChange(event.target.files)
      event.target.value = ''
    },
    handleRemoveBtnClick() {
      this.onChange([])
    }
  }
}
</script>

<style scoped>
.file {
  cursor: pointer;
  line-height: 36px;
  padding-left: 0.5rem;
  border: 1px solid #ccc;
}

.remove {
  float: right;
  z-index: 100;
  font-size: 1.2rem;
  padding: 0 1rem;
  color: #555;
}

input[type="file"] {
  display: none;
}
</style>