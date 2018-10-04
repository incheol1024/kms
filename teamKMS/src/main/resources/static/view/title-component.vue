<template>
<v-content>
	<v-layout fill-height column justify-center>
		<v-carousel>
    		<v-carousel-item v-for="(item,i) in items" :key="i" :src="item" contain></v-carousel-item>
    	</v-carousel>
	</v-layout>
</v-content>
</template>

<script>
  module.exports =  {
	 data: () => ({
		 items : ['../assets/kms1.png','../assets/kms2.jpg','../assets/kms3.png']
     }),
	 methods: {
		
	 }
  }
</script>