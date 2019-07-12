<template inline-template>
    <div>
        <v-data-table :headers="headers" :items="datas" :pagination.sync="pagination" :total-items="totalCount"
                      :select-all="!!allowSelect" v-model="selection" 
                      :loading="loading" must-sort class="elevation-1">
            <template slot="items" slot-scope="props">
                <tr>
                    <td v-if="allowSelect">
                        <v-checkbox v-model="props.selected" primary hide-details @change="updateSelection"></v-checkbox>
                    </td>
                    <td v-for="value in mappingHeader(props.item)" @click="clickRow(props.item)">{{ value }}</td>
                    <td v-if="allowDelete || allowEdit">
                        <v-icon v-if="allowEdit" small class="mr-2" @click="editItem(props.item)">edit</v-icon>
                        <v-icon v-if="allowDelete" small @click="deleteItem(props.item)">delete</v-icon>
                    </td>
                </tr>
            </template>
        </v-data-table>
    </div>
</template>

<script>
    module.exports = {
        props: {
            headers: {
                default: [],
                type: Array,
                required: true
            },
            pageReq: {
                default: function (page) {
                    return new Promise(function (resolve, reject) {
                    })
                },
                type: Function
            },
            pageRes: {
                default: function (value, _this) {
                    _this.datas = value.data.content;
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
                    this.datas.push(copyObject(item));
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
                default() {
                    return [];
                },
                type: Array
            },
            search: {
                default: "",
                type: String
            },
            clickRow: {
                default: function (item) {
                },
                type: Function
            }
        },
        data: () => ({
            datas: [],
            pagination: {},
            loading: false,
            length : 0,
            totalCount : 0
        }),
        watch: {
            pagination: {
                handler() {
                    this.sync()
                },
                deep: true
            }
        },
        methods: {
            sync: async function sync(e) {
                this.loading = true;
                let _this = this;
                try {
                    let response = await _this.pageReq(jsTojavaPage(_this.pagination));
                    if(response !== undefined){
                        _this.pageRes(response, _this);
                        _this.totalCount = response.data.totalElements;
                    }
                }
                catch (reason) {
                    catchPromise(reason)
                }
                finally {
                    _this.loading = false
                }
            },
            editItem: function editItem(item) {
                this.editFunction(item);
            },
            deleteItem: function deleteItem(item) {
                let _this = this;
                this.deleteFunction(item).then(res => {
                    _this.datas.splice(_this.datas.indexOf(item), 1);
                }).catch(reason => catchPromise(reason));
            },
            mappingHeader: function mappingHeader(item) {
                let arr = [];
                this.headers.forEach(it => {
                    if ("undefined" !== typeof item[it.value])
                        arr.push(item[it.value]);
                });
                return arr
            },
            updateSelection: function updateSelection() {
                this.$emit("update:selection", this.selection)
            },
            clear: function () {
                this.datas = [];
            }
        }
    }
</script>

<style scoped>
    tr:hover {
        cursor: pointer;
    }

    td:hover {
        cursor: pointer;
    }

    table thead tr th span {
        font-size: 1.0625rem !important;
    }
</style>