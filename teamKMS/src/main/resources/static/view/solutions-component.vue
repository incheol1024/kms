<template>
    <v-layout column>
        <v-tabs>
            <v-tab>
                Patch Note
            </v-tab>
            <v-tab>
                Bug Report
            </v-tab>
            <v-tab>
                Site Version History
            </v-tab>
            <v-tab>
                Manual
            </v-tab>
            <v-tab-item>
                <solpatch-component :id="id"></solpatch-component>
            </v-tab-item>
            <v-tab-item>
                <solbug-component :id="id"></solbug-component>
            </v-tab-item>
            <v-tab-item>
                <solsite-component :id="id"></solsite-component>
            </v-tab-item>
            <v-tab-item>
                <solmenual-component :id="id"></solmenual-component>
            </v-tab-item>
        </v-tabs>
    </v-layout>
</template>

<script>
    module.exports = {
        name: 'solution',
        props: ['id'],
    };
</script>
