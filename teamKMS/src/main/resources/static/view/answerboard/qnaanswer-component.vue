<template>
<v-content>

  <v-container grid-list-xs align-content-center>
    <v-layout row wrap mb-5>
      <v-flex xs12>
        <v-card>
          <v-card-title>
            <div>
              <h1 class="headline mb-0">Q.{{ title }}</h1>
            </div>
          </v-card-title>
          <v-divider inset light></v-divider>
          <v-card-text>
            <div>
              <h2>{{ content }}</h2>
            </div>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn icon>
              <v-icon>favorite</v-icon>
            </v-btn>
            <v-btn icon>
              <v-icon>bookmark</v-icon>
            </v-btn>
            <v-btn icon>
              <v-icon>share</v-icon>
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-flex>
    </v-layout>

    <template v-for="(answer, index) in answers">
      <v-layout justify-center row mb-4>

        <v-flex xs10>
          <v-card>
            <div>
              <v-card-title>

                <v-avatar color="grey lighten-4">

                </v-avatar>

                {{ answer.cmtUserId }} - {{ answer.cmtDate }}

                <v-spacer></v-spacer>
                <v-btn flat icon @click="changeCommentStatus(answer.cmtId, index)">
                  <v-icon>build</v-icon>
                </v-btn>
                <v-btn flat icon @click="deleteComment(answer.cmtId, index)">
                  <v-icon>delete</v-icon>
                </v-btn>

                <v-card-title>
            </div>
            <v-divider inset light></v-divider>
            <v-card-text>
              <div>
                <span>{{ answer.cmtContents}}</span>
              </div>
            </v-card-text>
            <v-card-actions>
              <v-btn flat icon color="blue lighten-2" @click="updateLike(answer.cmtId)">
                <v-icon>thumb_up</v-icon>
              </v-btn>
              <v-btn flat icon color="red lighten-2">
                <v-icon>thumb_down</v-icon>
              </v-btn>
              <v-spacer></v-spacer>
              <template v-if="updateShow" >
              <v-btn color="success">수정하기</v-btn>
              <v-btn color="warning">취소하기</v-btn>
              </template>
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
      <input type="text" name="boardId" value="1" v-model="boardId" />
      <input type="text" name="cmtContents" value="content test" v-model="cmtContents" />
      <input type="submit" value="댓글 등록 테스트" />
    </form>

      <form @submit.prevent="addFile">
        <input type="file" id="files" ref="files" multiple @change="handleFileUpload()" />
        <!-- <input type="submit" value="파일 등록 테스트" /> -->
        <v-btn @click="addFile"> 파일등록테스트</v-btn>
      </form>


  </v-form>

</v-content>
</template>

<script>
module.exports = {
  props: ['id', 'name'],
  data() {
    return {
      title: "title binding",
      content: 'content binding',
      boardId: 1,
      cmtId: 1,
      cmtContents: "답변 내용을 입력하세요.",
      cmtUserId: "",
      cmtDate: "",
      multiPartFile: [],
      answers: [],
      updateShow: [],
      _this: this
    }
  },

  created: function() {
    console.log("id = " + this.$route.params.id);
    console.log("name = " + this.$route.params.name);
    console.log("qid = " + this.$route.params.qid);
    var _this = this;
    this.getQuestionById(_this);
    this.getCommentById(_this);
  //  this.setShowValue();
  },
  methods: {
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
    changeCommentStatus: function(cmtId, index) {
      console.log("changeCommentStatus is called cmtId =  " + cmtId + ", index = " + index);

      this.updateShow = !this.updateShow

    },
    setShowValue: function(cmtId, index) {
      console.log("changeCommentStatus is called cmtId =  " + cmtId + ", index = " + index);

      this.updateShow = !this.updateShow

    },
    getQuestionById: function(_this) {
      axios.get("qna/answer/" + _this.$route.params.qid)
        .then(
          function(response) {
            _this.title = response.data.subject;
            _this.content = response.data.contents;
          }
        )
        .catch(function(error) {
          console.log(error)
        })
    },
    getCommentById: function(_this) {
      axios.get("comment/" + _this.$route.params.qid)
        .then(
          function(response) {
            for (var i = 0; i < response.data.length; i++) {
              console.log(response.data[i]);
              _this.answers.push(response.data[i]);
            }
          }
        )
        .catch(function(error) {
          console.log(error)
        })
    },
    addComment: function() {
      console.log("addComment function is called");
      var _this = this;
      let formData = new FormData();
      formData.append('boardId', this.boardId);
      formData.append('cmtContents', this.cmtContents);

      axios.post('/comment/add', {
          boardId: this.boardId,
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
    updateComment: function(cmtId, index) {
      console.log("updateComment function is called cmtId = " + cmtId + "index = " + index);
      axios.post('/comment/update', {
          boardId: this.boardId,
          cmtContents: this.cmtId
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
      formData.append('cmtId', this.cmtId);
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
            console.log(response.data);
          }
        )
        .catch(function(error) {
          console.log(error)
        })
    },
    updateLike: function(cmtId) {
      console.log("updateLike function is called");
      var _this = this;
      axios.post('/comment/' + cmtId + '/like/')
        .then(
          function(response) {
            _this.answers.push(response.data);
            console.log(response.data);
          }
        )
        .catch(function(error) {
          console.log(error)
        })
    },
    updateUnLike: function() {
      console.log("updateUnLike function is called");
      axios.post('/comment/' + cmtId + '/unlike', {
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
    }

  }
}
</script>
