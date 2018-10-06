<template>
<v-content>
	qna {{name}} {{id}}
	<codemirror v-model="code" :options="cmOptions"></codemirror>
</v-content>
</template>

<script>
  module.exports =  {
	props : ['id','name'],
	data () {
	    return {
	      code: '',
	      cmOptions: {
	        tabSize: 4,
	        mode: '',
	        lineNumbers: true,
	        line: true
	      }
	    }
    },
     created: function () {
    	console.log(this.name);
		 	if(this.name == 'C++')
		 		this.cmOptions.mode = 'text/x-c++src';
		 	else if(this.name == 'CSharp')
		 		this.cmOptions.mode =  'text/x-csharp';
		 	else if(this.name == 'Java')
		 		this.cmOptions.mode =  'text/x-java';
		 	else if(this.name == 'Python')
		 		this.cmOptions.mode =  'text/x-python';
		 	else
		 		this.cmOptions.mode =  'text/javascript';
  	},
	 methods: {
		
	 }
  }
</script>