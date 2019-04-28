<template>
    <v-layout column>
        <ckeditor :editor="editor" v-model="editorData" :config="editorConfig" :disabled="readOnly"></ckeditor>
        <v-layout>
            <v-spacer></v-spacer>
            <v-btn color="primary" @click="save">{{saveName}}</v-btn>
        </v-layout>
    </v-layout>
</template>

<script>
    module.exports = {
        props: {
            editorData: '',
            readOnly: false,
            save: {
                default: function (page) {
                    return new Promise(function (resolve, reject) {
                    })
                },
                type: Function
            },
            saveName: ""
        },
        data: () => ({
            editor: ClassicEditor,
            editorConfig: {}
        })
    };
</script>