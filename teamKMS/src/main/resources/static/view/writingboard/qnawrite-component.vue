<template>

    <v-container grid-list-xs align-content-center>
        <v-layout row wrap>
            <v-flex xs2>
                <v-combobox
                        v-model="select"
                        :items="items"
                        chips
                        label="language"
                ></v-combobox>
            </v-flex>

            <v-flex xs12>
                <v-text-field
                        v-model="subject"
                        :counter="30"
                        :rules="titleRules"
                        :label="labelTitle"
                        required
                ></v-text-field>
            </v-flex>

            <v-flex xs12>
                <write-component ref="questionEditor"></write-component>
            </v-flex>

            <v-spacer></v-spacer>
            <div></div>
            <v-flex xs12>
                <v-btn
                        :loading="uploading"
                        :disabled="uploading"
                        color="blue-grey"
                        class="white--text"
                        block
                        @click="registerQuestion"
                >
                    Upload
                    <v-icon right dark>cloud_upload</v-icon>
                </v-btn>
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
                labelTitle: "제목",
                titleRules: [
                    v => v.length <= 30 || 'Title must be less than 30 characters'
                ],
                uploading: false,
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

                if (!this.validateQna()) {
                    return;
                }

                this.uploading = true;
                let menuName = this.select;
                let menuId = this.menuAndId[this.select];
                axios.post("/qna/register/" + menuId, {
                    subject: this.subject,
                    contents: this.$refs.questionEditor.getText()
                })
                    .then(response => {
                        router.push("/qna/answer/" + menuName + "/" + menuId + "/" + response.data.boardId);
                        this.uploading = false;
                    })
                    .catch(function (error) {
                        console.log(error);
                    })
            },
            moveToQnaPage: function (_this) {
                console.log("moveToQnaPage is called");
                this.$router.push("/qna/write/" + _this.name + "/" + _this.id);
            },
            validateQna: function () {
                if (this.select === null || this.select === undefined) {
                    alert("select language!")
                    return false;
                } else if (this.subject.trim().length < 1) {
                    alert("fulfill your subject!")
                    return false;
                } else if (this.$refs.questionEditor.getText().trim() < 1) {
                    alert("fulfill your question content!")
                    return false;
                } else {
                    return true;
                }
            }
        }
    }
</script>