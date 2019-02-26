<template inline-template>
    <v-data-table :headers="headers" :items="items" :pagination.sync="pagination"
                  :select-all="allowSelect ? true : false" v-model="selection"
                  :total-items="total" :loading="loading" must-sort class="elevation-1">
        <template slot="items" slot-scope="props">
                <td v-if="allowSelect">
                    <v-checkbox v-model="props.selected" primary hide-details @change="updateSelection"></v-checkbox>
                </td>
                <td v-if="mappingHeader(prop)" v-for="(value,prop) in props.item">{{ value }}</td>
                <td v-if="allowDelete || allowEdit">
                    <v-icon v-if="allowEdit" small class="mr-2" @click="editItem(props.item)">edit</v-icon>
                    <v-icon v-if="allowDelete" small @click="deleteItem(props.item)">delete</v-icon>
                </td>
        </template>
    </v-data-table>
</template>

<script>
    module.exports = {
        props: {
            headers: {
                default: [],
                type: Array,
                required : true
            },
            pageReq: {
                default: function (page) {
                    return new Promise(function (resolve, reject) {
                    })
                },
                type: Function,
                required : true
            },
            pageRes: {
                default: function (value, _this) {
                    _this.total = value.data.totalElements;
                    let max = value.data.content.length;
                    for (let i = 0; i < max; i++) {
                        _this.items.push(value.data.content[i]);
                    }
                },
                type: Function
            },
            allowEdit: {
                default: false,
                type: Boolean
            },
            allowDelete: {
                default: false,
                type: Boolean
            },
            allowSelect: {
                default: false,
                type: Boolean
            },
            addFunction: {
                default: function (item) {
                    this.items.push(item);
                },
                type: Function
            },
            editFunction: {
                default: function (item) {
                },
                type: Function
            },
            deleteFunction: {
                default: function (item) {
                    return new Promise(function (resolve, reject) {
                    })
                },
                type: Function
            },
            selection: {
                default: [],
                type: Array
            }
        },
        data: () => ({
            items: [],
            pagination: {},
            loading: false,
            total: 0
        }),
        watch: {
            pagination: {
                handler() {
                    this.loading = true;
                    this.items = [];
                    let _this = this;
                    this.pageReq(jsTojavaPage(_this.pagination)).then(value => {
                        this.pageRes(value, _this);
                    }).catch(reason => catchPromise(reason))
                        .finally(_this.loading = false);
                }
            }
        },
        methods: {
            editItem: function editItem(item) {
                this.editFunction(item);
            },
            deleteItem: function deleteItem(item) {
                let _this = this;
                this.deleteFunction(item).then(_this.items.splice(_this.items.indexOf(item), 1))
                    .catch(reason => catchPromise(reason));
            },
            mappingHeader: function mappingHeader(prop) {
                let max = this.headers.length;
                for (let i = 0; i < max; i++) {
                    if (this.headers[i].value === prop)
                        return true;
                }
                return false;
            },
            updateSelection: function updateSelection() {
                this.$emit("update:selection", this.selection)
            }
        }
    }
</script>

<style scoped>
</style>