  <template>
  <v-content>

    <v-container grid-list-xs align-content-center>

      <commentwrite-component :id="id" :name="name" :qid="qid"
      @emitcomment="renderComment"></commentwrite-component>

      <template v-for="(answer, index) in answers">
        <v-layout justify-center row mb-4 :key="index">
          <v-flex xs10>
            <v-card>
              <div>
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
              </div>

              <v-divider></v-divider>
              
              <v-card-text>
                <div>
                  <span>{{ answer.cmtContents}}</span>
                </div>               
              </v-card-text>

            <v-divider></v-divider>
              <v-card-actions>
                  <div> 
                    <!-- <template v-for="(doc, index) in docs"> -->
                      <v-chip close color="orange" label outline :key="index" @click="fileDownload(doc.docId)">
                        파일 이름
                      </v-chip>
                    <!-- </template>                                             -->
                  </div>
              </v-card-actions>

              <v-card-actions>
                <v-btn flat icon color="blue lighten-2" @click.prevent="updateLike(answer.cmtId, index)">
                  <v-icon>thumb_up</v-icon>                  
                </v-btn>
                <span class="subheading mr-2">{{answer.cmtLike}}</span>
                <v-btn icon>
                <v-icon>share</v-icon>
                </v-btn>
<!-- 
                <v-btn flat icon color="red lighten-2">
                  <v-icon>thumb_down</v-icon>
                </v-btn>
-->
              </v-card-actions>
            </v-card>
          </v-flex>
        </v-layout>
      </template>
    </v-container>


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

  </v-content>
  </template>

  <script>
  module.exports = {
    props: ['id', 'name', 'qid'],
    data() {
      return {        
        cmtId: 1,
        cmtContents: "default comment",
        cmtUserId: "",
        cmtDate: "",
        multiPartFile: [],
        answers: [],
        docs: [],
        fileTransactKey: null,
        fileCount:0,
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
                console.log(response.data[i]);
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
      addComment: function() {
        if(this.fileTransactKey != null) {
          this.addCommentFile(this.fileTransactKey, this.fileCount);
          return;
        }
        
        console.log("addComment function is called");
        var that = this;

        axios.post('/comment/add', {
            boardId: Number(this.$route.params.qid),
            cmtContents: this.cmtContents
          })
          .then(
            function(response) {
              // router.push("/qna/answer/" + _this.name + "/" + _this.id + "/" + response.data.boardId );
              that.answers.push(response.data);
              console.log(response.data);
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
            "commentDto": {
              "cmtContents": this.cmtContents
            },
            "fileTranscationDto": {
              "fileTransactKey": fileTransactKey,
              "fileCount": fileCount
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
      addFile: function() {
        console.log("file submit button click" + "boarId: " + this.boardId + ", cmtId: " + this.cmtId + " , multipartFile: " + this.multiPartFile);
        var _this = this;
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
      fileDownload: function (docId) {
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
      renderComment: function (data) {
        this.answers.push(data);
      }
    }
  }
  </script>
