<template>
    <v-layout column>
        <v-card>
            <v-card-title> {{name}}
                <v-spacer></v-spacer>
                <v-text-field v-model="search" append-icon="search" label="Search" single-line hide-details></v-text-field>
                <v-btn color="primary" @click="moveToPage">글쓰기</v-btn>
            </v-card-title>
            <table-component ref="table" :headers="headers" :search="search" :page-req="getSolutionList"
                             :allow-delete="true" :allow-edit="true"
                             :edit-function="editSolution" :delete-function="deleteSolution">
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
                {text: '번호', value: 'id'},
                {text: '제목', value: 'title'},
                {text: '작성자', value: 'userName',sortable : false},
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
                let _this = this;
                return axios.get("solution/" + _this.id, {
                    params : page
                });
            },
            moveToPage: function() {
                this.$router.push("/solutions/write/0");
            },
            deleteSolution: function (item) {
                if (confirm("삭제하시겠습니까?"))
                    return axios.delete('/solution/' + item.id)
            },
            editSolution : function (item){
                this.$router.push("/solutions/write/" + item.id);
            }
        }
    };
</script>
