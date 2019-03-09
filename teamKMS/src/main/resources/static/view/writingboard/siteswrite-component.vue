<template>
<v-content>
	site {{id}}
    <ckeditor :editor="editor" v-model="editorData" :config="editorConfig"></ckeditor>
</v-content>
</template>

<script>
module.exports =  {
	props : ['id'],
	data() {
    return {
        editor: ClassicEditor,
        editorData: '<p>Content of the editor.</p>',
        editorConfig: {
            // The configuration of the editor.
        }
    };
  },
  methods: {
    
  }
}
</script>