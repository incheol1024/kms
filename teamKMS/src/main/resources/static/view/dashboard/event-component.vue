<template>
    <v-layout column>
        <h2>New Project Document</h2>
        <v-layout>
            <shortencard-component color="blue"
                                   icon="check_box"
                                   title="프로젝트"
                                   value="완료"
                                   sub-icon=""
                                   sub-text="A사이트 프로젝트 완료" class="mr-3">
            </shortencard-component>

            <shortencard-component color="green"
                                   icon="settings"
                                   title="프로젝트"
                                   value="진행중"
                                   sub-icon=""
                                   sub-text="B사이트 프로젝트 중 C단계" class="mr-3">
            </shortencard-component>

            <shortencard-component color="red"
                                   icon="error"
                                   title="솔루션"
                                   value="패치"
                                   sub-icon=""
                                   sub-text="6.1.3 긴급수정" class="mr-3">
            </shortencard-component>
        </v-layout>
    </v-layout>
</template>

<script>
    module.exports = {
        data: () => ({}),
        methods: {}
    };
</script>

<style scoped>

</style>