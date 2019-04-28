<template>
    <v-layout column>
        {{name}}
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
        <write-component :save-name="buttonName" :editor-data="contents" :save="tryWrite"></write-component>
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
            buttonName : ""
        }),
        watch : {
            id : function (){
                if(this.id === 0)
                    this.buttonName = "New";
                else
                    this.buttonName = "Edit";
            }
        },
        methods: {
            tryWrite: function tryWrite() {
                let obj_b = {subject: this.subject, contents: this.contents};
                let obj_s = {site: this.site, solution: this.solution};
                let obj = Object.assign({}, obj_b, obj_s);
                axios.post('/solution/register', obj)
                    .then(
                        function (response) {
                            console.log(response.data);
                        }
                    )
                    .catch(function (error) {
                        console.log("[ERR] : " + error)
                    })
                this.$router.push("/solutions/" + this.id);
            },
            editWrite: function editWrite() {
                let obj_b = {boardId: this.id, subject: this.subject, contents: this.contents};
                let obj_s = {site: this.site, solution: this.solution};
                let obj = Object.assign({}, obj_b, obj_s);
                axios.post('/solution/edit', obj)
                    .then(
                        function (response) {
                            console.log(response.data);
                        }
                    )
                    .catch(function (error) {
                        console.log("[ERR] : " + error)
                    })
                this.$router.push("/solutions/" + this.id);
            }
        }
    };

</script>