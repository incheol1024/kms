<template>

  <v-content>

    <v-form>
    
<v-container>
      <v-layout row wrap>

        <v-flex xs12 sm6>
          <v-text-field background-color="lime lighten-5"></v-text-field>
        </v-flex>

      </v-layout>
</v-container>

      <v-layout row wrap>

        <v-flex xs12 sm6>
          <v-textarea outline name="input-7-4" label="Outline textarea" value="The Woodman set to work at once, and so sharp was his axe that the tree was soon chopped nearly through."></v-textarea>
        </v-flex>

      </v-layout>

    </v-form>

  </v-content>

</template>

<script>
module.exports = {
  props: ['id', 'name'],
  data() {
    return {
      title: "title field",
      content: 'const A = 10',
      _this: this
    }
  },
  methods: {
    registerQuestion: function() {
      var _this = this;
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
            router.push("/qna/write" + _this.name + "/" + _this.id);

          }

        )
        .catch(function(error) {
          console.log(error);
        })

    },
    moveToQnaPage: function(_this) {
      console.log("moveToQnaPage is called");
      this.$router.push("/qna/write/" + _this.name + "/" + _this.id);
    }
  }
}
</script>
