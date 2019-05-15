<template>
    <v-list two-line>
        <template v-for="(item, index) in items">
            <v-subheader v-if="item.header" :key="item.header">
                {{ item.header }}
            </v-subheader>

            <v-divider v-else-if="item.divider" :key="index" :inset="item.inset"></v-divider>

            <v-list-tile v-else :key="item.title" avatar @click="">
                <v-list-tile-avatar>
                    <img :src="item.avatar">
                </v-list-tile-avatar>

                <v-list-tile-content>
                    <v-list-tile-title>{{item.title}}</v-list-tile-title>
                    <v-list-tile-sub-title>{{ item.subtitle }}</v-list-tile-sub-title>
                </v-list-tile-content>
            </v-list-tile>
        </template>
    </v-list>
</template>

<script>
    module.exports = {
        data: () => ({
            items: [
                { header: 'Today Announce(카드 컴포넌트로 대체)' },
                {
                    avatar: 'https://cdn.vuetifyjs.com/images/lists/1.jpg',
                    title: 'EDM Bug Patch 4.1.1',
                    subtitle : '와 AIX 안됨..'
                },
                { divider: true, inset: true },
                {
                    avatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
                    title: 'ECM New Feature 6.1.2',
                    subtitle : '마이그 자동화'
                },
                { divider: true, inset: true },
                {
                    avatar: 'https://cdn.vuetifyjs.com/images/lists/3.jpg',
                    title: '게맛',
                    subtitle : '간장게장~~'
                }
            ]
        }),
        methods: {}
    };
</script>

<style scoped>

</style>