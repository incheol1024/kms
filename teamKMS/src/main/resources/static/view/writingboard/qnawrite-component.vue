<template>

    <v-container grid-list-xs align-content-center>
        qna {{name}} {{id}}

        <v-layout>
            <v-flex xs12>
                <v-text-field
                        v-model="subject"
                        :counter="30"
                        :rules="titleRules"
                        :label="labelTitle"
                        required
                ></v-text-field>
            </v-flex>
        </v-layout>

        <v-layout row wrap>
            <v-flex xs12>

                <write-component ref="questionEditor"></write-component>

            </v-flex>

            <v-layout row wrap>
                <v-flex xs4>
                    <v-combobox
                            v-model="select"
                            :items="items"
                            chips
                            label="select language you want!"
                    ></v-combobox>
                </v-flex>
                <v-flex xs4>
                    <v-btn round color="primary" dark @click="registerQuestion">질문 등록</v-btn>
                </v-flex>
            </v-layout>

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


    </v-container>
</template>

<script>
    module.exports = {
        props: ['id', 'name'],
        data() {
            return {
                subject: "",
                contents: 'contents..',
                code: 'const a = 10',
                cmOptions: {
                    tabSize: 4,
                    mode: 'text/javascript',
                    theme: 'base16-dark',
                    lineNumbers: true,
                    line: true,
                },
                labelTitle: "제목",
                titleRules: [
                    v => v.length <= 30 || 'Title must be less than 30 characters'
                ],
                select: '',
                items: [
                    'C++',
                    'CSharp',
                    'Java',
                    'Python',
                    'Other'
                ],
                menuAndId: {
                    'Java': 4,
                    'C++': 5,
                    'Python': 6,
                    'CSharp': 7,
                    'Other': 8
                }


            }
        },
        created: function () {
            (function (object) {
                object.select = object.name;
            })(this);
        },
        methods: {
            registerQuestion: function () {
                let menuName = this.select;
                let menuId = this.menuAndId[this.select];
                axios.post("/qna/register/" + menuId, {
                        subject: this.subject,
                        contents: this.$refs.questionEditor.getText()
                    })
                    .then(response => {
                            router.push("/qna/answer/" + menuName + "/" + menuId + "/" + response.data.boardId);
                        })
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