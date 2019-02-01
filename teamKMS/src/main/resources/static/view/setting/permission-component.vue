<template>
    <v-layout column>
        <v-flex class="mb-3">
            <v-card>
                <v-card-title class="title">Acl List</v-card-title>
                <v-layout>
                    <table-component :header="aclheader" :pagefunction="aclpage" action="true"></table-component>
                </v-layout>
            </v-card>
        </v-flex>

        <v-flex>
            <v-card>
                <h5 class="title mb-0">User/Group</h5>
                <v-layout>

                    <v-flex class="mr-3">
                        user,group 검색 가능한 테이블
                        그리고 선택
                        <table-component :header="ugheader" :pagefunction="ugpage" action="true"></table-component>
                    </v-flex>

                    <v-flex>
                        acl 리스트 >> user,또는 그룹이 선태된 경우
                        그 이름의 acl은 활성화 시킴. 선택 후 confirm으로 업데이트 또는 new 시킴
                        <v-list>
                            <v-list-tile>
                                <v-list-tile-action>
                                    <v-checkbox></v-checkbox>
                                </v-list-tile-action>

                                <v-list-tile-content>
                                    <v-list-tile-title>Notifications</v-list-tile-title>
                                    <v-list-tile-sub-title>Allow notifications</v-list-tile-sub-title>
                                </v-list-tile-content>
                            </v-list-tile>
                        </v-list>
                        <v-btn color="primary">Confirm</v-btn>
                    </v-flex>

                </v-layout>
            </v-card>
        </v-flex>
    </v-layout>
</template>

<script>
    module.exports = {
        data: () => ({
            aclheader: [],
            aclpage: Function,
            ugheader: [],
            ugpage: Function
        }),
        methods: {}
    }
</script>