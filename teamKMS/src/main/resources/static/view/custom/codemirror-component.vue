<template>
    <codemirror ref="customCm"
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
                cmtCode: ""
            }
        },
        methods: {
            onCmReady(cm) {
                console.log('the editor is readied!', cm)
            },
            onCmFocus(cm) {
                console.log('the editor is focus!', cm)
            },
            onCmCodeChange(newCode){
                console.dir(this);
                const test = this.$refs.customCm.codemirror.options.mode;
                console.dir(test);
            },
            setLanguageMode: function (object) {
                this.mode = object;
            }
        },
        watch: {
            languageMode: function () {
                console.log(this.languageMode);
            }
        }

    }
</script>

<style scoped>

</style>