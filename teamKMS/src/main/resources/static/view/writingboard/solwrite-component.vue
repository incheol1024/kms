<template>
<v-content>
	sol {{name}} {{id}}
	<vue-ckeditor v-model="content" :config="config" />
</v-content>
</template>

<script>
module.exports = {
  props: ["id"],
  data() {
    return {
      content: "",
      config: {}
    };
  },
  methods: {}
}
</script>