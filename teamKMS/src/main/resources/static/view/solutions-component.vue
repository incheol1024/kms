<template>
    <v-layout column>
        <v-card>
            <v-card-title> {{name}}
                <v-spacer></v-spacer>
                <v-text-field v-model="search" append-icon="search" label="Search" single-line hide-details></v-text-field>
                <v-btn color="primary" @click="newWrite">글쓰기</v-btn>
            </v-card-title>
            <table-component ref="table" :headers="headers" :search="search" :page-req="getSolutionList"
                             :allow-delete="true" :click-row="clickRow" :delete-function="deleteSolution">
            </table-component>
        </v-card>
    </v-layout>
</template>

<script>
    module.exports = {
        name: 'solution',
        props: ['id', 'name'],
        data: () => ({
            headers: [
                {text: '번호', value: 'boardId'},
                {text: '제목', value: 'subject'},
                {text: '작성자', value: 'userId',sortable : false},
                {text: '조회수', value: 'hits' ,sortable : false},
                {text: '등록일자', value: 'regDate'},
                {text: 'action', value: '' , sortable : false}
            ],
            search : ""
        }),
        watch : {
            id : function(){
                this.$refs.table.sync();
            }
        },
        methods: {
            getSolutionList: function (page) {
                return this.$axios.get(`solution/${this.id}`, {
                    params : page
                });
            },
            newWrite: function() {
                this.$router.replace({path: "/solutions/write/0", query: { readOnly: false}});
            },
            deleteSolution: function (item) {
                if (confirm("삭제하시겠습니까?"))
                    return this.$axios.delete(`/solution/${item.boardId}`)
            },
            clickRow : function (item){
                this.$router.replace({path: `/solutions/write/${item.boardId}`, query: {readOnly: true}});
            }
        }
    };
</script>
