<template>
<v-content>
	solution Detail Page test

  <v-container id="solution_write" grid-list-xl>
    <v-layout row wrap>
      <v-flex xs12 sm6 md3>
          <v-text-field
            label="title"
            placeholder="title"
          ></v-text-field>
      </v-flex>
      <v-flex xs12 sm4>
        <p>Solution</p>
        <v-overflow-btn
            :items="Solution"
            label="Solution"
            target="solution_write"
        ></v-overflow-btn>
      </v-flex>
      <v-flex xs12 sm4>
        <p>Site</p>
        <v-overflow-btn
          :items="Site"
          label="Site"
          target="solution_write"
        ></v-overflow-btn>
      </v-flex>
        <v-flex xs12 sm2>
        <select v-model="selected">
          <option disabled value="">select</option>
          <option>ECM</option>
          <option>EDM</option>
          <option>ETC</option>
        </select>
      </v-flex>
      <v-flex xs12 sm2>
        <select v-model="selected2">
          <option disabled value="">select</option>
          <option>st1</option>
          <option>st2</option>
          <option>st3</option>
        </select>
      </v-flex>
    </v-layout>
  </v-container>

  <div class="text-xs-center">
	  <vue-ckeditor v-model="content" :config="config" />
  </div>

  <div class="text-xs-center">
    <v-btn color="success" @click="try_write">글쓰기</v-btn>
    <v-btn color="error"   @click="cancel_write">취소</v-btn>    
  </div> 

</v-content>

</template>

<script>
module.exports = {
  name: 'solution_write',
  props: ["id"],
  data: () => ({
      Solution: ['ECM', 'EDM', 'ETC'],
      Site: [ 'site1', 'site2', 'site3'],
      selected: '',
      selected2: ''
    }),
  created: function(){
      console.log("id = " + this.$route.params.id);
     	var _this = this;
  },
  methods: {
    try_write: function() {
      //this.$router.push("/solutions/" + this.id);
      axios.post('/write/register',{
        
      })
      .then(
        function(response){
          _this.answers.push(response.data);
          console.log(response.data);
        }
      )
      .catch(function(error){
        console.log("[ERR] : " + error)
      })
	  },
		cancel_write : function(){
			router.push("/solutions/" + this.id);
    }
  }
};

</script>