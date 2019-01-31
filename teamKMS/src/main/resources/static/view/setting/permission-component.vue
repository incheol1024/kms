<template>
    <v-layout column>
        <v-flex class="mb-5">
            <v-card>
                <v-card-title>Permission Control</v-card-title>
            </v-card>

            <v-layout>
                ACL TABLE (생성 삭제 조회 권한 이 여기_
            </v-layout>
        </v-flex>

        <v-flex>
            <v-layout>
                <v-flex>
                    user,group 검색 가능한 테이블
                    그리고 선택
                </v-flex>
                <v-flex>
                    acl 리스트 >> user,또는 그룹이 선태된 경우
                    그 이름의 acl은 활성화 시킴. 선택 후 confirm으로 업데이트 또는 new 시킴
                </v-flex>
            </v-layout>
        </v-flex>
    </v-layout>
</template>

<script>
    module.exports = {
        data: () => ({}),
        methods: {}
    }
</script>