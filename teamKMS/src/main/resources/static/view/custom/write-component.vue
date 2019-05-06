<template>
    <ckeditor :editor="editor" v-model="editorData" :config="editorConfig" :disabled="editorDisabled"></ckeditor>
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
        watch : {
            readOnly : function (value) {
                console.log(value);
            }
        },
        data: () => ({
            editorData: '',
            editor: ClassicEditor,
            editorConfig: {},
            editorDisabled: false
        }),
        methods : {
            getText : function () {
                return this.editorData;
            }
        }
    };
</script>
