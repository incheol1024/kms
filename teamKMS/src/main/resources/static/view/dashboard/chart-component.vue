<template>
    <v-card height="100%">
        <v-card-title primary-title>
            <v-layout>
                <chartjs-horizontal-bar :option="option" :data="data"></chartjs-horizontal-bar>
            </v-layout>
        </v-card-title>
        <v-card-actions>
        </v-card-actions>
    </v-card>
</template>

<script>
    module.exports = {
        data: () => ({
            data : [11],
            option: {
                responsive: true,
                maintainAspectRatio: true,
                title: {
                    display: false
                },
                scales: {
                    yAxes: [{
                        display: false
                    }],
                    xAxes: [{
                        display: false
                    }]
                },
                legend: {
                    display: false
                }
            }
        }),
        methods: {}
    };
</script>

<style scoped>

</style>