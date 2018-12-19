<template>
<v-content>
	sol {{id}}
  <v-card-title>
	<v-btn color="primary"  @click="writepage">Write</v-btn> 
	<v-spacer></v-spacer>
	<v-text-field v-model="search" append-icon="search" label="검색" single-line hide-details></v-text-field>
  </v-card-title>
  <v-data-table :headers="headers" :items="solutions" :search="search" class="elevation-1">
	<template slot="headerCell" slot-scope="props">
      <v-tooltip bottom>
        <span slot="activator">
          {{ props.header.text }}
        </span>
        <span>
          {{ props.header.text }}
        </span>
      </v-tooltip>
    </template>
    <template slot="items" slot-scope="props">
      <td class="text-xs-right">{{ props.item.boardId }}</td>
      <td class="text-xs-right">{{ props.item.subject }}</td>
      <td class="text-xs-right">{{ props.item.userId }}</td>
      <td class="text-xs-right">{{ props.item.hits }}</td>
      <td class="text-xs-right">{{ props.item.regDate }}</td>
    </template>
    <v-alert slot="no-results" :value="true" color="error" icon="warning">
      "{{ search }}"에 대한 결과를 찾을 수 없습니다.
    </v-alert>
  </v-data-table>
</v-content>
</template>

<script>
  module.exports =  {
  name: 'solution',
  	 props : ['id', 'name'],
	 data: () => ({
	 	search: '',
		 headers: [
	          { text: '번호', value: 'id' },
	          { text: '제목', value: 'title' },
	          { text: '작성자', value: 'userName' },
	          { text: '답변수', value: 'replyCount' },
	          { text: '등록일자', value: 'regDate' }
	        ],
	        solutions:[]
     }),
     created: function(){
     	var _this = this;
     	this.getSolutionList(_this);
     },
	 methods: {
	 	getSolutionList: function(_this){
	 		axios.get("solution")
	 		.then(
	 			function(response){
	 				for ( var i = 0; i < response.data.length; i++){
	 					_this.solutions.push(response,data[i]);
	 				}
	 			}	
	 		)
	 		.catch(function(error){
	 			console.log(error);
	 		})
	 	},
	 	moveToWritePage: function() {
	      this.$router.push("/solutions/write/" + this.id);
	    },
		 writepage : function writepage(){
		 	router.push("/solutions/write/" + this.id);
		 }
	 }
  };
</script>