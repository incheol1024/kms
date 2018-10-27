<template>
<v-content>
  qna {{name}} {{id}}

  <v-form>
    <v-layout row wrap>

      <v-flex xs12 sm6>
        <v-text-field v-model="title"></v-text-field>
      </v-flex>

      <v-btn color="success" @click="registerQuestion"> registration </v-btn>
    </v-layout>
  </v-form>



  <codemirror v-model="content"></codemirror>
</v-content>
</template>

<script>
module.exports = {
  props: ['id', 'name'],
  data() {
    return {
      title: "title field",
      content: 'const A = 10',

    }
  },
  created: function() {
    if (this.name == 'C++')
      this.cmOptions.mode = 'text/x-c++src';
    else if (this.name == 'CSharp')
      this.cmOptions.mode = 'text/x-csharp';
    else if (this.name == 'Java')
      this.cmOptions.mode = 'text/x-java';
    else if (this.name == 'Python')
      this.cmOptions.mode = 'text/x-python';
    else
      this.cmOptions.mode = 'text/javascript';
  },
  methods: {
    registerQuestion: function() {
      axios.post("/qna/register", {
          id: "",
          title: this.title,
          content: "content content content",
          userName: "",
          replyCount: "0",
          viewCount: "0"
        })
        .then(
          function(response) {
            console.log("registerQuestion is called", response);

            console.log("before router push");
            router.push("/");

          }

        )
        .catch(function(error) {
          console.log(error);
        })

    },
    moveToQnaPage: function() {
      console.log("moveToQnaPage is called");
      this.$router.push("/qna/write/" + this.name + "/" + this.id);
    }
  }
}
</script>