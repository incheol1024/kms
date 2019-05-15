<template>
    <chartjs-horizontal-bar :width="200" :height="100" :option="option" :data="data"></chartjs-horizontal-bar>
</template>

<script>
    module.exports = {
        data: () => ({
            data : [11],
            option: {
                responsive: false,
                maintainAspectRatio: false,
                tooltips :{
                    mode : 'point'
                },
                animation: {
                    easing : "linear"
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