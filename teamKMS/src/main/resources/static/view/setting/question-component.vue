  <template>
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
  </template>

  <script>
  module.exports = {
    props: ['id', 'name', 'qid'],
    data() {
      return {
        title: "defualt title",
        content: 'default content',
        boardId: 0
      }
    },
    created: function() {
      console.log("question-component: life cycle created start");
      console.log("question-component: id = " + this.id);
      console.log("question-component: name = " + this.name);
      console.log("question-component: qid = " + this.qid);
      var _this = this;
      this.getQuestionById(_this);
      //this.getDocById(_this);
      console.log("question-component: life cycle created end");

    },
    methods: {
        getQuestionById: function(_this) {
        var that = this;
        
        axios.get("/qna/answer/" + this.qid)
          .then(
            function(response) {
              _this.title = response.data.subject;
              _this.content = response.data.contents;
            }
          )
          .catch(function(error) {
            console.log(error)
          })
      }
    }
  }
  </script>
