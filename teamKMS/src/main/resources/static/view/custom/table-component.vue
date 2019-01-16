<template>
    <v-data-table :headers="headers" :items="dataset" :pagination.sync="pagination"
                  :total-items="total" :loading="loading" class="elevation-1">
        <template slot="items" slot-scope="props">
            <td v-for="value in object">{{ value }}</td>
            <td class="justify-center layout px-0">
                <v-icon small v-on:click="editItem(props.item)">edit</v-icon>
                <v-icon small v-on:click="deleteItem(props.item)">delete</v-icon>
            </td>
        </template>
    </v-data-table>
</template>

<script>
    modules.exports  = {
        props: {
            headers : []
        },
        data: () => ({
            dataset : [],
            pagination: { rowsPerPage : 0 , page : 0, descending : false, sortBy : ""},
            loading: false
        }),
        watch: {
            pagination: {
                handler() {

                }
            }
        },
        methods: {

        }
    }
</script>

<style scoped>

</style>