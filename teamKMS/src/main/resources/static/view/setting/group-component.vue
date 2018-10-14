<template>
<v-layout column>
	<v-flex >
		<v-data-table :headers="headers" :items="desserts" class="elevation-1">
		    <template slot="items" slot-scope="props">
		      <td>{{ props.item.name }}</td>
		      <td class="text-xs-right">{{ props.item.calories }}</td>
		      <td class="text-xs-right">{{ props.item.fat }}</td>
		      <td class="text-xs-right">{{ props.item.carbs }}</td>
		      <td class="text-xs-right">{{ props.item.protein }}</td>
		      <td class="text-xs-right">{{ props.item.iron }}</td>
		    </template>
		</v-data-table>
	</v-flex>
	
	<v-flex>
		<div>Test</div>
	</v-flex>
</v-layout>
</template>

<script>
  module.exports =  {
	 data: () => ({
		  headers: [
	          { text: 'Calories', value: 'calories' },
	          { text: 'Fat (g)', value: 'fat' },
	          { text: 'Carbs (g)', value: 'carbs' },
	          { text: 'Protein (g)', value: 'protein' },
	          { text: 'Iron (%)', value: 'iron' }
	        ],
	        desserts: [
	          {
	            value: false,
	            name: 'Frozen Yogurt',
	            calories: 159,
	            fat: 6.0,
	            carbs: 24,
	            protein: 4.0,
	            iron: '1%'
	          }
	        ]
     }),
	 methods: {
		
	 }
  }
</script>