<template>
    <v-layout column>
        <v-card>
            <v-card-title> {{name}}
                <v-spacer></v-spacer>
                <v-text-field v-model="search"
                              append-icon="search"
                              label="Search"
                              single-line hide-details>
                </v-text-field>
                <v-btn color="primary"
                       @click="moveToWritePage">
                    글쓰기
                </v-btn>
            </v-card-title>
            <table-component ref="table"
                             :headers="headers"
                             v-model="search"
                             :page-req="getQnaList"
                             :allow-delete="true"
                             :click-row="clickRow"
                             :delete-function="deleteQna">
            </table-component>
        </v-card>
    </v-layout>
</template>

<script>
    module.exports = {
        name: 'qna',
        props: ['id', 'name'],
        data: () => ({
            headers: [
                {text: '번호', value: 'boardId'},
                {text: '제목', value: 'subject'},
                {text: '작성자', value: 'userId', sortable: false},
                {text: '조회수', value: 'hits', sortable: false},
                {text: '등록일자', value: 'regDate'},
                {text: 'action', value: '', sortable: false}
            ],
            search: ""
        }),
        created: function () {
            this.getQnaList(this.id);
        },

        methods: {
            getQnaList: function (page) {
                return axios.get(`qna/${this.id}`, {
                        params: page
                    });
            },
            moveToWritePage: function () {
                this.$router.push(`/qna/write/${this.name}/${this.id}`);
            },
            deleteQna: function (item) {
                if (confirm("삭제하시겠습니까?"))
                    return axios.delete(`qna/delete/${item.boardId}`);
            },
            clickRow: function (item) {
                router.push(`/qna/answer/${this.name}/${this.id}/${item.boardId}`);
            }
        },
        watch: {
            id : function(){
                this.$refs.table.sync();
            }
        }
    };
</script>
