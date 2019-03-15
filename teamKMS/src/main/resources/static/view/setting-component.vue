<template>
	<v-card flat>
      <router-view></router-view>
      <v-bottom-nav :active.sync="bottomNav" :value="true" shift :color="color">
      
        <v-btn dark @click="moveUser">
          <span>User</span>
          <v-icon>history</v-icon>
        </v-btn>

        <v-btn dark @click="moveGroup">
          <span>Group</span>
          <v-icon>favorite</v-icon>
        </v-btn>

        <v-btn dark @click="moveACL">
          <span>Permission</span>
          <v-icon>visibility</v-icon>
        </v-btn>

      </v-bottom-nav>
    </v-card>
</template>

<script>
  module.exports =  {
	 props : ['name','id'],
	 data: () => ({
	 	bottomNav: 0
     }),
	 methods: {
		moveUser : function moveUser() {
			router.push('/setting/user');
		},
		moveGroup : function moveGroup() {
			router.push('/setting/group');		
		},
		moveACL : function moveACL() {
			router.push('/setting/permission');
		},
	 },
	 mounted: function () {
	    router.push('/setting/user');
  	 },
	 computed: {
      color () {
        switch (this.bottomNav) {
          case 0: return 'blue-grey'
          case 1: return 'teal'
          case 2: return 'brown'
          case 3: return 'blue'
        }
      }
    }
  }
</script>