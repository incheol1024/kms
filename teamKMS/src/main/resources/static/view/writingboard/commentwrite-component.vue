<template>
<div>
  <codemirror ref="myCm"
              :value="cmtContents" 
              v-model="cmtContents"
              :options="cmOptions"
              @ready="onCmReady"
              @focus="onCmFocus"
              @input="onCmCodeChange">
  </codemirror> 
  <v-btn color="info" @click.prevent="addComment">댓글 등록</v-btn>
</div>
</template>

<script>
module.exports =  {
props: ['id', 'name', 'qid'],
data () {
    return {
      cmtContents: 'const a = 10',
      cmOptions: {
        tabSize: 4,
        mode: 'text/javascript',
        theme: 'default',
        lineNumbers: true,
        line: true,
        styleActiveLine: true,
        lineWrapping: true,
        lineSeparator:"CRLF"
      }
    }
  },
  methods: {
    onCmReady(cm) {
      console.log('the editor is readied!', cm)
    },
    onCmFocus(cm) {
      console.log('the editor is focus!', cm)
    },
    onCmCodeChange(newCode) {
      console.log('this is new code', newCode)
      this.code = newCode
    },
    addComment: function() {
        if(this.fileTransactKey != null) {
          this.addCommentFile(this.fileTransactKey, this.fileCount);
          return;
        }
        
        console.log("addComment function is called");
        var that = this;

        axios.post('/comment/add', {
            boardId: Number(this.qid),
            cmtContents: this.cmtContents
          })
          .then(
            function(response) {
              // router.push("/qna/answer/" + _this.name + "/" + _this.id + "/" + response.data.boardId );
              //that.answers.push(response.data);
              console.log(response.data);
              that.emitComment(response.data);
            }
          )
          .catch(function(error) {
            console.log(error)
          })
    },
    emitComment: function(data) {
        this.$emit('emitcomment', data, '');
    }
  },
  computed: {
    codemirror() {
      return this.$refs.myCm.codemirror
    }
  },
  mounted() {
    console.log('this is current codemirror object', this.codemirror)
    // you can use this.codemirror to do something...
  }

}
</script>