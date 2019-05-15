<template>
    <v-layout row wrap full-height>
        <v-flex d-flex xs9>
            <v-layout column>
                <v-flex>
                    <project-component></project-component>
                </v-flex>
                <v-flex>
                    <event-component></event-component>
                </v-flex>
            </v-layout>
        </v-flex>

        <v-flex xs3>
            <overview-component></overview-component>
        </v-flex>

        <v-flex xs12>
            <recent-component></recent-component>
        </v-flex>
    </v-layout>
</template>

<script>
    module.exports = {
        data: () => ({}),
        methods: {}
    };
</script>