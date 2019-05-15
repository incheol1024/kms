<template>
    <v-layout column>
        <h2>Today Q&A</h2>
        <table-component :headers="headers"></table-component>
    </v-layout>
</template>

<script>
    module.exports = {
        data: () => ({
            headers: [
                {text: '번호', value: 'boardId'},
                {text: '제목', value: 'subject'},
                {text: '작성자', value: 'userId',sortable : false},
                {text: '조회수', value: 'hits' ,sortable : false},
                {text: '등록일자', value: 'regDate'},
                {text: 'action', value: '' , sortable : false}
            ]
        }),
        methods: {}
    };
</script>

<style scoped>

</style>