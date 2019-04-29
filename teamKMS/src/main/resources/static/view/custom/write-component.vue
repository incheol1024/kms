<template>
    <ckeditor :editor="editor" v-model="editorData" :config="editorConfig" :disabled="readOnly"></ckeditor>
</template>

<script>
    module.exports = {
        props: {
            editorData: '',
            readOnly:  {
                 default: false,
                type: Boolean
            }
        },
        data: () => ({
            editor: ClassicEditor,
            editorConfig: {}
        })
    };
</script>
