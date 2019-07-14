<template>
    <v-card>
        <v-card-title>
            <v-spacer></v-spacer>
            <v-text-field v-model="search" append-icon="search" label="Search" single-line hide-details></v-text-field>
            <v-btn color="primary" @click="newWrite">글쓰기</v-btn>
        </v-card-title>
        <table-component ref="table" :headers="headers" v-model="search" :page-req="getSolutionList"
                         :allow-delete="true" :click-row="clickRow" :delete-function="deleteSolution">
        </table-component>
    </v-card>
</template>

<script>
    module.exports = {
        props: ['id'],
        data: () => ({
            headers: [
                {text: '번호', value: 'boardId'},
                {text: '제목', value: 'subject'},
                {text: '사이트', value: 'site', sortable: false},
                {text: '버전', value: 'version', sortable: false},
                {text: '작성자', value: 'userId', sortable: false},
                {text: '조회수', value: 'hits', sortable: false},
                {text: '수정일', value: 'updDate'},
                {text: '등록일자', value: 'regDate'},
                {text: 'action', value: '', sortable: false}
            ],
            search: ""
        }),
        watch: {
            id: function () {
                this.$refs.table.sync();
            }
        },
        methods: {
            getSolutionList: function (page) {
                return axios.get(`solution/${this.id}`, {
                    params: page
                });
            },
            newWrite: function () {
                router.push(`/solutions/write/${this.id}/0`);
            },
            deleteSolution: function (item) {
                if (confirm("삭제하시겠습니까?"))
                    return axios.delete(`/solution/${item.boardId}`)
            },
            clickRow: function (item) {
                router.push(`/solutions/write/${this.id}/${item.boardId}`);
            },
        }
    }
</script>

<style scoped>

</style>