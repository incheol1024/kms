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
        <write-component :editor-data="contents"></write-component>
        <v-btn color="primary" @click="save">{{buttonName}}</v-btn>
        <comment-component :qid="id"></comment-component>
    </v-layout>
</template>

<script>
    module.exports = {
        props: ['id'],
        data: () => ({
            subject: '',
            solution: '',
            site: '',
            contents: '',
            buttonName: "New",
            url: "solution/register"
        }),
        watch : {
            '$route.params.id' : {
                handler: function(id) {
                    console.log(id);
                    if (id === "0") {
                        this.buttonName = "New";
                        this.url = "solution/register";
                    } else {
                        this.buttonName = "Edit";
                        this.url = "solution/edit";
                    }
                },
                deep: true,
                immediate: true
            }
        },
        methods: {
            save: function tryWrite() {
                let board = { subject: this.subject, contents: this.contents};
                let solution = { site: this.site, solution: this.solution};
                let data = Object.assign({}, board, solution);
                axios.post(this.url, data).then(response => {})
                    .catch(error => { catchPromise(error)});
                this.$router.push("/solutions/" + this.id);
            }
        }
    };

</script>