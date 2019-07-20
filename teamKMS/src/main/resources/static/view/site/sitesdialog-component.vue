<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-layout>
         <v-dialog v-model="dialog" width="500" persistent="true">
            <v-card>
                <v-card-title class="headline primary lighten-2" primary-title>
                    ADD NEW PROJECT
                </v-card-title>
                <v-card-text>
                    <v-layout column>
                        <v-flex xs12 sm6 md4>
                            <v-text-field v-model="curProject.name" label="Name"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm6 md4>
                            <v-menu v-model="startDialog" :close-on-content-click="false" :nudge-right="40" lazy
                                    transition="scale-transition" offset-y full-width min-width="290px">
                                <template v-slot:activator="{ on }">
                                    <v-text-field v-model="curProject.startDate" label="Start Date"
                                                  prepend-icon="event"
                                                  readonly v-on="on">
                                    </v-text-field>
                                </template>
                                <v-date-picker v-model="curProject.startDate"
                                               @input="startDialog = false"></v-date-picker>
                            </v-menu>
                        </v-flex>
                        <v-flex xs12 sm6 md4>
                            <v-menu v-model="endDialog" :close-on-content-click="false" :nudge-right="40" lazy
                                    transition="scale-transition" offset-y full-width min-width="290px">
                                <template v-slot:activator="{ on }">
                                    <v-text-field v-model="curProject.endDate" label="End Date"
                                                  prepend-icon="event" readonly v-on="on">
                                    </v-text-field>
                                </template>
                                <v-date-picker v-model="curProject.endDate" @input="endDialog = false"></v-date-picker>
                            </v-menu>
                        </v-flex>
                        <v-flex xs12 sm6 md4>
                            <v-text-field v-model="curProject.manager" label="Manager"></v-text-field>
                        </v-flex>
                    </v-layout>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" flat @click="dialog =false">Close</v-btn>
                    <v-btn color="blue darken-1" flat @click="addProject">Save</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-layout>
</template>

<script>
    module.exports = {
        props: ["id"],
        mounted: function () {

        },
        watch: {
            // id: function (id) {
            //     this.window=0;
            //     this.getSiteList(id);
            // },
            // 'curSite.siteId': function () {
            //    this.$refs.table.sync();
            // }
        },
        data: () => ({
            curProject: copyObject(PROJECTMODEL),
            dialog: false,
            startDialog: false,
            endDialog: false,

        }),
        methods: {
            addProject: function () {
                let _this = this;
                _this.curProject.siteId = _this.curSite.siteId;
                _this.curProject.projectId=0;
                axios.put(`site/${_this.curSite.siteId}`, _this.curProject).then(res => {
                    _this.curProject.projectId = res.data;
                    _this.$refs.table.addFunction(_this.curProject);
                }).catch(reason => catchPromise(reason));
                this.dialog = false;
            },

        }
    };
</script>