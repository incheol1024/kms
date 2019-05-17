<template>
    <v-layout column>
        <card-component color="blue"
                        title="프로젝트 현황"
                        text="">
            <v-layout>
                <chart-component :title="'완료'"></chart-component>
                <chart-component :title="'진행중'"></chart-component>
                <chart-component :title="'장애'"></chart-component>
                <chart-component :title="'이슈'"></chart-component>
                <chart-component :title="'지연'"></chart-component>
            </v-layout>
        </card-component>
    </v-layout>
</template>

<script>
    module.exports = {}
</script>

<style scoped>

</style>