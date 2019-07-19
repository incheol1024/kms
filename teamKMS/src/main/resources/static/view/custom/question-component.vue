<template>
    <!--    <v-layout row wrap mb-5>-->
    <v-flex xs12 offset-xs1>
        <v-card :hover="true" :flat="true">
            <v-card-title>
                <div class="title font-weight-light">
                    <p class="display-2">{{ title }}</p>
                </div>
            </v-card-title>
            <v-card-title>
                <div>
                        <span>
                        <v-avatar
                                :tile="tile"
                                :size="avatarSize"
                                color="grey lighten-4">
                            <img src="https://vuetifyjs.com/apple-touch-icon-180x180.png" alt="avatar">
                        </v-avatar>
                        </span>
                    <span>
                            {{userName}}
                            {{regDate}}
                        </span>
                </div>
            </v-card-title>
            <v-divider inset light></v-divider>
            <v-card-text>
                <div class="title font-weight-regular">
                    {{ content }}
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
    <!--    </v-layout>-->
</template>

<script>
    module.exports = {
        props: {
            id: {
                type: Number,
                required: true
            },
            name: {
                type: String,
                required: true
            },
            qid: {
                type: Number,
                required: true
            }
        },
        data() {
            return {
                title: "defualt title",
                content: 'default content',
                userId: '1234',
                userName: 'User TMP',
                teamName: 'Team TMP',
                regDate: '2019-10-11',
                boardId: 0,
                tile: false,
                avatarSize: "80px"
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
                        return response;
                    })
                    .catch(error => {
                        console.log(error)
                    });
            }
        }
    }
</script>
