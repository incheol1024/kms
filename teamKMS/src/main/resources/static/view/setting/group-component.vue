<template>
<v-layout column>
	<v-flex>
		<v-treeview :items="items" activatable :active.sync="active" active-class="primary--text">				
			<v-icon slot="prepend" slot-scope="{ item, active }" :color="active ? 'primary' : ''">
				mdi-account
			</v-icon>  			
		</v-treeview>
	</v-flex>
</v-layout>
</template>

<script>
module.exports = {
  created: function() {
    var _this = this;
    axios
      .post("getAllGroupList")
      .then(function(response) {
        _this.items.push(response.data);
      })
      .catch(function(error) {
        console.log(error);
      });
  },
  computed: {      
      selected () {
        if (!this.active.length) return undefined
        const id = this.active[0]
        return id;
      }
  },
  data: () => ({
    active: [],
    items: []
  }),
  methods: {
    next: function next() {
      console.log("aaa");
    }
  }
};
</script>
