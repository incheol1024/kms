<template>
    <v-layout column>
        <h2 class="mb-5">New Project Document(쇼트카드 컴포넌트 대체)</h2>
        <v-layout>
            <shortencard-component color="blue"
                                   icon="settings"
                                   title="카드"
                                   value="되나"
                                   sub-icon="settings"
                                   sub-text="잘란다.">
                <v-layout>
                    <h1>여기에 내용들어감</h1>
                </v-layout>
            </shortencard-component>

            <shortencard-component color="green"
                                   icon="settings"
                                   title="카드"
                                   value="되나"
                                   sub-icon="settings"
                                   sub-text="잘란다.">
                <v-layout>
                    <h1>여기에 내용들어감</h1>
                </v-layout>
            </shortencard-component>

            <shortencard-component color="red"
                                   icon="settings"
                                   title="카드"
                                   value="되나"
                                   sub-icon="settings"
                                   sub-text="잘란다.">
                <v-layout>
                    <h1>여기에 내용들어감</h1>
                </v-layout>
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