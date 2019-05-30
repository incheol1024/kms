<template>
    <ckeditor :editor="editor" v-model="editorData" :config="editorConfig" :disabled="readOnly"></ckeditor>
</template>

<script>
    module.exports = {
        props: {
            readOnly:  {
                default: false,
                type: Boolean,
                required : true
            }
        },
        data: () => ({
            editorData: '',
            editor: ClassicEditor,
            editorConfig: {}
        }),
        methods : {
            getText() {
                return this.editorData;
            },
            setText(value){
                this.editorData = value;
            }
        }
    };
</script>
