<template>
<div>
    
  <v-container grid-list-md text-xs-center>
    <v-layout row wrap>
      <v-flex xs12>    
	
  <codemirror ref="myCm"
              :value="cmtContents" 
              v-model="cmtContents"
              :options="cmOptions"
              @ready="onCmReady"
              @focus="onCmFocus"
              @input="onCmCodeChange">
  </codemirror> 
<!--   
  <form @submit.prevent="addFile">
        <input type="file" id="files" ref="files" multiple @change="handleFileUpload()" />
        <input type="submit" value="파일 등록 테스트" />
        <v-btn @click="addFile"> 파일등록테스트</v-btn>
  </form> 
-->
			<v-flex xs12 class="text-xs-center text-sm-center text-md-center text-lg-center">
					<img :src="imageUrl" height="100" v-if="imageUrl"/>
					<v-text-field label="Select Image" @click='pickFile' v-model='imageName' prepend-icon='attach_file'></v-text-field>
					<input
						type="file"
						style="display: none"
						ref="image"
						accept="image/*"
						@change="onFilePicked"
                        multiple
					>
				</v-flex>k
            <v-btn color="info" @click.prevent="addComment">댓글 등록</v-btn>


      </v-flex>
    </v-layout>
  </v-container>
  
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
        lineSeparator:"\n"
      },
      title: "Image Upload",
        dialog: false,
		imageName: '',
		imageUrl: '',
		imageFile: ''
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
    },
    handleFileUpload: function() {
        console.log("handleFileUpload is called : ");
        //this.multiPartFile = this.$refs.files.files;

        let uploadedFiles = this.$refs.files.files;
        for (var i = 0; i < uploadedFiles.length; i++) {
          this.multiPartFile.push(uploadedFiles[i]);
          console.log("uploadedFiles : " + uploadedFiles[i]);
          console.log("multiPartFile : " + this.multiPartFile[i]);
        }
        console.log("this.multiPartFile length: " + this.multiPartFile.length);

        for (var i = 0; i < this.multiPartFile.length; i++) {
          console.log(this.multiPartFile[i]);
        }
      },
       pickFile () {
            this.$refs.image.click ()
        },
		
		onFilePicked (e) {
			const files = e.target.files
			if(files[0] !== undefined) {
				this.imageName = files[0].name
				if(this.imageName.lastIndexOf('.') <= 0) {
					return
				}
				const fr = new FileReader ()
				fr.readAsDataURL(files[0])
				fr.addEventListener('load', () => {
					this.imageUrl = fr.result
                    this.imageFile = files[0] // this is an image file that can be sent to server...
				})
			} else {
				this.imageName = ''
				this.imageFile = ''
				this.imageUrl = ''
			}
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