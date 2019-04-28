  <template>

    <v-container grid-list-xs align-content-center>

      <commentwrite-component :id="id" :name="name" :qid="qid"
      @emitcomment="renderComment"></commentwrite-component>

      <template v-for="(answer, index) in answers">
        <!-- <v-layout justify-center row mb-4 :key="index"> -->
            
        <v-layout row wrap justify-space-around :key="index">
          <v-flex xs12>
            <v-card>
                <v-card-title>
                  <v-avatar color="grey lighten-4">
                  </v-avatar>
                  {{ answer.cmtUserId }} - {{ answer.cmtDate }} 
                  <v-spacer></v-spacer>
                  <v-btn flat icon>
                    <v-icon>build</v-icon>
                  </v-btn>
                  <v-btn flat icon @click="deleteComment(answer.cmtId, index)">
                    <v-icon>delete</v-icon>
                  </v-btn>
                </v-card-title>
                   
             <v-divider></v-divider>
    

              <v-card-actions>
                  <div v-html="answer.cmtContents"></div>
              </v-card-actions>
              
                
<!-- 
                  <ckeditor :editor="editor" 
                             v-model="answer.cmtContents" 
                             :config="editorConfig" 
                             :disabled="editorDisabled"
                             @ready="onCkViewReady"
                  ></ckeditor>
                   -->
               
                
                 <codemirror ref="myCm"
                 :value="answer.cmtCode" 
                 :options="cmOptions"
                 v-if="isExistData(answer.cmtCode)"
                 >
                 </codemirror> 
                

  
            <v-divider></v-divider>

              <v-card-actions v-if="isExistData(answer.docId)">
                  <div>   	
                    <!-- <template v-for="(doc, index) in docs"> -->
                      <v-chip close color="orange" label outline :key="index" @click="fileDownload(answer.docId, answer.docName)">
                        {{answer.docName}}
                      </v-chip>
                    <!-- </template>                                             -->
                  </div>
              </v-card-actions>
              <v-card-actions>
                <v-btn flat icon color="blue lighten-2" @click.prevent="updateLike(answer.cmtId, index)">
                  <v-icon>thumb_up</v-icon>                  
                </v-btn>
                <span class="subheading mr-2">{{answer.cmtLike}}</span>
              </v-card-actions>   
          </v-card>
         </v-flex>
       </v-layout>
      </template>



    <v-form>
      <!--
      <form enctype="multipart/form-data" method="post" action="/comment/add" @submit.prevent="registerFile">
        <input type="text" name="boardId" value="1" v-model="boardId" />
        <input type="text" name="cmtId" value="1" v-model="cmtId" />
        <input type="text" name="cmtContents" value="content test" v-model="cmtContents" />
        <input type="text" name="cmtUserId" v-model="cmtDate" />
        <input type="text" name="cmtDate" v-model="cmtDate" />
        <input type="file" name="multiPartFile" ref="file" v-on:change="handleFileUpload" /> </br>
        <input type="submit" />
        <button> send </button>
      </form>
  -->

      <form @submit.prevent="addComment">
        <input type="text" name="boardId" :value="qid" />
        <input type="text" name="cmtContents" value="content test" v-model="cmtContents" />
        <input type="submit" value="댓글 등록 테스트" />
      </form>
 

    </v-form>
    </v-container>
  </template>

  <script>
  module.exports = {
    props: ['id', 'name', 'qid'],
    data() {
      return {        
        cmtId: 1,
        editor: ClassicEditor,
        editorDisabled: false,
        editorConfig: {
        },
        cmtContents: "default comment",
        cmOptions: {
          tabSize: 4,
          mode: 'text/javascript',
          theme: 'monokai',
          lineNumbers: true,
          line: true,
          styleActiveLine: true,
          lineWrapping: true,
          lineSeparator:"</br>",
          readOnly: 'nocursor'
        },
        cmtUserId: "",
        cmtDate: "",
        multiPartFile: [],
        answers: [],
        docs: [],
        _this: this
      }
    },

    created: function() {
      console.log("id = " + this.$route.params.id);
      console.log("name = " + this.$route.params.name);
      console.log("qid = " + this.$route.params.qid);
      this.getCommentById();
      //this.getDocById(_this);
    },

    methods: {

      getCommentById: function() {
        var that = this;
        console.log("getCommentById URL : /comment/list/" + this.qid );
        axios.get("/comment/list/" + this.qid)
          .then(
            function(response) {
              for (var i = 0; i < response.data.length; i++) {
                console.log(response. data[i]);
                that.answers.push(response.data[i]);
              }
            }
          )
          .catch(function(error) {
            console.log(error)
          })
      },
      getDocById: function() {
        var that = this;
        axios.get("/comment/" + this.qid)
          .then(
            function(response) {
              for (var i = 0; i < response.data.length; i++) {
                console.log(response.data[i]);
                that.docs.push(response.data[i]);
              }
            }
          )
          .catch(function(error) {
            console.log(error)
          })
      },
      updateComment: function() {
        console.log("updateComment function is called");
        axios.post('/comment/update', {
            boardId: this.boardId,
            cmtId: this.cmtId,
            cmtContents: this.cmtContents
          })
          .then(
            function(response) {
              // router.push("/qna/answer/" + _this.name + "/" + _this.id + "/" + response.data.boardId );

              _this.answers.push(response.data);

              console.log(response.data);
            }
          )
          .catch(function(error) {
            console.log(error)
          })
      },
      deleteComment: function(cmtId, index) {
        if (confirm('답글을 삭제 하시겠습니까?')) {
          console.log("deleteComment function is called" + "cmtId = " + cmtId);
          var _this = this;
          axios.delete('/comment/delete', {
              params: {
                cmtId: cmtId
              }
            })
            .then(
              function(response) {
                console.log(response.data);
                _this.answers.splice(index, 1);
              }
            )
            .catch(function(error) {
              console.log(error)
            })
        } else {
          // Do nothing!
        }
      },
      updateLike: function(cmtId, index) {
        var that = this;
        console.log("updateLike function is called");
        axios.post('/comment/like/' + cmtId)
          .then(
            function(response) {
              that.answers[index].cmtLike = response.data.cmtLike; 
              //that.answers.push(response.data);
              console.log(response.data);
            }
          )
          .catch(function(error) {
            console.log(error)
          })
      },
      updateUnLike: function() {
        console.log("updateUnLike function is called");
        axios.post('/comment/unlike', {
            cmtId: this.cmtId
          })
          .then(
            function(response) {
              // router.push("/qna/answer/" + _this.name + "/" + _this.id + "/" + response.data.boardId );

              _this.answers.push(response.data);

              console.log(response.data);
            }
          )
          .catch(function(error) {
            console.log(error)
          })
      },
      fileDownload: function (docId, docName) {
        console.log("fileDownload function is called");
        console.log("file download url" + " file/download/" + docId);
        axios.get('/file/download/' + docId, 
          {
            docId: docId
          },  
          {
            headers: {
               responseType: 'blob'
            }
          })
          .then(
            function(response) {
                 const url = window.URL.createObjectURL(new Blob([response.data]));
                 const link = document.createElement('a');
                 link.href = url;
                 link.setAttribute('download', docName); //or any other extension
                 document.body.appendChild(link);
                 link.click();
            }
          )
          .catch(function(error) {
            console.log(error)
          })
      },
      renderComment: function (data) {
        this.answers.push(data);
      },
      isExistData: function(data) {
        if(data === undefined || data === null || data === '0' || 0 === Number(data)) {
          return false;
        }
        else {
          return true;
        }
        
      },
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
      onCkViewReady(editor) {
        console.log('this is ckeditor view ready');
      }
        
    }
  }
  </script>
