<template>
  <v-content>
  
    qna {{name}} {{id}}
  
    <!-- 
  
    <v-form>
  
      <v-layout row wrap>
  
  
  
        <v-flex xs12 sm6>
  
          <v-text-field v-model="subject"></v-text-field>
  
        </v-flex>
  
  
  
        <v-btn color="success" @click="registerQuestion"> registration </v-btn>
  
      </v-layout>
  
    </v-form> 
  
    -->
  
    <codemirror v-model="code" :options="cmOptions"></codemirror>
  
    <!-- or to manually control the datasynchronization -->
    <codemirror ref="myCm" :value="code" :options="cmOptions" @ready="onCmReady" @focus="onCmFocus" @input="onCmCodeChange">
    </codemirror>
  
    <!-- if Nust.js/SSR（如果在 Nuxt.js 环境下，需要外面包裹一层 no-ssr） -->
    <no-ssr placeholder="Codemirror Loading...">
      <codemirror ref="myCm" :value="code" :options="cmOptions" @ready="onCmReady" @focus="onCmFocus" @input="onCmCodeChange">
      </codemirror>
    </no-ssr>
</template>  


    <v-form>
 
    <form @submit.prevent="registerQuestion">
      <input type="text" name="subject" value="subject..." v-model="subject" />
      <input type="text" name="Contents" value="contents..." v-model="contents" />
      <input type="submit" value="테스트용 글쓰기" />
    </form>

  </v-form>

</v-content>
</template>

<script>
  module.exports = {
  
    props: ['id', 'name'],
  
    data() {
  
      return {
  
        subject: "subject..",
  
        contents: 'contents..'
  
      }
  
    },
  
    created: function() {
  
  
  
      // if (this.name == 'C++')
  
      //       this.cmOptions.mode = 'text/x-c++src';
  
      //     else if (this.name == 'CSharp')
  
      //       this.cmOptions.mode = 'text/x-csharp';
  
      //     else if (this.name == 'Java')
  
      //       this.cmOptions.mode = 'text/x-java';
  
      //     else if (this.name == 'Python')
  
      //       this.cmOptions.mode = 'text/x-python';
  
      //     else
  
      //       this.cmOptions.mode = 'text/javascript';
  
  
  
    },
  
    methods: {
  
      registerQuestion: function() {
  
        var _this = this;
  
        console.log("url = /qna/register/" + this.id);
  
        axios.post("/qna/register/" + this.id, {
  
            subject: this.subject,
  
            contents: "content content content",
  
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