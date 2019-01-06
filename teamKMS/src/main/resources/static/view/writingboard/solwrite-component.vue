<template>
<v-content>
	sol {{name}} {{id}} write page

  <v-container id="dropdown-example" grid-list-xl>
    <v-layout row wrap>
      <v-flex xs12 sm4>
        <p>Solution</p>
        <v-overflow-btn
            :items="Solution"
            label="Overflow Btn"
            target="#dropdown-example"
        >
        </v-overflow-btn>
      </v-flex>
      <v-flex xs12 sm4>
        <p>Site</p>
        <v-overflow-btn
          :items="Site"
          label="Overflow Btn"
          segmented
          target="#dropdown-example"
        ></v-overflow-btn>
      </v-flex>

    </v-layout>
  </v-container>

  <div class="text-xs-center">
	  <vue-ckeditor v-model="content" :config="config" />
  </div>

  <div class="text-xs-center">
    <v-btn color="success" @click="solutionpage">글쓰기</v-btn>
    <v-btn color="error"  @click="solutionpage">취소</v-btn>    
  </div> 

</v-content>

</template>

<script>
module.exports = {
  name: 'solutionwrite',
  props: ["id"],
  data: () => ({
      Solution: ['ECM', 'EDM', 'ETC'],
      Site: [
        { text: 'list', callback: () => console.log('list') },
        { text: 'favorite', callback: () => console.log('favorite') },
        { text: 'delete', callback: () => console.log('delete') }
      ]
    }),
  created: function(){
     	var _this = this;
     	this.getSolutionList(_this);
  },
  methods: {
    moveToSolutionPage: function() {
	      this.$router.push("/solutions/" + this.id);
	  },
		solutionpage : function solutionpage(){
			router.push("/solutions/" + this.id);
    }
  }
};

</script>