  <template>
  <div>
      
<v-layout row wrap>
  <v-flex xs12>    
    
    <ckeditor :editor="editor" 
              v-model="cmtContents" 
              :config="editorConfig" 
              @ready="onEditorReady"
    ></ckeditor>
  </v-flex>
  <v-flex xs12>    
    <codemirror ref="myCm"
                :value="cmtCode" 
                v-model="cmtCode"
                :options="cmOptions"
                @ready="onCmReady"
                @focus="onCmFocus"
                @input="onCmCodeChange">
    </codemirror> 
  </v-flex>    
    <v-flex xs12 class="text-xs-center text-sm-center text-md-center text-lg-center">
          <template v-for="doc in docs" >
            <img :src="doc.fileUrl" height="100" v-if="doc.fileName" :key="doc.fileName" />
          </template>
            <v-text-field label="Select Image" @click='pickFile' v-model='imageName' prepend-icon='attach_file' ></v-text-field>
            <input
              type="file"
              style="display: none"
              ref="image"
              accept="image/*"
              @change="onFilePickedCustom"
              multiple
            >        
    </v-flex>
    <v-btn color="info" @click.prevent="addCommentAndFile">댓글 등록</v-btn>
</v-layout>
    
    
  </div>
  </template>

  <script>
  module.exports =  {
  props: ['id', 'name', 'qid'],
  data () {
      return {
          editor: ClassicEditor,
          cmtContents: '<p>댓글을 입력하세요~^^..!!</p>',
          editorConfig: {
          },
          cmtCode: 'const a = 10',
          cmOptions: {
          tabSize: 4,
          mode: 'text/javascript',
          theme: 'monokai',
          lineNumbers: true,
          line: true,
          styleActiveLine: true,
          lineWrapping: true,
          lineSeparator:"</br>"
          },
          title: "Image Upload",
          dialog: false,
          imageName: '',
          imageUrl: '',
          imageFile: '',
          fileTransactKey: null,
          fileCount:0,
          docs: []  
      }
    },
  created: function() {
   // this.setLangMode();
  },
  watch: {
      mode: function(){
          if(4 === Number(this.id)) {
            this.cmOptions.mode = 'text/x-java';
          } else if(5 === Number(this.id)) {
            this.cmOptions.mode = 'text/x-c++src';
          } else if(6 === Number(this.id)) {
            this.cmOptions.mode = 'text/x-python';
          } else if(7 === Number(this.id)) {
            this.cmOptions.mode = 'text/x-csharp';
          } else if(8 === Number(this.id)) {
            this.cmOptions.mode = 'text/html';
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
      emitComment: function(data) {
          this.$emit('emitcomment', data, '');
      },
      setLangMode: function() {
          console.log("setLangMode is called");
          console.log("this.id =" + this.id);
          console.log( 5 === Number(this.id));
          if(4 === Number(this.id)) {
            this.cmOptions.mode = 'text/x-java';
          } else if(5 === Number(this.id)) {
            this.cmOptions.mode = 'text/x-c++src';
          } else if(6 === Number(this.id)) {
            this.mode = 'text/x-python';
          } else if(7 === Number(this.id)) {
            this.mode = 'text/x-csharp';
          } else if(8 === Number(this.id)) {
            this.mode = 'text/html';
          }
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
      pickFile: function() {
              this.$refs.image.click();
          },	
      onFilePicked: function(e) {
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
      onFilePickedCustom: function(e) {
      const files = e.target.files
      
          if(files.length > 0) {
                  //const fr = new FileReader();
                for(var i = 0; i < files.length; i++) {
                  var frArr = [];
                  var fr = new FileReader();
                  
                  fr.readAsDataURL(files[i]);
                  var fileName = files[i].name;
                  var fileUrl = fr.result;

                  var doc = {
                    fileName: fileName,
                    fileUrl: fileUrl,
                    fileData: files[i]
                  };

                  console.dir(doc);
                  this.docs.push(doc);

                  // fr.addEventListener('load', function(){
                    
                  //   console.log("i = " , i);
                  //   console.log(files[0]);

                  //   var fileName = files[i].name;
                  //   fr.readAsDataURL(files[i]);
                  //   var fileUrl = fr[i].result;
                  //   console.log(fileUrl);
    

                  //   var fileData = files[i];
                  //   var file = {
                  //     fileName: fileName,
                  //     fileUrl: fileUrl,
                  //     fileData: fileData
                  //   }
                  //   this.docs.push(file);
                  
                  // });

                }
              
          } else {
              this.docs = [];
          }
          
      },
      addComment: function() {
          // if(this.fileTransactKey != null) {
          //   this.addCommentFile(this.fileTransactKey, this.fileCount);
          //   return;
          // }
          console.log("addComment function is called");

          // if(this.docs.length > 0 ){
          //   console.log('docs length > 0');
          //   this.addFile();
          // }
          
          var that = this;

          var url = "/comment/add";
          if(this.fileTransactKey !== undefined && this.fileCount > 0) {
            url = "/comment/add/files"
          }
          
          console.log('defined url ' + url);
          axios.post(url, {
              boardId: Number(this.qid),
              cmtContents: this.cmtContents,
              cmtCode: this.cmtCode,
              fileTransactKey: this.fileTransactKey,
              fileCount: this.fileCount
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
      addCommentAndFile: function() {

          if(this.docs.length < 1) {
            this.addComment();
            return;
          }

          var that = this;
          let formData = new FormData(); 

          formData.append('boardId', this.qid);
          //formData.append('cmtId', this.cmtId);
          for (var i = 0; i < this.docs.length; i++) {
            let file = this.docs[i].fileData;
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
              //   console.log(response.data.fileTransactKey);
              //   console.log(response.data.fileCount);
                that.fileTransactKey = response.data.fileTransactKey;
                that.fileCount = response.data.fileCount;
                console.log("that.fileTransactKey = " + that.fileTransactKey);
                console.log("that.fileCount = " + that.fileCount);
              // 
              //this.fileCallBack(reresponse);
              }
            )
            .then(res => {
                that.addComment();
            })
            .catch(function(error) {
              console.log(error)
            })
      },
      fileCallBack: function(response) {
        this.fileTransactKey = response.data.fileTransactKey;
        this.fileCount = response.data.fileCount;

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
              console.log(error);
            })
      },
      onEditorReady: function(editor) {
        
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
  <style>
   .ck-editor__editable {
    min-height: 300px;
   } 
  </style>
    