<template>
    <v-layout column>
        <v-card-title>
            <v-btn slot="activator" color="primary" dark class="mb-2" @click="moveToWritePage"> 글쓰기</v-btn>
            <v-spacer></v-spacer>
            <v-text-field v-model="search" append-icon="search" label="검색" single-line hide-details></v-text-field>
        </v-card-title>
        <v-data-table :headers="headers" :items="questions" :search="search">
            <template slot="headerCell" slot-scope="props">
                <v-tooltip bottom>
        <span slot="activator">
          {{ props.header.text }}
        </span>
                    <span>
          {{ props.header.text }}
        </span>
                </v-tooltip>
            </template>
            <template slot="items" slot-scope="props">
                <tr @click="moveToSpecificPage(props.item)">
                    <td class="text-xs-left">{{ props.item.hits }}</td>
                    <td class="text-xs-left">{{ props.item.subject }}</td>
                    <td class="text-xs-left">{{ props.item.userId }}</td>
                    <td class="text-xs-left">{{ props.item.hits }}</td>
                </tr>
            </template>
            <v-alert slot="no-results" :value="true" color="error" icon="warning">
                "{{ search }}"에 대한 결과를 찾을 수 없습니다.
            </v-alert>
        </v-data-table>
    </v-layout>
</template>

<script>
    module.exports = {
        name: 'qna',
        props: ['id', 'name'],
        data: () => ({
            search: '',
            headers: [{
                text: '조회수',
                align: 'left',
                sortable: false,
                value: 'viewCount'
            },
                {
                    text: '제목',
                    value: 'title'
                },
                {
                    text: '작성자',
                    value: 'userName'
                },
                {
                    text: '답변수',
                    value: 'replyCount'
                }
            ],
            questions: []
        }),
        created: function () {
            this.getQnaList(this.id);
        },

        methods: {
            getQnaList: function (id) {
                var that = this;
                axios.get("/qna/" + id)
                    .then(
                        function (response) {
                            that.questions = [];
                            for (var i = 0; i < response.data.length; i++) {
                                that.questions.push(response.data[i]);
                            }
                        }
                    )
                    .catch(function (error) {
                        console.log(error);
                    })
            },
            moveToWritePage: function () {
                this.$router.push("/qna/write/" + this.name + "/" + this.id);
            },
            writepage: function writepage() {
                router.push("/qna/write/" + this.name + "/" + this.id);
            },
            moveToSpecificPage: function (question) {
                console.log('qna-component: moveToSpecificPage() router.push url /qna/answer/' + this.name + "/" + this.id + "/" + question.boardId);
                router.push("/qna/answer/" + this.name + "/" + this.id + "/" + question.boardId);
            }
        },
        watch: {
            id: function (id) {
                this.getQnaList(id);
            }
        }
    };
</script>
