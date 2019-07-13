<template>
    <codemirror ref="originalCodemirror"
                :value="cmtCode"
                v-model="cmtCode"
                :options="cmOptions"
                @ready="onCmReady"
                @focus="onCmFocus"
                @input="onCmCodeChange">
    </codemirror>
</template>

<script>
    module.exports = {
        props: {
            languageMode: {
                type: Object,
                required: true
            }
        },
        data() {
            return {
                cmOptions: {
                    tabSize: 4,
                    mode: this.languageMode,
                    theme: 'monokai',
                    lineNumbers: true,
                    line: true,
                    styleActiveLine: true,
                    lineWrapping: true,
                    lineSeparator: "</br>"
                },
                cmtCode: "",
                language:{}
            }
        },
        created: function() {
        },
        methods: {
            onCmReady(cm) {
                console.log('the editor is readied!', cm)
            },
            onCmFocus(cm) {
                console.log('the editor is focus!', cm)
            },
            onCmCodeChange(newCode){
                const test = this.$refs.originalCodemirror.codemirror.options.mode;
                console.dir(test);
            },
            setLanguageMode: function (object) {
                console.log("codemirror-component setLangaugeMode:");
                console.dir(object);
                this.language = object;
            }
        },
        watch: {
            language: function () {
                this.cmOptions["mode"] = this.language;
                console.log("cmOptions[mode]");
                console.dir(this.cmOptions);

            },
        }

    }
</script>

<style scoped>

</style>