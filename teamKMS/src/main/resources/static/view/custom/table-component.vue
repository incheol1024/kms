<template>
    <v-data-table :headers="header" :items="dataset" :pagination.sync="pagination"
                  :total-items="total" :loading="loading" must-sort class="elevation-1">
        <template slot="items" slot-scope="props">
            <td v-for="(value,propertyName) in props.item">{{ value }}</td>
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
            pagination: {},
            loading: false,
            dataset : [],
            total : 0
        }),
        watch: {
            pagination: {
                handler() {
                    try{
                        this.loading = true;
                        this.dataset = [];
                        var _this = this;
                        this.pagefunction(this.getSpringType(this.pagination)).then(function (response) {
                            _this.total = response.data.total
                            var max = response.data.list.length;
                            for (var i = 0; i < max; i++) {
                                _this.dataset.push(response.data.list[i]);
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
                let str = "asc";
                if(pagination.descending) str = "desc";
                return {page: pagination.page , size : pagination.rowsPerPage, sort : pagination.sortBy + "," + str}
            }
        }
    }
</script>

<style scoped>
</style>