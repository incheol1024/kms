<template>
    <v-layout row wrap mb-5>
        <v-flex xs12>
            <v-card>
                <v-card-title>
                    <div>
                        <h1 class="headline mb-0">Q.{{ title }}</h1>
                    </div>
                </v-card-title>
                <v-divider inset light></v-divider>
                <v-card-text>
                    <div>
                        <h2>{{ content }}</h2>
                    </div>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn icon>
                        <v-icon>favorite</v-icon>
                    </v-btn>
                    <v-btn icon>
                        <v-icon>bookmark</v-icon>
                    </v-btn>
                    <v-btn icon>
                        <v-icon>share</v-icon>
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-flex>
    </v-layout>
</template>

<script>
    module.exports = {
        props: ['id', 'name', 'qid'],
        data() {
            return {
                title: "defualt title",
                content: 'default content',
                boardId: 0
            }
        },
        created: function () {
            this.getQuestionById();
        },
        methods: {
            getQuestionById: function () {

                axios.get("/qna/answer/" + this.qid)
                    .then(response => {
                            this.title = response.data.subject;
                            this.content = response.data.contents;
                        }
                    )
                    .catch(error => {
                        console.log(error)
                    });
            }
        }
    }
</script>
