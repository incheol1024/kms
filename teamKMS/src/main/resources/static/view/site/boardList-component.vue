<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-layout>
      <v-flex>
            <v-window v-model="window" class="elevation-1" vertical>
                <v-window-item>
                 <v-card-title>

                <!--<v-text-field v-model="search" append-icon="search" label="Search" single-line hide-details></v-text-field>-->
                     <h3>Project : {{curProject.name}}</h3>
                    </v-card-title>

                    <table-component ref="table2" :headers="boardHeader" :page-req="getPage_board"
                                     :click-row="clickBoard"
                                     :allow-delete="true" :delete-function="deleteBoard"></table-component>
                    <v-btn color="primary" @click="addBoard">Add Board</v-btn>
                </v-window-item>
            </v-window>
        </v-flex>
    </v-layout>
</template>

<script>
    module.exports = {
        props: ["id"],
        mounted: function () {
           // this.getSiteList(this.id);
        },
        watch: {
            id: function (id) {
                this.window=0;
                this.getSiteList(id);
            },
            'curSite.siteId': function () {
               this.$refs.table.sync();
            }
        },
        data: () => ({
            window: 0,
            curProject: copyObject(PROJECTMODEL),
            dialog: false,
            startDialog: false,
            endDialog: false,
            boardHeader: [
                {text: "boardId", value: "boardId"},
                {text: "제목", value: "subject"},
                {text: "작성자", value: "userId"},
                {text: "수정일", value: "updDate"},
                {text: "등록일", value: "regDate"},
                {text: "조회수", value: "hits"},
                {text: 'action', value: '', sortable: false}

            ]
        }),
        methods: {
            getBoard: function (item) {
                let _this = this;
                _this.curProject.name=item.name;
                _this.curProject.projectId = item.projectId;
                this.window = 2;
                this.$refs.table2.sync();
            },
            getPage_board: function (page) {
                let _this = this;
                if (this.curSite.siteId === 0) return;
                return axios.get(`site/${_this.id}/${_this.curSite.siteId}/${_this.curProject.projectId}`, {
                    params: page
                });
            },
            addBoard: function () {
                let _this = this;
                router.push(`/sites/write/${_this.id}/${_this.curSite.siteId}/${_this.curProject.projectId}/0`);
            },
            clickBoard: function (item) {
                let _this = this;
                router.push(`/sites/write/${_this.id}/${_this.curSite.siteId}/${_this.curProject.projectId}/${item.boardId}`);
            },
            deleteBoard: function (item) {
                let _this = this;
                if (confirm("삭제하시겠습니까?"))
                    return axios.delete(`site/${_this.curSite.siteId}/${_this.curProject.projectId}/${item.boardId}`);
            }
        }
    };
</script>