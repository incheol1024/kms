<template>
    <v-data-table :headers="header" :items="dataset" :pagination.sync="pagination" :search="search"
                  :total-items="total" :loading="loading" must-sort class="elevation-1">
        <template slot="items" slot-scope="props">
            <tr>
                <td v-if="mappingheader(prop)" v-for="(value,prop) in props.item">{{ value }}</td>
                <td v-if="deletefunction != null || editfunction != null">
                    <v-icon v-if="editfunction" small class="mr-2" @click="editItem(props.item)">edit</v-icon>
                    <v-icon v-if="deletefunction" small @click="deleteItem(props.item)">delete</v-icon>
                </td>
            </tr>
        </template>
    </v-data-table>
</template>

<script>
    module.exports = {
        props: {
            header: [],
            pagefunction: Function,
            search: "",
            addfunction: null,
            deletefunction: null,
            editfunction: null
        },
        data: () => ({
            pagination: {},
            loading: false,
            dataset: [],
            total: 0
        }),
        watch: {
            pagination: {
                handler() {
                    this.loading = true;
                    this.dataset = [];
                    let _this = this;
                    this.pagefunction(this.getSpringType(this.pagination)).then(value => {
                        _this.total = value.data.totalElements;
                        let max = value.data.content.length;
                        for (var i = 0; i < max; i++) {
                            _this.dataset.push(value.data.content[i]);
                        }
                    }).catch(reason => catchPromise(reason));
                    this.loading = false
                }
            }
        },
        methods: {
            getSpringType: function getSpringType(pagination) {
                return jsTojavaPage(pagination);
            },
            addItem: function addItem(item) {
                let _this = this;
                this.addfunction(item).then(_this.dataset.push(item))
                    .catch(reason => catchPromise(reason));
            },
            editItem: function editItem(item) {
                this.editfunction(item).catch(reason => catchPromise(reason));
            },
            deleteItem: function deleteItem(item) {
                let _this = this;
                this.deletefunction(item).then(_this.dataset.splice(_this.dataset.indexOf(item), 1)).catch(reason => catchPromise(reason));
            },
            mappingheader : function mappingheader(prop) {
                let max = this.header.length;
                for (let i = 0; i < max; i++) {
                    if(this.header[i].value === prop)
                    return true;
                }
                return false;
            }
        }
    }
</script>

<style scoped>
</style>