<template>
<v-content>
<v-container fluid grid-list-md>
<v-layout wrap>		

	<v-flex d-flex xs12 lg6 md6>
	<v-card>
		<v-card-title class="purple">
			<v-icon dark size="30" class="mr-3">mdi-magnify</v-icon>
			<h2 class="display-1 white--text font-weight-light">New Patch Arrived</h2>
        </v-card-title>
        <v-container>
			<v-card>
				<v-list two-line>
					<template v-for="(item) in pacthitmes" >
						<v-list-tile  :key="item.title" avatar ripple>
							<v-list-tile-content>
								<v-list-tile-title>{{ item.title }}</v-list-tile-title>
								<v-list-tile-sub-title class="text--primary">{{ item.headline }}</v-list-tile-sub-title>
							</v-list-tile-content>				
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
			<h2 class="display-1 white--text font-weight-light">Month Error Rate</h2>
        </v-card-title>
        <v-container>
			<chartjs-pie :option="myoption"></chartjs-pie>
        </v-container>
	</v-card>
	</v-flex>
	
	<v-flex d-flex xs12 lg6 md6>
	<v-card>
		<v-card-title class="cyan">
			<v-icon dark size="30" class="mr-3">mdi-magnify</v-icon>
			<h2 class="display-1 white--text font-weight-light">New Project Document</h2>
        </v-card-title>
        <v-container>
			<chartjs-bar></chartjs-bar>
        </v-container>
	</v-card>
	</v-flex>
	
	<v-flex d-flex xs12 lg6 md6>
	<v-card>
		<v-card-title class="amber">
			<v-icon dark size="30" class="mr-3">mdi-magnify</v-icon>
			<h2 class="display-1 white--text font-weight-light">New Posted</h2>
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
</v-container>
</v-content>
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
      { text: "name", value: "name", sortable: false },
      { text: "type", value: "type", sortable: false },
      { text: "date", value: "date", sortable: false},
      { text: "actions", value: "actions", sortable: false }
    ],
	items: [],
	pacthitmes : [
		{ headline: 'EDM 4.1', title: 'EDM Bug Patch'}
	]
  }),
  methods: {}
};
</script>