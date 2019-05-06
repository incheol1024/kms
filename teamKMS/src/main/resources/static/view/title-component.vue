<template>
    <v-layout wrap>
        <v-flex d-flex xs12 lg6 md6>
            <v-card>
                <v-card-title class="purple">
                    <v-icon dark size="30" class="mr-3">mdi-magnify</v-icon>
                    <h2 class="display-1 white--text font-weight-light">Patch Note</h2>
                </v-card-title>
                <v-container>
                    <v-card>
                        <v-list two-line>
                            <template v-for="(item) in pacthitmes" >
                                <v-list-tile :key="item.title" avatar ripple>
                                    <v-icon large :style="{color: '{{key}} :{{value}}' }">{{ item.icon }}</v-icon>
                                    <v-list-tile-content>
                                        <v-list-tile-sub-title class="text--primary">{{ item.headline }}
                                        </v-list-tile-sub-title>
                                        <v-list-tile-sub-title>{{ item.title }}</v-list-tile-sub-title>
                                    </v-list-tile-content>
                                    <v-icon :style="{color: '{{key}} :{{value}}' }">{{ item.stIcon }}</v-icon>
                                    <v-btn icon>
                                        <v-icon color="material-icons">zoom_in</v-icon>
                                    </v-btn>
                                </v-list-tile>
                                <v-divider></v-divider>
                            </template>
                        </v-list>
                    </v-card>
                </v-container>
            </v-card>
        </v-flex>

        <v-flex d-flex xs12 lg6 md6>
            <v-card>
                <v-card-title class="red">
                    <v-icon dark size="30" class="mr-3">mdi-magnify</v-icon>
                    <h2 class="display-1 white--text font-weight-light">New Update</h2>
                </v-card-title>
                <v-container>
                    <v-card>
                        <v-list two-line>
                            <v-list-tile avatar>
                                <v-list-tile-content class="headline font-weight-bold">"Test1"</v-list-tile-content>
                            </v-list-tile>
                            <v-card-actions>
                                <v-list-tile-avatar>
                                    <v-icon large color="material-icons">account_box</v-icon>
                                </v-list-tile-avatar>
                                <v-list-tile-content>
                                    <v-list-tile-title>USER</v-list-tile-title>
                                </v-list-tile-content>
                                <v-layout align-center justify-end>
                                    <span class="font-weight-bold">2019-03-01</span>
                                </v-layout>
                            </v-card-actions>
                        </v-list>
                    </v-card>
                </v-container>
            </v-card>
        </v-flex>

        <v-flex d-flex xs12 lg6 md6>
            <v-card>
                <v-card-title class="cyan">
                    <v-icon dark size="30" class="mr-3">mdi-magnify</v-icon>
                    <h2 class="display-1 white--text font-weight-light">Statistics Chart</h2>
                </v-card-title>
                <v-container>
                    <chartjs-pie :option="myoption"></chartjs-pie>
                </v-container>
                <v-container>
                    <chartjs-bar></chartjs-bar>
                </v-container>
            </v-card>
        </v-flex>

        <v-flex d-flex xs12 lg6 md6>
            <v-card>
                <v-card-title class="amber">
                    <v-icon dark size="30" class="mr-3">mdi-magnify</v-icon>
                    <h2 class="display-1 white--text font-weight-light">Resolved Q&A</h2>
                </v-card-title>
                <v-container>
                    <v-data-table :headers="headers" :items="items" class="elevation-1" hide-actions>
                        <template slot="items" slot-scope="props">
                            <td>{{ props.item.id }}</td>
                            <td>{{ props.item.name }}</td>
                            <td>{{ props.item.type }}</td>
                            <td>{{ props.item.groupName }}</td>
                        </template>
                    </v-data-table>
                </v-container>
            </v-card>
        </v-flex>
    </v-layout>
</template>

<script>
    module.exports = {
        data: () => ({
            myoption: {
                responsive: true,
                maintainAspectRatio: true,
                title: {
                    display: true,
                    position: "bottom"
                }
            },
            headers: [
                {text: "name", value: "name", sortable: false},
                {text: "type", value: "type", sortable: false},
                {text: "date", value: "date", sortable: false},
                {text: "actions", value: "actions", sortable: false}
            ],
            items: [],
            pacthitmes: [
                {headline: 'ECM 1.0', title: 'ECM Bug Patch', icon: 'folder', stIcon: 'fiber_new'},
                {headline: 'EDM 2.0', title: 'EDM Bug Patch', icon: 'assignment', stIcon: 'warning'},
                {headline: 'CPS 3.0', title: 'CPS Bug Patch', icon: 'update', stIcon: 'error'}
            ],
            pacthcolors: [
                {icon: 'folder', iconColor: 'grey'},
                {icon: 'assignment', iconColor: 'green'},
                {icon: 'update', iconColor: 'blue'},
                {icon: 'fiber_new', iconColor: 'blue'},
                {icon: 'warning', iconColor: 'orange'},
                {icon: 'error', iconColor: 'red'}
            ]
        }),
        methods: {}
    };
</script>