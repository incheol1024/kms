<template>
<v-content>
  <v-card-title>
     <v-btn slot="activator" color="primary" dark class="mb-2" @click="moveToWritePage"> 글쓰기 </v-btn>
    <v-spacer></v-spacer>
    <v-text-field v-model="search" append-icon="search" label="검색" single-line hide-details></v-text-field>
  </v-card-title>
  <v-data-table :headers="headers" :items="questions" :search="search">
    <template slot="headerCell" slot-scope="props">
      <v-tooltip bottom>
        <span slot="activator">
          {{ props.header.text }}
        </span>
        <span>
          {{ props.header.text }}
        </span>
      </v-tooltip>
    </template>
    <template slot="items" slot-scope="props">
      <td class="text-xs-left">{{ props.item.viewCount }}</td>
      <td class="text-xs-left">{{ props.item.title }}</td>
      <td class="text-xs-center">{{ props.item.userName }}</td>
      <td class="text-xs-center">{{ props.item.replyCount }}</td>
    </template>
    <v-alert slot="no-results" :value="true" color="error" icon="warning">
      "{{ search }}"에 대한 결과를 찾을 수 없습니다.
    </v-alert>
  </v-data-table>
</v-content>
</template>

<script>
module.exports = {
  name: 'qna',
  data: () => ({
    search: '',
    headers: [{
        text: '조회수',
        align: 'left',
        sortable: false,
        value: 'viewCount'
      },
      {
        text: '제목',
        value: 'title'
      },
      {
        text: '작성자',
        value: 'userName'
      },
      {
        text: '답변수',
        value: 'replyCount'
      }
    ],
    questions: []
  }),
  beforeMount: function() {
    var item = this.questions;
    this.getQnaList(item);
  },

  methods: {
    getQnaList: function(questions) {
      axios.get("qna")
        .then(function(response) {
          for (var i = 0; i < response.data.length; i++) {
            questions.push(response.data[i]);
          }
        })
        .catch(function(error) {
          console.log(error);
        })
    },
    moveToWritePage: function(){
      this.$router.push('/qna/write/:name/:id');
    },
    writepage: function writepage() {
      router.push("/qna/write/" + this.name + "/" + this.id);
    }
  }
};
</script>
