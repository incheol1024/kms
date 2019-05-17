<template>
    <v-layout>
        <h5>{{title}}</h5>
        <canvas id="mycanvas" count="2"></canvas>
        <chartjs-line :datalabel="first_label"  :data= "first_data" target="mycanvas"></chartjs-line>
        <chartjs-line :datalabel="second_label" :data="second_data" target="mycanvas"></chartjs-line>
    </v-layout>
</template>

<script>
    module.exports = {
        data: () => ({
            first_data : [10,20,30,40],
            first_label : 'first',
            second_label : 'second',
            second_data : [50, 40, 90, 60]
        }),
        methods: {}
    };
</script>

<style scoped>

</style>