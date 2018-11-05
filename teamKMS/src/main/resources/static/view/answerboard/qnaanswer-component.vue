<template>
<v-content>

  <v-form>

    <v-container>
      <v-layout row wrap>

        <v-flex xs12 sm6>
          <v-text-field background-color="lime lighten-5" :value="title"></v-text-field>
        </v-flex>

      </v-layout>
    </v-container>

    <v-layout row wrap>

      <v-flex xs12 sm6>
        <v-textarea outline name="input-7-4" label="Outline textarea" :value="content"></v-textarea>
      </v-flex>

    </v-layout>

    <form enctype="multipart/form-data" method="post" action="/image/upload">
    <input type="file" name="profilePicture" accept="image/jpeg,image/png,image/tiff,image/gif" /> </br>
    <input type="submit" />
    <v-btn color="info" @click="registerAnswer"> 등록 </v-btn>
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
    getQnabyId: function(_this) {
      axios.get("qna/answer/" + _this.$route.params.qid)
      .then(
        function(response) {
          _this.title = response.data.subject;
          _this.content = response.data.contents;
        }
      )
      .catch(function(error){
        console.log(error)
      })
    },
    registerAnswer: function() {
      console.log("submit button click");
      axios.post("image/upload/" + _this.$route.params.qid)
      .then(
        function(response) {
          _this.title = response.data.subject;
          _this.content = response.data.contents;
        }
      )
      .catch(function(error){
        console.log(error)
      })
    }
  }

}
</script>
