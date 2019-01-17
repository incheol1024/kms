<template>
    <v-data-table :headers="header" :items="dataset" :pagination.sync="pagination"
                  :loading="loading" class="elevation-1">
        <template slot="items" slot-scope="props">
            <td v-for="value in props.item">{{ value }}</td>
        </template>
    </v-data-table>
</template>

<script>
    module.exports  = {
        props: {
            header : [],
            pagefunction : Function
        },
        data: () => ({
            pagination: { rowsPerPage : 0 , page : 0, descending : false, sortBy : ""},
            loading: false,
            dataset : [],
        }),
        watch: {
            pagination: {
                handler() {
                    try{
                        this.loading = true
                        this.dataset = []
                        var _this = this
                        this.pagefunction(this.getSpringType(this.pagination)).then(function (response) {
                            console.log(response)
                            var max = response.data.length;
                            for (var i = 0; i < max; i++) {
                                _this.dataset.push(response.data[i]);
                            }
                        }).catch(function (error) {
                            alert(error);
                        })
                    }finally {
                        this.loading = false
                    }
                }
            }
        },
        methods: {
            getSpringType : function getSpringType(pagination) {
                let str = "asc"
                if(pagination.descending) str = "desc"
                return {page: pagination.page , size : pagination.rowsPerPage, sort : pagination.sortBy + "," + str}
            }
        }
    }
</script>

<style scoped>
</style>