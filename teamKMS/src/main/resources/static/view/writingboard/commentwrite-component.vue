<template>
<div>
    
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
        <template v-for="(doc, index) in docs" >
					<img :src="doc.imageName" height="100" v-if="index" :key="index"/>
					<v-text-field label="Select Image" @click='pickFile' v-model='imageName' prepend-icon='attach_file' :key="index"></v-text-field>
					<input
						type="file"
						style="display: none"
						ref="image"
						accept="image/*"
						@change="onFilePickedCustom"
                        multiple
                        :key="index"
					>
        </template>
				</v-flex>
            <v-btn color="	info" @click.prevent="addComment">댓글 등록</v-btn>
      </v-flex>
    </v-layout>
  
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
        imageFile: '',
        fileTransactKey: null,
        fileCount:0,
        docs: [
            {fileName: '', fileUrl: '', fileData: ''}
        ]  
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
            this.$refs.image.click();
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
    },
    onFilePickedCustom (e) {
		const files = e.target.files
        if(files[0] !== undefined) {
            var i = 0;
            for( i = 0; i < files.length; i++) {

                const fr = new FileReader();
                fr.readAsDataURL(files[i]);
                
                this.docs[i].fileName = files[i].name;
                this.docs[i].fileUrl = fr.result;
                this.docs[i].fileData = files[i];
            };
        } else {
            this.docs = [];
        }
        
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
    addFile: function() {
        var that = this;
        let formData = new FormData(); 

        formData.append('boardId', this.boardId);
        //formData.append('cmtId', this.cmtId);
        for (var i = 0; i < this.multiPartFile.length; i++) {
          let file = this.multiPartFile[i];
          formData.append('multiPartFile', file);
        }
        axios.post('/file/upload/comment',
            formData, {
              headers: {
                'Content-Type': 'multipart/form-data'
              }
            }
          )
          .then(
            function(response) {
              //            _this.answers.push(response.data);
              console.log(response.data.fileTransactKey);
              console.log(response.data.fileCount);
              _this.fileTransactKey = response.data.fileTransactKey;
              _this.fileCount = response.data.fileCount;
            }
          )
          .catch(function(error) {
            console.log(error)
          })
    },
    addCommentFile: function(fileTransactKey, fileCount) {
        console.log("addComment function is called and fileTransactKey = " + fileTransactKey + " and fileCount = " + fileCount);
        var that = this;

        axios.post('/comment/add/files/' + this.$route.params.qid, {
          comFileDto: {
            commentDto: {
              cmtContents: this.cmtContents
            },
            fileTranscationDto: {
              fileTransactKey: this.fileTransactKey,
              fileCount: fileCount
            }
            }
                    })
          .then(
            function(response) {
              // router.push("/qna/answer/" + _this.name + "/" + _this.id + "/" + response.data.boardId );
              //_this.answers.push(response.data);
              console.log(response.data);
            }
          )
          .catch(function(error) {
            console.log(error)
          })
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