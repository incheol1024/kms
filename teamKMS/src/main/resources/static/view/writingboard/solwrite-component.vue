<template>
    <v-content>
        sol / {{name}} / {{id}} / write page [ {{Solution}} / {{Site}} / {{selected}} ]test / {{fuc}} /

        <v-container id="solution_write" grid-list-xl>
            <v-layout row wrap>
                <v-flex xs12 sm6 md3>
                    <v-text-field
                            label="title"
                            placeholder="title"
                            v-model="subject"
                    ></v-text-field>
                </v-flex>
                <v-flex xs12 sm4>
                    <p>Solution</p>
                    <v-overflow-btn
                            :items="Solution"
                            label="Solution"
                            target="solution_write"
                    ></v-overflow-btn>
                </v-flex>
                <v-flex xs12 sm4>
                    <p>Site</p>
                    <v-overflow-btn
                            :items="Site"
                            label="Site"
                            target="solution_write"
                    ></v-overflow-btn>
                </v-flex>
                <v-flex xs12 sm2>
                    <select v-model="selected.solution">
                        <option disabled value="">select</option>
                        <option>ECM</option>
                        <option>EDM</option>
                        <option>ETC</option>
                    </select>
                </v-flex>
                <v-flex xs12 sm2>
                    <select v-model="selected.site">
                        <option disabled value="">select</option>
                        <option>st1</option>
                        <option>st2</option>
                        <option>st3</option>
                    </select>
                </v-flex>
            </v-layout>
        </v-container>

        <div class="text-xs-center">
            <ckeditor :editor="editor" v-model="contents" :config="editorConfig"></ckeditor>
        </div>

        <div class="text-xs-center" v-if="id === '2'">
            <v-btn color="success" @click="try_write">등록</v-btn>
        </div>

        <div class="text-xs-center" v-else>
            <v-btn color="success" @click="edit_write">편집</v-btn>
        </div>

    </v-content>

</template>

<script>
    module.exports = {
        name: 'solution_write',
        props: ["id"],
        data: () => ({
            Solution: ['ECM', 'EDM', 'ETC'],
            Site: ['site1', 'site2', 'site3'],
            subject:'',
            contents:'',
            selected: '',
            selected2: '',
            editor: ClassicEditor,
            editorData: '',
            editorConfig: {
                // The configuration of the editor.
            }
        }),
        created: function () {
            console.log("id = " + this.$route.params.id);
            var _this = this;
        },
        methods: {
            try_write: function try_write() {
              let obj1 = {subject:this.subject,contents:this.contents};
              let obj2 = Object.assign({},obj1)
                axios.post('/solution/register', obj2)
                    .then(
                        function (response) {
                            _this.answers.push(response.data);
                            console.log(response.data);
                        }
                    )
                    .catch(function (error) {
                        console.log("[ERR] : " + error)
                    })
            },
            edit_write: function edit_write() {
                axios.post('/solution/edit', {})
                    .then(
                        function (response) {
                            _this.answers.push(response.data);
                            console.log(response.data);
                        }
                    )
                    .catch(function (error) {
                        console.log("[ERR] : " + error)
                    })
            },
            cancel_write: function () {
                router.push("/solutions/" + this.id);
            }
        }
    };

</script>