<template>
<v-content>
	qna {{name }} {{id}}
	<v-btn color="primary" @click="writepage">Write</v-btn> 
	
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
</v-content>
</template>

<script>
  module.exports =  {
	 props : ['name','id'],
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
		 writepage : function writepage(){
		 	router.push("/qna/write/" +this.name + "/" + this.id);
		 }
	 }
  }
</script>