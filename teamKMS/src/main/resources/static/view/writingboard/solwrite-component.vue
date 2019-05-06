<template>
    <v-layout column>
        <v-layout>
            <v-flex xs12 sm6 md3>
                <v-text-field label="title" placeholder="subject" v-model="subject"></v-text-field>
            </v-flex>
            <v-flex xs12 sm4>
                <v-text-field placeholder="solution" label="solution" v-model="solution"></v-text-field>
            </v-flex>
            <v-flex xs12 sm4>
                <v-text-field placeholder="site" label="site" v-model="site"></v-text-field>
            </v-flex>
        </v-layout>
        <write-component ref="editor" :read-only="readOnly"></write-component>
        <v-btn v-if="!readOnly" color="primary" @click="save">{{buttonName}}</v-btn>
        <comment-component v-if="mode === 1" comment-component :qid="$route.params.id"></comment-component>
    </v-layout>
</template>

<script>
    module.exports = {
        data: () => ({
            subject: '',
            solution: '',
            site: '',
            buttonName: "New",
            url: "solution/register",
            boardId : 0
        }),
        watch : {
            '$route.params.id' : {
                handler: function(id) {
                    if (id === "0") {
                        this.buttonName = "New";
                        this.url = "solution/register";
                        this.boardId = 0;
                    } else {
                        this.buttonName = "Edit";
                        this.url = "solution/edit";
                        this.boardId = id;
                    }
                },
                deep: true,
                immediate: true
            }
        },
        methods: {
            save: function save() {
                let board = { subject: this.subject, contents: this.$refs.editor.getText(), boardId : this.boardId };
                let solution = { site: this.site, solution: this.solution, boardId : this.boardId};
                let data = Object.assign({}, board, solution);
                axios.post(this.url, data).then(response => {})
                    .catch(error => { catchPromise(error)});
                this.$router.push("/solutions/" + this.id);
            }
        }
    };

</script>