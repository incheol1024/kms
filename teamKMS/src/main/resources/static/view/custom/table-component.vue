<template> //TODO:: USER REF
    <v-data-table :headers="options.header" :items="dataset" :pagination.sync="pagination" :search="search" select-all
                  :total-items="total" :loading="loading" must-sort class="elevation-1" v-model="options.select.selected">
        <template slot="items" slot-scope="props">
            <tr>
                <td v-if="options.select.allow">
                    <v-checkbox v-model="props.selected" primary hide-details></v-checkbox>
                </td>
                <td v-if="mappingheader(prop)" v-for="(value,prop) in props.item">{{ value }}</td>
                <td v-if="options.deleted.allow || options.edited.allow">
                    <v-icon v-if="options.edited.allow" small class="mr-2" @click="editItem(props.item)">edit</v-icon>
                    <v-icon v-if="options.deleted.allow" small @click="deleteItem(props.item)">delete</v-icon>
                </td>
            </tr>
        </template>
    </v-data-table>
</template>

<script>
    exports = {
        props : {
            options : Object.assign({},TABLEOPTIONS)
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
                    this.options.page.req(jsTojavaPage(this.pagination)).then(value => {
                        this.options.page.res(value,_this);
                    }).catch(reason => catchPromise(reason))
                    .finally( this.loading = false);
                }
            }
        },
        methods: {
            addItem: function addItem(item) {
                this.dataset.push(item);
            },
            //쫌 더 고민
            editItem: function editItem(item) {
                this.editfunction(item).catch(reason => catchPromise(reason));
            },
            deleteItem: function deleteItem(item) {
                let _this = this;
                this.options.deleted.func(item).then(_this.dataset.splice(_this.dataset.indexOf(item), 1)).catch(reason => catchPromise(reason));
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