<template>
  <v-layout column>
    <ckeditor :editor="editor" v-model="editorData" :config="editorConfig" :disabled="readOnly"></ckeditor>
    <v-btn color="primary" flat @click="save">Save</v-btn>
  </v-layout>
</template>

<script>
module.exports = {
  props: {
    editorData: '',
    readOnly : false,
    save: {
      default: function (page) {
        return new Promise(function (resolve, reject) {
        })
      },
      type: Function
    }
  },
  data: () => ({
    editor: ClassicEditor,
    editorConfig: {}
  }),
  methods: {

  }
};
</script>