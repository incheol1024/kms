<template>
<v-content>
	qna {{id}}
	<codemirror v-model="code" :options="cmOptions"></codemirror>
</v-content>
</template>

<script>
//text/x-c++src
//text/x-csharp
//text/x-java
//text/javascript
//text/x-python
  module.exports =  {
	 props : ['id'],
	data () {
    return {
      code: 'const a = 10',
      cmOptions: {
        tabSize: 4,
        mode: 'text/x-java',
        lineNumbers: true,
        line: true
      }
    }
  },
	 methods: {
		 
	 }
  }
</script>