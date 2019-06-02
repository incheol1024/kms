  <template>

    <div>
        <!-- write-component로 바꿔주세요-->
    <question-component :id="id" :name="name" :qid="qid"></question-component>
    <comment-component :id="id" :name="name" :qid="qid"></comment-component>
    </div>
  </template>

  <script>
  module.exports = {
    props: ['id', 'name', 'qid'],
    data() {
      return {
      }
    },

    created: function() {
        // console.log('qnaposts-component: life cycle created start');
        // console.log('qnaposts-component: id =' + this.id);
        // console.log('qnaposts-component: name = ' + this.name);
        // console.log('qnaposts-component: qid = ' + this.qid);
        // console.log('qnaposts-component: life cycle created end');
    },

    methods: {
    }
  }
  </script>
