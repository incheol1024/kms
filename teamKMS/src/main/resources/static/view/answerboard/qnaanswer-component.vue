<template>
<v-content>

  <v-container grid-list-xs align-content-center>
    <v-layout row wrap>
      <v-flex xs12>
        <v-card>
          <v-card-title>
            <h1 class="headline mb-0">Q.{{ title }}</h1>
          </v-card-title>
        </v-card>
      </v-flex>

      <v-flex xs12>
        <v-card height="200">
          <div>
            <h3 class="headline mb-0">{{ content }}</h1>
          </div>
        </v-card>
      </v-flex>
    </v-layout>

    <v-divider inset light></v-divider>

    <v-layout>
      <template v-for="answer in answers">
        <v-flex xs12>
          <v-card>
            {{ answer.cmtContents}}
          </v-card>
        </v-flex>
      </template>

    </v-layout>
  </v-container>


  <v-form>

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
      _this: this
    }
  },

  created: function() {
    console.log("id = " + this.$route.params.id);
    console.log("name = " + this.$route.params.name);
    console.log("qid = " + this.$route.params.qid);
    var _this = this;
    this.getQnabyId(_this);
  },
  methods: {
    handleFileUpload: function() {
      this.multiPartFile = this.$refs.file.files[0];
    },
    getQnabyId: function(_this) {
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
    registerAnswer: function() {
      console.log("submit button click");
      var _this = this;
      let formData = new FormData();
      formData.append('boardId', this.boardId);
      formData.append('cmtId', this.cmtId);
      formData.append('cmtContents', this.cmtContents);
      formData.append('cmtUserId', this.cmtUserId);
      formData.append('cmtDate', this.cmtDate);
      formData.append('multiPartFile', this.multiPartFile);

      axios.post('/comment/add',
          formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          }
        )
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
    registerFile: function() {
      console.log("file submit button click");
      var _this = this;
      let formData = new FormData();

      formData.append('boardId', this.boardId);
      formData.append('multiPartFile', this.multiPartFile);

      axios.post('/file/upload',
          formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          }
        )
        .then(
          function(response) {
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
