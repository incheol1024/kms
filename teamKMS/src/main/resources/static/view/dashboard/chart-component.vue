<template>
    <v-layout>
        <h5>{{title}}</h5>
        <chartjs-horizontal-bar :width="150" :height="50" :option="option" :data="data"></chartjs-horizontal-bar>
    </v-layout>
</template>

<script>
    module.exports = {
        props : ['title'],
        data: () => ({
            data: [11],
            option: {
                responsive: false,
                maintainAspectRatio: false,
                tooltips: {
                    mode: 'point'
                },
                animation: {
                    easing: "linear"
                },
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