<template>
    <v-content>
        qna {{name}} {{id}}
        <v-layout row wrap>
            <v-flex xs12>

        <write-component ref="commentEditor" :toolbar="false"></write-component>

            </v-flex>
        </v-layout>

<!--        <codemirror v-model="code" :options="cmOptions"></codemirror>-->
<!--        <codemirror ref="myCm" :value="code" :options="cmOptions" @ready="onCmReady" @focus="onCmFocus"-->
<!--                    @input="onCmCodeChange">-->
<!--        </codemirror>-->
<!--        <no-ssr placeholder="Codemirror Loading...">-->
<!--            <codemirror ref="myCm" :value="code" :options="cmOptions" @ready="onCmReady" @focus="onCmFocus"-->
<!--                        @input="onCmCodeChange">-->

<!--            </codemirror>-->
<!--        </no-ssr>-->


    </v-content>
</template>

<script>
    module.exports = {
        props: ['id', 'name'],
        data() {
            return {
                subject: "subject..",
                contents: 'contents..',
                code: 'const a = 10',
                cmOptions: {
                    tabSize: 4,
                    mode: 'text/javascript',
                    theme: 'base16-dark',
                    lineNumbers: true,
                    line: true,
                }
            }
        },
        created: function () {
        },
        methods: {
            registerQuestion: function () {
                var _this = this;
                console.log("url = /qna/register/" + this.id);
                axios.post("/qna/register/" + this.id, {
                    subject: this.subject,
                    contents: "content content content",
                })
                    .then(
                        function (response) {
                            console.log("registerQuestion is called", response);
                            console.log("before router push");
                            router.push("/qna/write" + _this.name + "/" + _this.id);
                        }
                    )
                    .catch(function (error) {
                        console.log(error);
                    })
            },
            moveToQnaPage: function (_this) {
                console.log("moveToQnaPage is called");
                this.$router.push("/qna/write/" + _this.name + "/" + _this.id);
            }
        }
    }
</script>